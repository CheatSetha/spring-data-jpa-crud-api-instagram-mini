package com.example.instagramapiclone.util;


import com.example.instagramapiclone.file.FileDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUtil {
    //    @Value("${file.base_url}")
    private String fileBaseUrl =  "http://localhost:8080/api/v1/files/";
    //    @Value("${file.server_path}")
    private String fileServerPath = "C:\\Users\\cheat\\Desktop\\server\\";
    //    @Value("${file.base_download_url}")
    private String fileBaseDownloadUrl= "http://localhost:8080/api/v1/files/download/";

    public FileDto uploadFile(MultipartFile file) {
        int lastDotIndex = file.getOriginalFilename().lastIndexOf(".");
        String extension = file.getOriginalFilename().substring(lastDotIndex+1);
        long size = file.getSize();
        String name = String.format("%s.%s", UUID.randomUUID(),extension);
        String url = String.format("%s%s",fileBaseUrl,name);
        String downloadUrl = String.format("%s%s",fileBaseDownloadUrl,name);
        Path path = Paths.get(fileServerPath+ name);
        try {


            // Perform the file copy operation
            Files.copy(file.getInputStream(), path);



            // Assuming 'size', 'url', 'downloadUrl' are defined elsewhere
            return FileDto.builder()
                    .name(name)
                    .extension(extension)
                    .size(size)
                    .url(url)
                    .downloadUrl(downloadUrl)
                    .build();
        } catch (IOException e) {
            // Handle the exception

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload error", e);
        }
    }

}
