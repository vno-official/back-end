package com.vno.auth.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import java.util.Map;

@ApplicationScoped
public class EmailService {

    private static final Logger LOG = Logger.getLogger(EmailService.class);

    @ConfigProperty(name = "resend.api-key")
    String resendApiKey;

    @ConfigProperty(name = "resend.from-email", defaultValue = "noreply@vno.com")
    String fromEmail;

    @ConfigProperty(name = "app.domain", defaultValue = "vno.com")
    String appDomain;

    public void sendMagicLink(String toEmail, String token) {
        String magicLink = String.format("https://%s/auth/callback?token=%s", appDomain, token);
        
        String htmlBody = String.format("""
            <html>
            <body>
                <h2>Your Magic Link</h2>
                <p>Click the link below to sign in to your account:</p>
                <p><a href="%s">Sign In</a></p>
                <p>This link will expire in 15 minutes.</p>
                <p>If you didn't request this, you can safely ignore this email.</p>
            </body>
            </html>
            """, magicLink);

        Map<String, Object> payload = Map.of(
            "from", fromEmail,
            "to", new String[]{toEmail},
            "subject", "Sign in to VNO",
            "html", htmlBody
        );

        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target("https://api.resend.com/emails")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + resendApiKey)
                .post(Entity.json(payload));

            if (response.getStatus() >= 200 && response.getStatus() < 300) {
                LOG.infof("Magic link email sent to %s", toEmail);
            } else {
                LOG.errorf("Failed to send email: %d - %s", response.getStatus(), response.readEntity(String.class));
            }
        } catch (Exception e) {
            LOG.error("Error sending email", e);
        }
    }
}
