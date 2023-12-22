package com.example.instagramapiclone.file;

import com.example.instagramapiclone.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService{
    private final FileUtil fileUtil;



    @Override
    public FileDto uploadSingleFile(MultipartFile file) {
        return fileUtil.uploadFile(file);
    }

    @Override
    public List<FileDto> uploadMultipleFiles(List<MultipartFile> files) {
        List<FileDto> fileDtoList = new ArrayList<>();
        for (MultipartFile file : files){
            fileDtoList.add(fileUtil.uploadFile(file));

        }
        return fileDtoList;
    }
}
