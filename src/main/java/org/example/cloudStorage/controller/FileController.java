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
import java.util.Map;
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
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file,
                                                          @AuthenticationPrincipal User user) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "File is required"));
        }
        try {
            File uploadedFile = fileService.uploadFile(file, user);
            FileDto fileDto = new FileDto(uploadedFile.getId(), uploadedFile.getName(),
                    uploadedFile.getSize(), uploadedFile.getPath());
            return ResponseEntity.ok(Map.of("file", fileDto));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload file: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteFile(@PathVariable Long id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.ok(Map.of("message", "File deleted successfully"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
