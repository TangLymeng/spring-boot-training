package com.example.demo.service;

import com.example.demo.dtos.LoginUserDto;
import com.example.demo.dtos.RegisterUserDto;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final StudentRepository studentRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    // Constructor
    public AuthenticationService(StudentRepository studentRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Student signup(RegisterUserDto input) {
        // Create a new student
        Student student = new Student();
        student.setStudentEmail(input.getEmail());
        student.setStudentName(input.getName());
        student.setStudentDepartment(input.getStudentDepartment());
        student.setStudentPassword(passwordEncoder.encode(input.getPassword()));

        return studentRepository.save(student);
    }

    public Student authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return studentRepository.findByStudentEmail(input.getEmail())
                .orElseThrow();
    }
}
