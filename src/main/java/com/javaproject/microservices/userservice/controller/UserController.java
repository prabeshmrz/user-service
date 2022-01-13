package com.javaproject.microservices.userservice.controller;

import com.javaproject.microservices.userservice.dto.AuthenticateResponse;
import com.javaproject.microservices.userservice.dto.AuthenticationRequest;
import com.javaproject.microservices.userservice.dto.UserDto;
import com.javaproject.microservices.userservice.dto.UserInfoDto;
import com.javaproject.microservices.userservice.service.UserService;
import com.javaproject.microservices.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = UserController.CONTROLLER_URL)
@RequiredArgsConstructor
public class UserController {

  public static final String CONTROLLER_URL = "user";

  private final AuthenticationManager authenticationManager;

  private final UserDetailsService userDetailsService;

  private final UserService userService;

  private final JwtUtil jwtUtil;

  @PostMapping("authenticate")
  public ResponseEntity<AuthenticateResponse> login(
    @RequestBody AuthenticationRequest authenticationRequest
  ) {
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          authenticationRequest.getUsername(),
          authenticationRequest.getPassword()
        )
      );
    } catch (BadCredentialsException e) {
      throw new ResponseStatusException(
        HttpStatus.UNAUTHORIZED,
        "Incorrect Credentials!"
      );
    }

    final var userDetails = userDetailsService.loadUserByUsername(
      authenticationRequest.getUsername()
    );

    final String token = jwtUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthenticateResponse(token));
  }

  @PostMapping("registration")
  public ResponseEntity<UserInfoDto> register(@RequestBody UserDto userDto) {
    return new ResponseEntity<>(
      userService.registerUser(userDto),
      HttpStatus.CREATED
    );
  }

  @GetMapping("{username}")
  public ResponseEntity<UserInfoDto> getUser(
    @PathVariable(value = "username") String username
  ) {
    return ResponseEntity.ok(userService.getUserInfo(username));
  }
}
