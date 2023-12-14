package org.example.user.service;

import org.example.user.model.User;
import org.example.user.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_PASSWORD = "testUser";
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUsername() {
        // Given

        User mockUser = buildTestUser();
        Mockito.when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(mockUser));

        // When
        UserDetails userDetails = userService.loadUserByUsername(TEST_USERNAME);

        // Then
        assertNotNull(userDetails);
        assertEquals(TEST_USERNAME, userDetails.getUsername());
        assertEquals(TEST_PASSWORD, userDetails.getPassword());

        verify(userRepository, times(1)).findByUsername(TEST_USERNAME);
    }

    private User buildTestUser() {
        return User.builder()
                .username(TEST_USERNAME)
                .password(TEST_PASSWORD)
                .build();
    }

}
