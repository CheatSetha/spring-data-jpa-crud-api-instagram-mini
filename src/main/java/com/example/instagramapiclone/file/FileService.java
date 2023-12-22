package com.example.instagramapiclone.file;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    FileDto uploadSingleFile(MultipartFile file);
    List<FileDto> uploadMultipleFiles(List<MultipartFile> files);
}
