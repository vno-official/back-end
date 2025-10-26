package com.vno.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryApplication {
  public static void main(String[] args) {
    SpringApplication.run(ServiceDiscoveryApplication.class, args);
  }

  @Configuration
  @EnableWebSecurity
  static class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http.csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
          .httpBasic(basic -> {})
          .formLogin(form -> form.disable());
      return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
      UserDetails user =
          User.withDefaultPasswordEncoder()
              .username("eureka")
              .password("eureka-secret")
              .roles("USER")
              .build();
      return new InMemoryUserDetailsManager(user);
    }
  }
}
