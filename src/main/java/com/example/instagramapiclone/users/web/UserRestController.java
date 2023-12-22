package com.example.instagramapiclone.users.web;


import com.example.instagramapiclone.base.AppConstants;
import com.example.instagramapiclone.base.BaseApi;
import com.example.instagramapiclone.file.FileDto;
import com.example.instagramapiclone.file.FileService;
import com.example.instagramapiclone.users.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserRestController {
    private final UserService userService;
    private final FileService fileService;


    @PostMapping
    public ResponseEntity<BaseApi<?>> createUser(@Valid @RequestBody CreateUserDto usrDto) {

        try {
            var user = userService.createUser(usrDto);
            return ResponseEntity.ok(BaseApi.builder().code(200).isSuccess(true).message("User has been created").timestamp(LocalDate.now()).payload(user).build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(BaseApi.builder().code(400).isSuccess(false).message(e.getMessage()).timestamp(LocalDate.now()).build());
        }
    }

    @GetMapping
    public BaseApi<?> getAllUsers(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize, @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_USERS_BY, required = false) String sortBy, @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder, @RequestParam(name = "username", defaultValue = "", required = false) String username, @RequestParam(name = "email", defaultValue = "", required = false) String email) {
        var users = userService.getAllUsers(pageNumber, pageSize, sortBy, sortOrder, username, email);
        log.info("Users have been found: {}", users);
        return BaseApi.builder().code(200).isSuccess(true).message("Users have been found").timestamp(LocalDate.now()).payload(users).build();
    }

    @GetMapping("/{uuid}")
    public BaseApi<?> getUserByUUID(@PathVariable(name = "uuid") String uuid) {
        var user = userService.getUserByUUID(uuid);
        return BaseApi.builder().code(200).isSuccess(true).message("User has been found").timestamp(LocalDate.now()).payload(user).build();
    }

    @GetMapping("/username/{username}")
    public BaseApi<?> getUserByUsername(@PathVariable(name = "username") String username) {
        var user = userService.getUserByUsername(username);
        return BaseApi.builder().code(200).isSuccess(true).message("User has been found").timestamp(LocalDate.now()).payload(user).build();
    }

    @GetMapping("/email/{email}")
    public BaseApi<?> getUserByEmail(@PathVariable(name = "email") String email) {
        var user = userService.getUserByEmail(email);
        return BaseApi.builder().code(200).isSuccess(true).message("User has been found").timestamp(LocalDate.now()).payload(user).build();
    }

    @PutMapping("/{uuid}")
    public BaseApi<?> updateUser(@Valid @RequestBody UpdateUserDto user, @PathVariable(name = "uuid") String uuid) {
        boolean isUuidExists = userService.getUserByUUID(uuid) != null;
        if (!isUuidExists) {
            return BaseApi.builder().code(400).isSuccess(false).message("User not found").timestamp(LocalDate.now()).build();
        }
        var updatedUser = userService.updateUser(uuid,user);
        return BaseApi.builder().code(200).isSuccess(true).message("User has been updated").timestamp(LocalDate.now()).payload(updatedUser).build();
    }

    @DeleteMapping("/{uuid}")
    public BaseApi<?> deleteUser(@PathVariable(name = "uuid") String uuid) {
        boolean isUuidExists = userService.getUserByUUID(uuid) != null;
        if (!isUuidExists) {
            return BaseApi.builder().code(400).isSuccess(false).message("User not found").timestamp(LocalDate.now()).build();
        }

        userService.deleteUser(uuid);
        return BaseApi.builder().code(200).isSuccess(true).message("User has been deleted").timestamp(LocalDate.now()).build();
    }
    @PutMapping("/{uuid}/profile")
    public BaseApi<?> updateUserProfile(@PathVariable(name = "uuid") String uuid, @RequestPart(name = "file")MultipartFile file) {
        FileDto fileDto = fileService.uploadSingleFile(file);
        boolean isUuidExists = userService.getUserByUUID(uuid) != null;
        if (!isUuidExists) {
            return BaseApi.builder().code(400).isSuccess(false).message("User not found").timestamp(LocalDate.now()).build();
        }
        var updatedUser = userService.updateUserProfile(uuid,fileDto.url());
        return BaseApi.builder().code(200).isSuccess(true).message("User has been updated").timestamp(LocalDate.now()).payload(updatedUser).build();
    }
}
