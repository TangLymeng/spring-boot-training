package com.example.demo.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dulfgdn5w",
                "api_key", "976632819488977",
                "api_secret", "ihuSBGzaBJD_v_p7eGx9okOv2uY"
        ));
    }
}
