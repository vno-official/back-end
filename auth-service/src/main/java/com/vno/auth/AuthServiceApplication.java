package com.vno.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.vno.auth.client")
@EnableJpaAuditing
@OpenAPIDefinition(
    info = @Info(
        title = "Auth Service API",
        version = "1.0",
        description = "Authentication and Authorization Service"
    ),
    servers = @Server(url = "/api/auth", description = "Gateway-routed")
)
public class AuthServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthServiceApplication.class, args);
  }
} 
