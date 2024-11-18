package org.example.cloudStorage;

import org.example.cloudStorage.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.example.cloudStorage.repository.UserRepository;
import org.example.cloudStorage.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserByUsername() {
        String username = "testuser";
        User user = new User();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByUsername(username);

        assertEquals(Optional.of(user), result);
        verify(userRepository, times(1)).findByUsername(username);
    }
}

