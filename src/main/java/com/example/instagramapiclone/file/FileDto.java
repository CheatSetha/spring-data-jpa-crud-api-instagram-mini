package com.example.instagramapiclone.file;

import lombok.Builder;

@Builder
public record FileDto(
        String name,
        String url,
        String extension,
        String downloadUrl,
        long size) {
}
