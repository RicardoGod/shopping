package com.backend.shopping.service;

import com.backend.shopping.dto.CartDTO;
import com.backend.shopping.dto.DepositDTO;
import com.backend.shopping.dto.ProductDTO;
import com.backend.shopping.dto.PurchaseDTO;
import com.backend.shopping.exceptions.NotEnoughMoneyException;
import com.backend.shopping.model.Coin;
import com.backend.shopping.model.CoinValue;
import com.backend.shopping.model.Deposit;
import com.backend.shopping.model.Product;
import com.backend.shopping.model.User;
import com.backend.shopping.repository.CoinRepository;
import com.backend.shopping.repository.DepositRepository;
import com.backend.shopping.repository.ProductRepository;
import com.backend.shopping.repository.UserRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartService {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  DepositRepository depositRepository;

  @Autowired
  CoinRepository coinRepository;

  @Transactional(rollbackOn = {Exception.class})
  public PurchaseDTO purchaseCart(CartDTO cart, Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    Optional<Product> optionalProduct = productRepository.findById(cart.getProductId());

    if (optionalUser.isPresent() && optionalProduct.isPresent()) {
      Product product = getProductResultAfterOperation(cart, optionalProduct.get());
      Long totalCost = product.getCost() * cart.getQuantity();

      User user = optionalUser.get();
      Deposit deposit = user.getDeposit();

      Long currentBalance = getBalance(deposit);

      if (currentBalance < totalCost) {
        log.error("User does not have enough Deposit to pay! CurrentBalance=[{}] TotalCost=[{}]", currentBalance, totalCost);
        throw new NotEnoughMoneyException();
      }

      Deposit depositChange = purchase(deposit, totalCost);

      depositRepository.save(deposit);
      productRepository.save(product);

      return new PurchaseDTO(totalCost, new ProductDTO(product), new DepositDTO(depositChange));
    }

    throw new IllegalArgumentException();
  }

  private Product getProductResultAfterOperation(CartDTO cart, Product product) {
    Long quantityRemaining = product.getAmountAvailable() - cart.getQuantity();
    product.setAmountAvailable(quantityRemaining);
    return product;
  }

  private Long getBalance(Deposit deposit) {
    return deposit.getCoins()
        .stream()
        .map(coin -> coin.getCoinValue().getValue())
        .reduce(0L, Long::sum);
  }

  private Deposit purchase(Deposit deposit, long totalPay) {
    Long changeValue = payWithDeposit(deposit, totalPay);
    Deposit changeDeposit = getChange(changeValue);
    putChangeOnDeposit(deposit, changeDeposit);
    return changeDeposit;
  }

  private Long payWithDeposit(Deposit initialDeposit, long totalPay) {
    while (totalPay > 0) {
      Coin coin = getMaximumCoinValue(initialDeposit);
      totalPay -= coin.getCoinValue().getValue();
      initialDeposit.getCoins().remove(coin);
      coinRepository.delete(coin);
    }

    return totalPay;
  }

  private Coin getMaximumCoinValue(Deposit initialDeposit) {
    List<Coin> collect = initialDeposit.getCoins().stream()
        .sorted(Comparator.comparingLong(c -> c.getCoinValue().getValue()))
        .toList();

    Collections.reverse(collect);

    return collect.stream().findFirst().orElseThrow(NotEnoughMoneyException::new);
  }

  private Deposit getChange(Long change) {
    Deposit deposit = new Deposit();
    while (change < 0) {
      Coin coin = tryGetMaxCoinForChange(change);
      coinRepository.save(coin);
      change += coin.getCoinValue().getValue();
      deposit.getCoins().add(coin);
    }
    return deposit;
  }

  private Coin tryGetMaxCoinForChange(long change) {
    CoinValue coinValue = Arrays.stream(CoinValue.values())
        .sorted(Comparator.comparingLong(CoinValue::getValue).reversed())
        .filter(c -> Math.abs(change) >= c.getValue())
        .findFirst()
        .get();

    return new Coin(coinValue);
  }

  private void putChangeOnDeposit(Deposit deposit, Deposit change) {
    List<Coin> coins = change.getCoins();
    coins.forEach(coin -> coin.setDeposit(deposit));
    coins.forEach(coin -> deposit.getCoins().add(coin));
  }

}
