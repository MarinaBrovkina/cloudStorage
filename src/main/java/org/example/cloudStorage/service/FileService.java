package org.example.cloudStorage.service;

import org.example.cloudStorage.entity.File;
import org.example.cloudStorage.entity.User;
import org.example.cloudStorage.repository.FileRepository;
import org.example.cloudStorage.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public FileService(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
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


