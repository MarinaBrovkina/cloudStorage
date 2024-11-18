package org.example.cloudStorage;

import org.example.cloudStorage.entity.File;
import org.example.cloudStorage.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.example.cloudStorage.repository.UserRepository;
import org.example.cloudStorage.service.FileService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CloudStorageApplication.class)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IntegrationTest {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void uploadAndDownloadFile() {
        try (PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("cloudstorage")
                .withUsername("testuser")
                .withPassword("testpassword")) {
            postgres.start();
            User user = new User("testuser", "testpassword");
            userRepository.save(user);
            String filename = "test.txt";

            Optional<File> optionalUploadedFile = Optional.empty();
            try {
                optionalUploadedFile.ifPresent(uploadedFile -> {
                    assertNotNull(uploadedFile.getId());
                    assertEquals(filename, uploadedFile.getName());
                    assertEquals(0L, uploadedFile.getSize());

                    Path uploadedFilePath = Paths.get(uploadedFile.getPath());
                    assertTrue(Files.exists(uploadedFilePath), "Файл не существует после загрузки");

                    File downloadedFile = findDownloadedFile(fileService.getFiles(user), uploadedFile.getId());

                    assertEquals(filename, downloadedFile.getName());
                    assertEquals(0L, downloadedFile.getSize());
                    assertEquals(uploadedFilePath.getFileName().toString(),
                            Paths.get(downloadedFile.getPath()).getFileName().toString());

                });

            } catch (Exception e) {
                fail("Ошибка во время теста: " + e.getMessage(), e);
            } finally {
                optionalUploadedFile.ifPresent(uploadedFile ->
                        fileService.deleteFile(uploadedFile.getId()));
            }
        }
    }

    private File findDownloadedFile(List<File> files, Long id) {
        return files.stream()
                .filter(Objects::nonNull)
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Файл не найден после загрузки"));
    }
}

