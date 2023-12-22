package com.example.instagramapiclone.post.web;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.lang.Nullable;


@Builder
public record CreatePostDto(
        @Nullable
        String caption,
        @NotNull
        String postImg,
        @NotNull
        long userId

) {
}
