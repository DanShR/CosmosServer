package com.dan.cosmos.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private final String uploadPath;

    public FileService(@Value("${UPLOAD_PATH}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String saveFile(MultipartFile file) {
        String filename = new StringBuilder()
                .append(UUID.randomUUID().toString())
                .append(".")
                .append(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1))
                .toString();

        Path path = Paths.get(uploadPath, filename);
        try {
            file.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }
}
