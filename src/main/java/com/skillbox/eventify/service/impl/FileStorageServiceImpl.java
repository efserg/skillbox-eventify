package com.skillbox.eventify.service.impl;

import com.skillbox.eventify.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public String storeFile(String folder, String filename, MultipartFile cover) {
        return null;
    }

    @Override
    public void deleteFile(String folder, String filename) {

    }
}
