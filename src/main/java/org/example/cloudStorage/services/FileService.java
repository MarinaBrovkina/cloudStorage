package org.example.cloudStorage.services;

import io.micrometer.common.util.StringUtils;
import org.example.cloudStorage.entity.File;
import org.example.cloudStorage.entity.User;
import org.example.cloudStorage.exceptions.*;
import org.example.cloudStorage.models.request.RequestEditFileName;
import org.example.cloudStorage.models.response.ResponseFile;
import org.example.cloudStorage.repositories.AuthRepository;
import org.example.cloudStorage.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FileService {

    private final AuthRepository authRepository;
    private final FileRepository fileRepository;

    @Autowired
    public FileService(AuthRepository authRepository, FileRepository fileRepository) {
        this.authRepository = authRepository;
        this.fileRepository = fileRepository;
    }

    public boolean uploadFile(String authToken, String filename, MultipartFile multipartFile) {
        User user = getUserByToken(authToken);
        if (user == null) {
            throw new ErrorUnauthorized();
        }
        try {
            File uploadFile = new File(filename, LocalDateTime.now(), multipartFile.getSize(), multipartFile.getBytes(), user);
            fileRepository.save(uploadFile);
        } catch (IOException e) {
            throw new ErrorInputData();
        }
        return true;
    }

    public void deleteFile(String authToken, String filename) {
        User user = getUserByToken(authToken);
        if (user == null) {
            throw new ErrorUnauthorized();
        }
        if (StringUtils.isEmpty(filename)) {
            throw new ErrorInputData();
        }
        long deletedCount = fileRepository.deleteByUserAndFilename(user, filename);
        if (deletedCount == 0) {
            throw new ErrorDeleteFile();
        }
    }

    public byte[] downloadFile(String authToken, String filename) {
        User user = getUserByToken(authToken);
        if (user == null) {
            throw new ErrorUnauthorized();
        }
        File file = fileRepository.findByUserAndFilename(user, filename);
        if (file == null) {
            throw new ErrorInputData();
        }
        byte[] fileContent = file.getFileContent();
        if (fileContent == null) {
            throw new ErrorUploadFile();
        }
        return fileContent;
    }

    public void editFileName(String authToken, String filename, RequestEditFileName requestEditFileName) {
        User user = getUserByToken(authToken);
        if (user == null) {
            throw new ErrorUnauthorized();
        }
        File file = fileRepository.findByUserAndFilename(user, filename);
        if (file == null) {
            throw new ErrorInputData();
        }
        fileRepository.setNewFilenameByUserAndFilename(requestEditFileName.getFilename(), user, filename);
        if (filename.equals(requestEditFileName.getFilename())) {
            throw new ErrorUploadFile();
        }
    }

    public List<ResponseFile> getAllFiles(String authToken, Integer limit) {
        User user = getUserByToken(authToken);
        if (user == null) {
            throw new ErrorUnauthorized();
        }
        if (limit == 0) {
            throw new ErrorInputData();
        }
        List<File> allFilesByUser = fileRepository.findAllByUser(user);
        if (allFilesByUser == null) {
            throw new ErrorGettingFileList();
        }
        return allFilesByUser.stream().map(x -> new ResponseFile(x.getFilename(), x.getSize())).toList();
    }

    public User getUserByToken(String authToken) {
        if (authToken.startsWith("Bearer ")) {
            String tokenWithoutBearer = authToken.substring(7);
            return authRepository.getAuthenticationUserByToken(tokenWithoutBearer);
        } else return null;
    }
}
