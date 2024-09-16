package com.example.demo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.dtos.DepartmentDTO;
import com.example.demo.dtos.LoginUserDto;
import com.example.demo.dtos.RegisterUserWithImageDto;
import com.example.demo.entity.Department;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class AuthenticationService {
    private final StudentRepository studentRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final DepartmentService departmentService;

    @Autowired
    private final Cloudinary cloudinary;
    // Constructor
    public AuthenticationService(StudentRepository studentRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, DepartmentService departmentService, Cloudinary cloudinary) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.departmentService = departmentService;
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile imageFile) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }

    public Student signup(RegisterUserWithImageDto input) throws IOException {
        // Upload image to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(input.getImageFile().getBytes(), ObjectUtils.emptyMap());
        String imageUrl = uploadResult.get("url").toString();

        // Create a new student
        Student student = new Student();
        student.setStudentEmail(input.getEmail());
        student.setStudentName(input.getName());

        // Retrieve the department using the DepartmentService
        DepartmentDTO departmentDTO = departmentService.getDepartmentById(input.getStudentDepartment())
                .orElseThrow(() -> new RuntimeException("Department not found for id: " + input.getStudentDepartment()));

        // Set the department in the student entity
        Department department = new Department(); // Create a new Department instance
        department.setDepartmentId(departmentDTO.getId());
        department.setDepartmentName(departmentDTO.getName());
        department.setDepartmentAddress(departmentDTO.getAddress());
        department.setDepartmentCode(departmentDTO.getCode());

        student.setDepartment(department);
        student.setStudentPassword(passwordEncoder.encode(input.getStudentPassword()));
        student.setStudentImageUrl(imageUrl);

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

    public boolean emailExists(String email) {
        // Implement the logic to check if the email exists in the database
        return studentRepository.findByStudentEmail(email).isPresent();
    }
}
