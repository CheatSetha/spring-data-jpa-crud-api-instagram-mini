package com.example.instagramapiclone.users.web;

import com.example.instagramapiclone.users.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User userDtoToUser(UserDto userDto);
    CreateUserDto userToCreateUserDto(User user);
    User createUserDtoToUser(CreateUserDto createUserDto);
    Iterable<UserDto> toDtoList(Iterable<User> users);

    User updateUserDtoToUser(UpdateUserDto user);
    UpdateUserDto userToUpdateUserDto(User user);
}
