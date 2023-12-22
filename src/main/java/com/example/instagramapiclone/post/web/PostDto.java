package com.example.instagramapiclone.post.web;

import com.example.instagramapiclone.users.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public record PostDto(
        String uuid,
        String caption,

        String postImg,

        User user,

        LocalDateTime createdAt) {
}
