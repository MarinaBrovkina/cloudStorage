package org.example.cloudStorage;

import org.example.cloudStorage.entity.File;
import org.example.cloudStorage.entity.User;
import org.example.cloudStorage.models.request.RequestAuth;
import org.example.cloudStorage.models.request.RequestEditFileName;
import org.example.cloudStorage.models.response.ResponseFile;
import org.example.cloudStorage.models.response.ResponseJWT;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class TestData {
    public static final String TOKEN_1 = "Token_1";
    public static final String TOKEN_2 = "Token_2";
    public static final String BEARER_TOKEN = "Bearer Token";
    public static final String BEARER_TOKEN_SPLIT = BEARER_TOKEN.split(" ")[1];
    public static final String BEARER_TOKEN_SUBSTRING_7 = BEARER_TOKEN.substring(7);
    public static final Long USER_ID_1 = 1L;
    public static final String USERNAME_1 = "Username_1";
    public static final String PASSWORD_1 = "Password_1";
    public static final User USER_1 = new User(USER_ID_1, USERNAME_1, PASSWORD_1, null);

    public static final Long USER_ID_2 = 2L;
    public static final String USERNAME_2 = "Username_2";
    public static final String PASSWORD_2 = "Password_2";
    public static final User USER_2 = new User(USER_ID_2, USERNAME_2, PASSWORD_2, null);

    public static final Long FILE_ID_1 = 1L;
    public static final String FILENAME_1 = "Filename_1";
    public static final Long SIZE_1 = 100L;
    public static final byte[] FILE_CONTENT_1 = FILENAME_1.getBytes();
    public static final File FILE_1 = new File(FILE_ID_1, FILENAME_1, LocalDateTime.now(), SIZE_1, FILE_CONTENT_1, USER_1);

    public static final Long FILE_ID_2 = 2L;
    public static final String FILENAME_2 = "Filename_2";
    public static final Long SIZE_2 = 200L;
    public static final byte[] FILE_CONTENT_2 = FILENAME_2.getBytes();
    public static final File FILE_2 = new File(FILE_ID_2, FILENAME_2, LocalDateTime.now(), SIZE_2, FILE_CONTENT_2, USER_2);

    public static final Long FILE_ID_3 = 4L;
    public static final String FILENAME_3 = "Filename_3";
    public static final Long SIZE_4 = 400L;
    public static final byte[] NULL_FILE_CONTENT = null;
    public static final File FILE_WITH_NULL_FILE_CONTENT = new File(FILE_ID_3, FILENAME_3, LocalDateTime.now(), SIZE_4, NULL_FILE_CONTENT, USER_1);

    public static final File FILE_NULL = null;
    public static final String FILENAME_EMPTY = null;
    public static final String NEW_FILENAME = "New_Filename";
    public static final RequestEditFileName REQUEST_EDIT_FILE_NAME = new RequestEditFileName(NEW_FILENAME);
    public static final RequestEditFileName REQUEST_EDIT_FILE_NAME_REPEAT = new RequestEditFileName(FILENAME_1);

    public static final MultipartFile MULTIPART_FILE = new MockMultipartFile(FILENAME_2, FILE_CONTENT_2);

    public static final List<File> FILE_LIST = List.of(FILE_1, FILE_2);
    public static final List<File> FILE_LIST_NULL = null;
    public static final ResponseFile RESPONSE_FILE_1 = new ResponseFile(FILENAME_1, SIZE_1);
    public static final ResponseFile RESPONSE_FILE_2 = new ResponseFile(FILENAME_2, SIZE_2);
    public static final List<ResponseFile> RESPONSE_FILE_LIST = List.of(RESPONSE_FILE_1, RESPONSE_FILE_2);

    public static final Integer LIMIT = 100;
    public static final Integer LIMIT_NULL = 0;

    public static final RequestAuth REQUEST_AUTH = new RequestAuth(USERNAME_1, PASSWORD_1);
    public static final ResponseJWT RESPONSE_JWT = new ResponseJWT(TOKEN_1);

    public static final UsernamePasswordAuthenticationToken USERNAME_PASSWORD_AUTH_TOKEN = new UsernamePasswordAuthenticationToken(USERNAME_1, PASSWORD_1);
}
