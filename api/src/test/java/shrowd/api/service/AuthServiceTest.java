package shrowd.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shrowd.api.config.AppUser;
import shrowd.api.config.jwt.UserAuthenticationProvider;
import shrowd.api.entity.User;
import shrowd.api.enums.Role;
import shrowd.api.exception.BadRequestException;
import shrowd.api.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private UserAuthenticationProvider userAuthenticationProvider;

    @InjectMocks
    private AuthService authService;

    private AppUser testUser;
    private String email;
    private String jwtToken;


    @BeforeEach
    void setUp() {
        email = "test@test.com";
        jwtToken = "jwtToken";

        User user = new User();
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode("password"));
        user.setRole(Role.USER);
        testUser = new AppUser(user);
    }

    @Test
    void shouldCallRegisterUserFromUserService() {
        String firstName = "test";
        String lastName = "test";
        String email = "test@test.com";
        String password = "password";

        authService.registerUser(firstName, lastName, email, password);

        verify(userService, times(1)).registerUser(firstName, lastName, email, password);
    }

    @Test
    void shouldValidateCredentials() {
        when(userService.loadUserByUsername(email)).thenReturn(testUser);

        assertDoesNotThrow(() -> authService.validateCredentials(email, "password"));
    }

    @Test
    void shouldThrowBadRequestExceptionWhenPasswordsMismatch() {
        when(userService.loadUserByUsername(email)).thenReturn(testUser);

        assertThrows(BadRequestException.class,
                () -> authService.validateCredentials(email, "wrong_password"));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUserWithGivenEmailDoesntExists() {
        when(userService.loadUserByUsername(email)).thenThrow(UsernameNotFoundException.class);

        assertThrows(ResourceNotFoundException.class,
                () -> authService.validateCredentials(email, "password"));
    }

    @Test
    void shouldReturnGeneratedJwtToken() {
        when(userService.loadUserByUsername(email)).thenReturn(testUser);
        when(userAuthenticationProvider.createToken(email, Role.USER.name())).thenReturn(jwtToken);

        String result = authService.getJwtToken(email);

        assertEquals(jwtToken, result);
        verify(userAuthenticationProvider, times(1)).createToken(email, Role.USER.name());
    }

}