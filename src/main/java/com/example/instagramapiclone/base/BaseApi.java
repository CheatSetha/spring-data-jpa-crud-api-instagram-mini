package com.example.instagramapiclone.base;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BaseApi<T>(Boolean isSuccess,
                         Integer code,
                         String message,
                         LocalDate timestamp,
                         T payload) {
}

