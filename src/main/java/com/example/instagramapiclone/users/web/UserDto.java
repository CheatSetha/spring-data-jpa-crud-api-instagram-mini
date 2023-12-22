package com.example.instagramapiclone.users.web;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto(
        Long id,
        String uuid,
        String username,
        String email,
        String fullName,
        String gender,
        String profileImage,
        String bio,
        LocalDateTime createdAt

) {
}
