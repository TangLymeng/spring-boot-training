// Java Program to Illustrate Department File

// Importing package module to code fragment
package com.example.demo.entity;

// Importing required classes
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// Annotations
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

// Class
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long departmentId;
    private String departmentName;
    private String departmentAddress;
    private String departmentCode;

    @JsonManagedReference // to avoid infinite loop
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @Override
    public String toString() {
        return "Department{" +
                "id=" + departmentId +
                ", name='" + departmentName + '\'' +
                // Avoid including fields that could cause recursion
                '}';
    }

}
