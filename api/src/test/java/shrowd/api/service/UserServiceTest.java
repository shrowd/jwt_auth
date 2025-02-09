package shrowd.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import shrowd.api.entity.User;
import shrowd.api.enums.Role;
import shrowd.api.exception.AlreadyExistsException;
import shrowd.api.repository.UserRepo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setFirstName("test");
        testUser.setLastName("test");
        testUser.setEmail("test@test.com");
        testUser.setPassword("password");
        testUser.setRole(Role.USER);
    }

    @Test
    void shouldReturnUser() {
        when(userRepo.findByEmail("test@test.com")).thenReturn(Optional.of(testUser));

        UserDetails userDetails = userService.loadUserByUsername("test@test.com");

        assertNotNull(userDetails);
        assertEquals("test@test.com", userDetails.getUsername());
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        when(userRepo.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("unknown@test.com"));
    }

    @Test
    void shouldReturnCurrentUser() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@test.com");
        when(userRepo.findByEmail("test@test.com")).thenReturn(Optional.of(testUser));

        User user = userService.getCurrentUser();

        assertNotNull(user);
        assertEquals("test@test.com", user.getEmail());
    }

    @Test
    void shouldRegisterUser() {
        when(userRepo.existsByEmail("new@test.com")).thenReturn(false);

        userService.registerUser(
                "test",
                "test",
                "new@test.com",
                "password"
        );

        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowAlreadyExistsExceptionWhenEmailAlreadyExists() {
        when(userRepo.existsByEmail("used@test.com")).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () ->
                userService.registerUser(
                        "test",
                        "test",
                        "used@test.com",
                        "password"));
    }
}