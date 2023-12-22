package com.example.instagramapiclone.users.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Builder
public record UpdateUserDto(
        @NotBlank
        String username,
        @Email
        String email,

        String fullName,

        String gender,

        String bio

) {
}
