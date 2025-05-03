package com.krajust.criti_cloud_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class CritiCloudBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CritiCloudBackApplication.class, args);
    }

    @GetMapping("/test")
    public String test() {
        return "Hello CritiCloudBack!";
    }

}
