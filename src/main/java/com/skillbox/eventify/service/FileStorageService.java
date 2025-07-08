package com.skillbox.eventify.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeFile(String filename, MultipartFile cover);

    void deleteFile(String filename);

    String downloadUrl(String coverUrl, String filename);
}
