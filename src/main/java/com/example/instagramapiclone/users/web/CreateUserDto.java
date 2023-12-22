package com.example.instagramapiclone.users.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreateUserDto(
                            @NotBlank
                            String username,
                            @Email
                            String email,
                            @NotBlank
                            String fullName,
                            @NotBlank
                            String password


) {
}