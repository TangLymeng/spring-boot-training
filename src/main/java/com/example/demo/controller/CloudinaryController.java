// src/main/java/com/example/demo/controller/CloudinaryController.java
package com.example.demo.controller;

import com.example.demo.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/media")
public class CloudinaryController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<Map> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Map uploadResult = cloudinaryService.uploadFile(file);
            return ResponseEntity.ok(uploadResult);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to upload file"));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map> deleteFile(@RequestParam("public_id") String publicId) {
        try {
            Map deleteResult = cloudinaryService.deleteFile(publicId);
            return ResponseEntity.ok(deleteResult);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to delete file"));
        }
    }
}