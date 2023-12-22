package com.example.instagramapiclone.file;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Table(name = "file_upload")
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String extension;
    private String fileName;
    private String fileOrgName;
    private Long fileSize;
    private String fullPath;
    private boolean status;
    private String channelCode;
    private LocalDateTime uploadedDate = LocalDateTime.now();
}
