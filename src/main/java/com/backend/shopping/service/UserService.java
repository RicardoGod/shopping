package com.backend.shopping.service;

import com.backend.shopping.dto.CoinDTO;
import com.backend.shopping.dto.UserDTO;
import com.backend.shopping.model.Coin;
import com.backend.shopping.model.Deposit;
import com.backend.shopping.model.Role;
import com.backend.shopping.model.RoleCategory;
import com.backend.shopping.model.User;
import com.backend.shopping.repository.CoinRepository;
import com.backend.shopping.repository.DepositRepository;
import com.backend.shopping.repository.RoleRepository;
import com.backend.shopping.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  DepositRepository depositRepository;

  @Autowired
  CoinRepository coinRepository;

  @Autowired
  RoleRepository roleRepository;

  @Transactional(rollbackOn = Exception.class)
  public Optional<User> registerUser(UserDTO userDTO){
    log.info("[Service] Begin Register");

    User user = new User(userDTO);

    Role role = roleRepository.findFirstByName(RoleCategory.valueOf(userDTO.getRole()));
    user.setRole(role);

    Deposit deposit = new Deposit();
    deposit = depositRepository.save(deposit);

    List<Coin> coins = initCoins(userDTO.getDeposit().getCoins());
    for(Coin c : coins){
      c.setDeposit(deposit);
    }
    coins.forEach(coin -> coinRepository.save(coin));
    deposit.setCoins(coins);

    user.setDeposit(deposit);

    User savedUser = userRepository.save(user);

    Optional<User> persistedUser = userRepository.findById(savedUser.getId());

    persistedUser.ifPresent(u -> log.warn(u.toString()));
    return persistedUser;
  }

  private List<Coin> initCoins(List<CoinDTO> coins) {
    return coins.stream()
        .map(coin -> new Coin(coin.getCoinValue()))
        .toList();
  }

  @Transactional(rollbackOn = Exception.class)
  public Optional<User> addDeposit(CoinDTO coinDTO, Long userId) {
    Optional<User> user = userRepository.findById(userId);
    if(user.isPresent()){
      Deposit deposit = addCoinToDeposit(user.get().getDeposit(), coinDTO);
      depositRepository.save(deposit);
      return userRepository.findById(userId);
    } else {
      return Optional.empty();
    }
  }

  private Deposit addCoinToDeposit(Deposit deposit, CoinDTO coinDTO){
    Coin coin = new Coin(coinDTO.getCoinValue());
    coin.setDeposit(deposit);
    coinRepository.save(coin);
    deposit.getCoins().add(coin);
    return deposit;
  }

  @Transactional(rollbackOn = Exception.class)
  public Optional<User> resetDeposit(Long userId) {
    Optional<User> user = userRepository.findById(userId);
    if(user.isPresent()){
      Deposit deposit = tookAllCoins(user.get().getDeposit());
      depositRepository.save(deposit);
      return userRepository.findById(userId);
    } else {
      return Optional.empty();
    }
  }

  private Deposit tookAllCoins(Deposit deposit){
    deposit.getCoins().forEach(coin -> coinRepository.delete(coin));
    deposit.setCoins(new ArrayList<>());
    depositRepository.save(deposit);
    return deposit;
  }

  public Optional<User> find(Long userId) {
    Optional<User> persistedUser = userRepository.findById(userId);
    persistedUser.ifPresent(u -> log.warn(u.toString()));
    return persistedUser;
  }
}
