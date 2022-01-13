package com.javaproject.microservices.userservice.service;

import com.javaproject.microservices.userservice.dto.UserDto;
import com.javaproject.microservices.userservice.dto.UserInfoDto;

/**
 * UserService
 */
public interface UserService {
  UserInfoDto registerUser(UserDto userDto);

  UserInfoDto getUserInfo(String username);
}
