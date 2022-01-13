package com.javaproject.microservices.userservice.dto;

import com.javaproject.microservices.userservice.model.Role;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  private long id;

  private String firstName;

  private String lastName;

  private String username;

  private String password;

  List<Role> roles = new ArrayList<>();
}
