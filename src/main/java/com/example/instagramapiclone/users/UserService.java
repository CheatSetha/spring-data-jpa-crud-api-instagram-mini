package com.example.instagramapiclone.users;

import com.example.instagramapiclone.file.FileDto;
import com.example.instagramapiclone.users.web.CreateUserDto;
import com.example.instagramapiclone.users.web.UpdateUserDto;
import com.example.instagramapiclone.users.web.UserDto;

public interface UserService {
    User createUser(CreateUserDto userDto);
    Iterable<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder , String username, String email);

    UserDto getUserByUUID(String uuid);
    User getUserById(Long id);

    UserDto getUserByUsername(String username);


    UserDto getUserByEmail(String email);

    UserDto updateUser(String uuid,UpdateUserDto user);
    UserDto updateUserProfile(String uuid, String avatarUrl);

    void deleteUser(String uuid);


}
