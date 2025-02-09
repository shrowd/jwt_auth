package shrowd.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shrowd.api.entity.User;
import shrowd.api.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> getAdminEndpoint() {
        User user = userService.getCurrentUser();

        return ResponseEntity.ok("Hello admin: " + user.getFirstName() + " " + user.getLastName());
    }
}
