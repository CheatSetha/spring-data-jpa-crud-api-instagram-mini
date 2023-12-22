package com.example.instagramapiclone.users;



import com.example.instagramapiclone.file.FileService;
import com.example.instagramapiclone.users.web.CreateUserDto;
import com.example.instagramapiclone.users.web.UpdateUserDto;
import com.example.instagramapiclone.users.web.UserDto;
import com.example.instagramapiclone.users.web.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.UUID;

@Service
@Transactional
@Slf4j
public class UserServicImple implements UserService{

    private final UserRepository userRepository;
    private final FileService fileService;
    private final UserMapper userMapper;

    @Autowired
    public UserServicImple(UserRepository userRepository, UserMapper userMapper, FileService fileService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.fileService = fileService;
    }
    @Override
    public User createUser(CreateUserDto userDto) {
        boolean isEmailExists = userRepository.existsByEmail(userDto.email());
        if (isEmailExists){
            throw new RuntimeException("Email already exists");
        }else {
            try {
                User user = userMapper.createUserDtoToUser(userDto);
                user.setUuid(UUID.randomUUID().toString());
                user.setCreatedAt(LocalDateTime.now());
                user.setDeleted(false);
                user = userRepository.save(user);
                log.info("User has been created: {}", user);
                return user;
            } catch (DataAccessException e){
                throw new RuntimeException("Cannot create user");
            }
        }

    }

    @Override
    public Iterable<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String username, String email) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        User userCriteria = new User();
        userCriteria.setUsername(username);
        userCriteria.setEmail(email);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnorePaths("id", "createdAt", "updatedAt", "deleted");

        Example<User> example = Example.of(userCriteria, matcher);
        Page<User> users = userRepository.findAll(example, pageable);
//        List<UserDto> userDtos = users.stream().map(userMapper::toDto).toList();

        return userMapper.toDtoList(users);

    }

    @Override
    public UserDto getUserByUUID(String uuid) {
        User user = userRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("User not found!!!"));

        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found!!!"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found!!!"));
        return userMapper.toDto(user);
    }
    @Override
    public User getUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!!!"));
        return user;
    }

    @Override
    public UserDto updateUser(String uuid,UpdateUserDto user) {
//    update user
        boolean isEmailExists = userRepository.existsByEmail(user.email());
        if (isEmailExists){
            throw new RuntimeException("Email already exists");
        }
        var userToUpdate = userRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("User not found!!!"));
        userToUpdate.setUsername(user.username());
        userToUpdate.setEmail(user.email());
        userToUpdate.setBio(user.bio());
        userToUpdate.setFullName(user.fullName());
        userToUpdate.setGender(user.gender());

        userToUpdate = userRepository.save(userToUpdate);
        return userMapper.toDto(userToUpdate);

    }

    @Override
    public UserDto updateUserProfile(String uuid, String avatarUrl) {
        var userToUpdate = userRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("User not found!!!"));
        userToUpdate.setProfileImage(avatarUrl);
        return userMapper.toDto(userToUpdate);
    }

    @Override
    public void deleteUser(String uuid) {
        userRepository.deleteByUuid(uuid);

    }


}
