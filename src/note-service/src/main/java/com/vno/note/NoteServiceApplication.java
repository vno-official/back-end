package com.vno.note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebFlux
public class NoteServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoteServiceApplication.class, args);
    }
}
