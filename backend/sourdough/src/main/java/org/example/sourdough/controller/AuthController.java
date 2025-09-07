package org.example.sourdough.controller;

import jakarta.validation.Valid;
import org.example.sourdough.model.User;
import org.example.sourdough.model.dto.UserDto;
import org.example.sourdough.service.users.AuthService;
import org.example.sourdough.service.users.AuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * this is a test class for the register and login functionality implementations
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            String message = authService.registerUser(userDto);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
        try {
            User user = authService.loginUser(userDto.getEmail(), userDto.getPassword());
            //TODO: generate token here instead of the response code
            return ResponseEntity.ok("Login successful for user: " + user.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
