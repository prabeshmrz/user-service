package com.javaproject.microservices.userservice.service;

import com.javaproject.microservices.userservice.dto.UserDto;
import com.javaproject.microservices.userservice.dto.UserInfoDto;
import com.javaproject.microservices.userservice.mapper.UserMapper;
import com.javaproject.microservices.userservice.model.User;
import com.javaproject.microservices.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public UserInfoDto registerUser(UserDto userDto) {
    var user = userMapper.toEntity(userDto);
    return userMapper.toInfoDto(userRepository.save(user));
  }

  @Override
  public UserInfoDto getUserInfo(long id) {
    return userMapper.toInfoDto(findById(id));
  }

  public User findById(long id) {
    return userRepository
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found!!")
      );
  }
}
