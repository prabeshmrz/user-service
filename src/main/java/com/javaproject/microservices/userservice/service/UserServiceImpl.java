package com.javaproject.microservices.userservice.service;

import com.javaproject.microservices.userservice.dto.UserDto;
import com.javaproject.microservices.userservice.dto.UserInfoDto;
import com.javaproject.microservices.userservice.mapper.UserMapper;
import com.javaproject.microservices.userservice.model.User;
import com.javaproject.microservices.userservice.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserInfoDto registerUser(UserDto userDto) {
    var user = userMapper.toEntity(userDto);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userMapper.toInfoDto(userRepository.save(user));
  }

  @Override
  public UserInfoDto getUserInfo(String username) {
    return userMapper.toInfoDto(findByUsername(username));
  }

  public User findByUsername(String username) {
    return userRepository
      .findByUsername(username)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found!!")
      );
  }
}
