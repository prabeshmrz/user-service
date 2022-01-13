package com.javaproject.microservices.userservice.mapper;

import com.javaproject.microservices.userservice.dto.UserDto;
import com.javaproject.microservices.userservice.dto.UserInfoDto;
import com.javaproject.microservices.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
  User toEntity(UserDto userDto);

  UserDto toDto(User user);

  UserInfoDto toInfoDto(User user);
}
