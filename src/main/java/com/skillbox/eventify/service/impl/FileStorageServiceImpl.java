package com.skillbox.eventify.service.impl;

import com.skillbox.eventify.exception.FileStorageException;
import com.skillbox.eventify.exception.FileTooLargeException;
import com.skillbox.eventify.exception.NoCoverException;
import com.skillbox.eventify.exception.WrongFileException;
import com.skillbox.eventify.service.FileStorageService;
import jakarta.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Path EVENTS_COVERS_PATH = Path.of("events/covers");
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
            "image/jpeg",
            "image/png"
    );

    @Override
    public String storeFile(String filename, MultipartFile cover) {
        if (cover == null || cover.isEmpty()) {
            throw new NoCoverException();
        }
        if (cover.getSize() > MAX_FILE_SIZE) {
            throw new FileTooLargeException();
        }
        if (!ALLOWED_CONTENT_TYPES.contains(cover.getContentType())) {
            throw new WrongFileException();
        }

        return null;
    }

    @Override
    public void deleteFile(String filename) {
        final Path imagePath = EVENTS_COVERS_PATH.resolve(filename);
        try {
            Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            throw new FileStorageException("Ошибка при удалении файла изображения", e);
        }
    }

    @Override
    public String downloadUrl(String coverUrl, String filename) {
        if (coverUrl == null) {
            return null;
        }
        try {
            URL url = new URL(coverUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Failed to download image, HTTP code: " + responseCode);
            }

            String extension = FilenameUtils.getExtension(url.getPath());
            if (extension == null || extension.isEmpty()) {
                extension = "jpg";
            }

            String fileName = filename + "." + extension;
            Path filePath = EVENTS_COVERS_PATH.resolve(fileName);

            try (InputStream inputStream = connection.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream(filePath.toFile())
            ) {
                IOUtils.copy(inputStream, outputStream);
            }

            return fileName;

        } catch (Exception e) {
            throw new FileStorageException(e.getMessage());
        }
    }

    @PostConstruct
    public void createFolder() throws IOException {
        if (!Files.exists(EVENTS_COVERS_PATH)) {
            Files.createDirectories(EVENTS_COVERS_PATH);
        }
    }
}
