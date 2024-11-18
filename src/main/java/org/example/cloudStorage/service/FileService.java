package org.example.cloudStorage.service;

import org.example.cloudStorage.entity.File;
import org.example.cloudStorage.entity.User;
import org.example.cloudStorage.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> getFiles(User user) {
        return fileRepository.findByUser(user);
    }

    public File uploadFile(MultipartFile file, User user) throws IOException {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new IllegalArgumentException("Имя файла не может быть null");
        }

        Path uploadDir = Paths.get(System.getProperty("java.io.tmpdir"));
        Files.createDirectories(uploadDir);

        Path filePath = uploadDir.resolve(originalFileName);
        file.transferTo(filePath);

        File newFile = new File();
        newFile.setName(originalFileName);
        newFile.setSize(file.getSize());
        newFile.setPath(filePath.toString());
        newFile.setUser(user);
        return fileRepository.save(newFile);
    }

    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }
}