package com.backend.shopping.config;

import com.backend.shopping.model.RoleCategory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicAuthWebSecurityConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/h2").permitAll()
        .antMatchers(HttpMethod.POST, "/user").permitAll()
        .antMatchers(HttpMethod.GET,"/user").authenticated()
        .antMatchers(HttpMethod.GET,"/user/").authenticated()
        .antMatchers(HttpMethod.POST,"/user/reset").authenticated()
        .antMatchers(HttpMethod.POST, "/user/buy").hasAuthority(RoleCategory.BUYER.toString())
        .antMatchers(HttpMethod.POST, "/user/deposit").hasAuthority(RoleCategory.BUYER.toString())
        .antMatchers(HttpMethod.POST, "/product").hasAuthority(RoleCategory.SELLER.toString())
        .antMatchers(HttpMethod.POST, "/product/").hasAuthority(RoleCategory.SELLER.toString())
        .antMatchers(HttpMethod.PUT, "/product/*").hasAuthority(RoleCategory.SELLER.toString())
        .antMatchers(HttpMethod.DELETE, "/product/*").hasAuthority(RoleCategory.SELLER.toString())
        .anyRequest().authenticated()
        .and()
        .httpBasic();

    return http.build();
  }

}
