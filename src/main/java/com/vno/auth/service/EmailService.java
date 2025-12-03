package com.vno.auth.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import com.vno.auth.email.EmailTemplate;
import com.vno.auth.email.MagicLinkEmailTemplate;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.util.concurrent.CompletableFuture;

/**
 * Service for sending emails using Resend.
 * Supports both synchronous and asynchronous email sending.
 */
@ApplicationScoped
public class EmailService {

    private static final Logger LOG = Logger.getLogger(EmailService.class);

    @Inject
    Resend resendClient;

    @ConfigProperty(name = "resend.from-email")
    String fromEmail;

    @ConfigProperty(name = "app.domain")
    String appDomain;

    /**
     * Send an email synchronously using a template.
     *
     * @param toEmail   Recipient email address
     * @param template  Email template to use
     * @return Email ID from Resend
     * @throws ResendException if email sending fails
     */
    public String sendEmail(String toEmail, EmailTemplate template) throws ResendException {
        return sendEmail(toEmail, template, null);
    }

    /**
     * Send an email synchronously using a template with a custom from name.
     *
     * @param toEmail   Recipient email address
     * @param template  Email template to use
     * @param fromName  Optional sender name (e.g., "VNO Team")
     * @return Email ID from Resend
     * @throws ResendException if email sending fails
     */
    public String sendEmail(String toEmail, EmailTemplate template, String fromName) throws ResendException {
        String from = fromName != null ? fromName + " <" + fromEmail + ">" : fromEmail;

        CreateEmailOptions.Builder emailBuilder = CreateEmailOptions.builder()
                .from(from)
                .to(toEmail)
                .subject(template.getSubject())
                .html(template.getHtmlBody());

        // Add text body if available
        if (template.getTextBody() != null) {
            emailBuilder.text(template.getTextBody());
        }

        CreateEmailOptions params = emailBuilder.build();

        try {
            CreateEmailResponse response = resendClient.emails().send(params);
            LOG.infof("Email sent successfully to %s. Email ID: %s", toEmail, response.getId());
            return response.getId();
        } catch (ResendException e) {
            LOG.errorf(e, "Failed to send email to %s", toEmail);
            throw e;
        }
    }

    /**
     * Send an email asynchronously using a template.
     *
     * @param toEmail   Recipient email address
     * @param template  Email template to use
     * @return CompletableFuture with email ID
     */
    public CompletableFuture<String> sendEmailAsync(String toEmail, EmailTemplate template) {
        return sendEmailAsync(toEmail, template, null);
    }

    /**
     * Send an email asynchronously using a template with a custom from name.
     *
     * @param toEmail   Recipient email address
     * @param template  Email template to use
     * @param fromName  Optional sender name
     * @return CompletableFuture with email ID
     */
    public CompletableFuture<String> sendEmailAsync(String toEmail, EmailTemplate template, String fromName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return sendEmail(toEmail, template, fromName);
            } catch (ResendException e) {
                LOG.errorf(e, "Async email sending failed for %s", toEmail);
                throw new RuntimeException("Failed to send email", e);
            }
        });
    }

    /**
     * Send an email asynchronously using Mutiny Uni (Quarkus reactive).
     *
     * @param toEmail   Recipient email address
     * @param template  Email template to use
     * @return Uni with email ID
     */
    public Uni<String> sendEmailReactive(String toEmail, EmailTemplate template) {
        return sendEmailReactive(toEmail, template, null);
    }

    /**
     * Send an email asynchronously using Mutiny Uni with a custom from name.
     *
     * @param toEmail   Recipient email address
     * @param template  Email template to use
     * @param fromName  Optional sender name
     * @return Uni with email ID
     */
    public Uni<String> sendEmailReactive(String toEmail, EmailTemplate template, String fromName) {
        return Uni.createFrom().item(() -> {
            try {
                return sendEmail(toEmail, template, fromName);
            } catch (ResendException e) {
                throw new RuntimeException("Failed to send email", e);
            }
        });
    }

    /**
     * Convenience method to send a magic link email.
     *
     * @param toEmail Recipient email address
     * @param token   Magic link token
     */
    public void sendMagicLink(String toEmail, String token) {
        String magicLink = String.format("https://%s/auth/callback?token=%s", appDomain, token);
        MagicLinkEmailTemplate template = new MagicLinkEmailTemplate(magicLink);

        try {
            sendEmail(toEmail, template, "VNO");
        } catch (ResendException e) {
            LOG.errorf(e, "Failed to send magic link to %s", toEmail);
            // Don't throw exception to avoid breaking the auth flow
            // The user can request a new magic link
        }
    }

    /**
     * Convenience method to send a magic link email asynchronously.
     *
     * @param toEmail Recipient email address
     * @param token   Magic link token
     * @return CompletableFuture that completes when email is sent
     */
    public CompletableFuture<Void> sendMagicLinkAsync(String toEmail, String token) {
        String magicLink = String.format("https://%s/auth/callback?token=%s", appDomain, token);
        MagicLinkEmailTemplate template = new MagicLinkEmailTemplate(magicLink);

        return sendEmailAsync(toEmail, template, "VNO")
                .thenAccept(emailId -> LOG.infof("Magic link sent to %s with email ID: %s", toEmail, emailId))
                .exceptionally(throwable -> {
                    LOG.errorf(throwable, "Failed to send magic link to %s", toEmail);
                    return null;
                });
    }
}
