package org.example.cloudStorage.controller;

import org.example.cloudStorage.entity.File;
import org.example.cloudStorage.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.cloudStorage.service.FileService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public List<File> getFiles(@AuthenticationPrincipal User user) {
        return fileService.getFiles(user);
    }

    @PostMapping
    public ResponseEntity<File> uploadFile(@RequestParam("file") MultipartFile file,
                                           @AuthenticationPrincipal User user) throws IOException {
        File uploadedFile = fileService.uploadFile(file, user);
        return ResponseEntity.ok(uploadedFile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }
}

