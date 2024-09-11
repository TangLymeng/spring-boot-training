package com.example.demo.controller;

import com.example.demo.responses.LoginResponse;
import com.example.demo.dtos.LoginUserDto;
import com.example.demo.dtos.RegisterUserDto;
import com.example.demo.entity.Student;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/signup")
    public ResponseEntity<Student> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        if (authenticationService.emailExists(registerUserDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Student student = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(student);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        Student authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}
