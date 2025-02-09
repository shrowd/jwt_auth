package shrowd.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shrowd.api.dto.request.LoginRequest;
import shrowd.api.dto.request.RegisterRequest;
import shrowd.api.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        authService.validateCredentials(request.email(), request.password());
        String jwtToken = authService.getJwtToken(request.email());

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        authService.registerUser(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.password()
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
