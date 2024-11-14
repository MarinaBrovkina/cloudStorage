package org.example.cloudStorage.controller;

import org.example.cloudStorage.dto.FileDto;
import org.example.cloudStorage.entity.File;
import org.example.cloudStorage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.cloudStorage.service.FileService;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<List<FileDto>> getFiles(@AuthenticationPrincipal User user) {
        List<File> files = fileService.getFiles(user);
        List<FileDto> fileDtos = files.stream()
                .map(file -> new FileDto(file.getId(), file.getName(), file.getSize(), file.getPath()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(fileDtos);
    }

    @PostMapping
    public ResponseEntity<FileDto> uploadFile(@RequestParam("file") MultipartFile file,
                                              @AuthenticationPrincipal User user) {
        try {
            File uploadedFile = fileService.uploadFile(file, user);
            FileDto fileDto = new FileDto(uploadedFile.getId(), uploadedFile.getName(),
                    uploadedFile.getSize(), uploadedFile.getPath());
            return ResponseEntity.ok(fileDto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
