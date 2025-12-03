package com.vno.auth.config;

import com.resend.Resend;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Configuration for Resend email service.
 * Produces a singleton Resend client for dependency injection.
 */
@ApplicationScoped
public class EmailConfig {

    @ConfigProperty(name = "resend.api-key")
    String apiKey;

    @Produces
    @Singleton
    public Resend resendClient() {
        return new Resend(apiKey);
    }
}
