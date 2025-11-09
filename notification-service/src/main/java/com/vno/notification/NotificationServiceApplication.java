package com.vno.notification;

import io.ably.lib.realtime.AblyRealtime;
import io.ably.lib.rest.AblyRest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class NotificationServiceApplication {

    @Value("${ABLY_API_KEY:}")
    private String ablyApiKey;

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @Bean
    public AblyRest ablyRest() throws Exception {
        return new AblyRest(ablyApiKey);
    }

    @Bean
    public AblyRealtime ablyRealtime() throws Exception {
        return new AblyRealtime(ablyApiKey);
    }
}
