package com.krajust.criti_cloud_back;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@RestController
@SpringBootApplication
public class CritiCloudBackApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CritiCloudBackApplication.class, args);
    }

    @GetMapping("/test")
    public String test() {
        return "Hello CritiCloudBack!";
    }

    @Override
    public void run(String... args) {
        System.out.println(">>>> Starting app");
    }
}
