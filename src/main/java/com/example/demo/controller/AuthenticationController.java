package com.example.demo.controller;

import com.example.demo.responses.LoginResponse;
import com.example.demo.dtos.LoginUserDto;
import com.example.demo.dtos.RegisterUserWithImageDto;
import com.example.demo.entity.Student;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/auth")
@RestController
@Validated
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    // Constructor
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/signup", consumes = "multipart/form-data")
    public ResponseEntity<Student> register(@Valid @ModelAttribute RegisterUserWithImageDto registerUserWithImageDto) {
        if (authenticationService.emailExists(registerUserWithImageDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        try {
            // Handle the file upload and set the URL
            String imageUrl = authenticationService.uploadImage(registerUserWithImageDto.getImageFile());
            registerUserWithImageDto.setStudentImageUrl(imageUrl);

            // Proceed with the registration process
            Student student = authenticationService.signup(registerUserWithImageDto);
            return ResponseEntity.ok(student);
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        Student authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}
