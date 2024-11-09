package org.example.cloudStorage;

import org.example.cloudStorage.entity.File;
import org.example.cloudStorage.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.example.cloudStorage.repository.UserRepository;
import org.example.cloudStorage.service.FileService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ComponentScan("org.example.cloudStorage")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("cloudstorage")
            .withUsername("testuser")
            .withPassword("testpassword");

    @Autowired
    private FileService fileService;

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void uploadAndDownloadFile() throws IOException {
        User user = new User("testuser", "testpassword");
        userRepository.save(user);

        String filename = "test.txt";
        String filepath = "src/test/resources/" + filename;

        MultipartFile multipartFile = new MockMultipartFile(filename,
                Files.readAllBytes(Paths.get(filepath)));

        File uploadedFile = fileService.uploadFile(multipartFile, user);

        assertNotNull(uploadedFile.getId());
        assertEquals(filename, uploadedFile.getName());
        assertEquals(Files.size(Paths.get(filepath)), uploadedFile.getSize());
        assertTrue(Files.exists(Paths.get(uploadedFile.getPath())));

        File downloadedFile = fileService.getFiles(user).stream()
                .filter(f -> f.getId().equals(uploadedFile.getId()))
                .findFirst().orElse(null);

        assertNotNull(downloadedFile);
        assertEquals(filename, downloadedFile.getName());
        assertEquals(Files.size(Paths.get(filepath)), downloadedFile.getSize());
        assertTrue(Files.exists(Paths.get(downloadedFile.getPath())));
    }
}

