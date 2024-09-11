// src/main/java/com/example/demo/service/ThirdPartyApiService.java
package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ThirdPartyApiService {

    @Autowired
    private RestTemplate restTemplate;

    public String getCovidData() {
        // The API endpoint to call
        String url = "https://covid-193.p.rapidapi.com/countries";
        // Make a GET request to the third-party API
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", "8e786318e3msh3128d9a1f3da4f6p1cbee0jsn4ecea8869510");
        headers.set("x-rapidapi-host", "covid-193.p.rapidapi.com");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        // Handle the response
        if (response.getStatusCode().is2xxSuccessful()) {
            // Return the body of the response (the weather data)
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to call third-party API. Status code: " + response.getStatusCode());
        }
    }
}