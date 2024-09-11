// src/main/java/com/example/demo/controller/ThirdPartyApiController.java
package com.example.demo.controller;

import com.example.demo.service.ThirdPartyApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThirdPartyApiController {

    @Autowired
    private ThirdPartyApiService thirdPartyApiService;

    @GetMapping("/fetch-third-party-data")
    public String getData() {
        // Call the third-party API service to get weather data
        return thirdPartyApiService.getCovidData();
    }
}