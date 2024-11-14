package org.example.cloudStorage.service;

import org.example.cloudStorage.entity.File;
import org.example.cloudStorage.entity.User;
import org.example.cloudStorage.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        String path = "uploads/" + originalFileName;
        file.transferTo(Paths.get(path));
        File newFile = new File(originalFileName, file.getSize(), path, user);
        return fileRepository.save(newFile);
    }

    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }
}
