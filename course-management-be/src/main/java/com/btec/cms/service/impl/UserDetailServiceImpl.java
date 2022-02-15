package com.btec.cms.service.impl;

import com.btec.cms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  public static final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

  @Autowired UserService userService;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) {
    log.info("Load user by username: {}", username);
    return userService
        .findByUsername(username)
        .map(
            u -> {
              User.UserBuilder builder = User.withUsername(u.getUsername());
              builder.password(u.getPassword());
              builder.username(u.getUsername());
              SimpleGrantedAuthority authority = new SimpleGrantedAuthority(u.getRole().getRole());
              builder.authorities(authority.toString());
              return builder.build();
            })
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
