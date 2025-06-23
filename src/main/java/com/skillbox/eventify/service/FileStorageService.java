package com.skillbox.eventify.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeFile(String folder, String filename, MultipartFile cover);

    void deleteFile(String folder, String filename);
}
