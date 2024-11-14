//package org.example.cloudStorage;
//
//import org.example.cloudStorage.entity.File;
//import org.example.cloudStorage.entity.User;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.web.multipart.MultipartFile;
//import org.example.cloudStorage.repository.FileRepository;
//import org.example.cloudStorage.repository.UserRepository;
//import org.example.cloudStorage.service.FileService;
//
//import java.io.IOException;
//import java.nio.file.Path;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//    @ExtendWith(MockitoExtension.class)
//    public class FileServiceTest {
//
//        @Mock
//        private FileRepository fileRepository;
//
//        @Mock
//        private UserRepository userRepository;
//
//        @InjectMocks
//        private FileService fileService;
//
//        @Test
//        void getFiles() {
//            User user = new User();
//            List<File> files = List.of(new File());
//            when(fileRepository.findByUser(user)).thenReturn(files);
//
//            List<File> result = fileService.getFiles(user);
//
//            assertEquals(files, result);
//            verify(fileRepository, times(1)).findByUser(user);
//        }
//
//        @Test
//        void uploadFile() throws IOException {
//            User user = new User();
//            MultipartFile mockFile = mock(MultipartFile.class);
//            when(mockFile.getOriginalFilename()).thenReturn("test.txt");
//            when(mockFile.getSize()).thenReturn(1024L);
//            File savedFile = new File();
//            when(fileRepository.save(any())).thenReturn(savedFile);
//
//            File uploadedFile = fileService.uploadFile(mockFile, user);
//
//            assertEquals(savedFile, uploadedFile);
//            verify(mockFile, times(1)).getOriginalFilename();
//            verify(mockFile, times(1)).getSize();
//            verify(mockFile, times(1)).transferTo(any(Path.class));
//            verify(fileRepository, times(1)).save(any());
//        }
//
//        @Test
//        void deleteFile() {
//            Long id = 1L;
//            fileService.deleteFile(id);
//            verify(fileRepository, times(1)).deleteById(id);
//        }
//    }
//
//
