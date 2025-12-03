package com.vno.auth.email;

import com.vno.auth.service.EmailService;

/**
 * Example usage of the EmailService.
 * This class demonstrates various ways to send emails using the Resend integration.
 */
public class EmailServiceExamples {

    // Note: In real code, inject EmailService using @Inject
    // private EmailService emailService;

    /**
     * Example 1: Send a magic link email (simplest way)
     */
    public void example1_SimpleMagicLink(EmailService emailService) {
        String userEmail = "user@example.com";
        String token = "abc123token";
        
        // This is the simplest way - one line!
        emailService.sendMagicLink(userEmail, token);
    }

    /**
     * Example 2: Send a magic link asynchronously (non-blocking)
     */
    public void example2_AsyncMagicLink(EmailService emailService) {
        String userEmail = "user@example.com";
        String token = "abc123token";
        
        // Send asynchronously - doesn't block the current thread
        emailService.sendMagicLinkAsync(userEmail, token)
            .thenAccept(result -> {
                System.out.println("Email sent successfully!");
            })
            .exceptionally(error -> {
                System.err.println("Failed to send email: " + error.getMessage());
                return null;
            });
    }

    /**
     * Example 3: Send a welcome email to a new user
     */
    public void example3_WelcomeEmail(EmailService emailService) {
        String userEmail = "newuser@example.com";
        String userName = "John Doe";
        String organizationName = "Acme Corp";
        String dashboardUrl = "https://app.vno.com/dashboard";
        
        WelcomeEmailTemplate template = new WelcomeEmailTemplate(
            userName,
            organizationName,
            dashboardUrl
        );
        
        try {
            String emailId = emailService.sendEmail(userEmail, template, "VNO Team");
            System.out.println("Welcome email sent! ID: " + emailId);
        } catch (Exception e) {
            System.err.println("Failed to send welcome email: " + e.getMessage());
        }
    }

    /**
     * Example 4: Send an invitation email
     */
    public void example4_InvitationEmail(EmailService emailService) {
        String recipientEmail = "colleague@example.com";
        String inviterName = "Jane Smith";
        String organizationName = "Tech Startup Inc";
        String invitationToken = "inv_xyz789";
        
        String invitationLink = String.format(
            "https://app.vno.com/invite?token=%s",
            invitationToken
        );
        
        InvitationEmailTemplate template = new InvitationEmailTemplate(
            inviterName,
            organizationName,
            invitationLink,
            recipientEmail
        );
        
        // Send asynchronously
        emailService.sendEmailAsync(recipientEmail, template)
            .thenAccept(emailId -> {
                System.out.println("Invitation sent! Email ID: " + emailId);
            });
    }

    /**
     * Example 5: Send multiple emails in batch (async)
     */
    public void example5_BatchEmails(EmailService emailService) {
        String[] recipients = {
            "user1@example.com",
            "user2@example.com",
            "user3@example.com"
        };
        
        String token = "magic_token_123";
        
        // Send to all recipients asynchronously
        for (String email : recipients) {
            emailService.sendMagicLinkAsync(email, token)
                .thenAccept(result -> {
                    System.out.println("Email sent to: " + email);
                })
                .exceptionally(error -> {
                    System.err.println("Failed for " + email + ": " + error.getMessage());
                    return null;
                });
        }
    }

    /**
     * Example 6: Create and use a custom email template
     */
    public void example6_CustomTemplate(EmailService emailService) {
        String userEmail = "user@example.com";
        
        // Create a custom inline template
        EmailTemplate customTemplate = new EmailTemplate() {
            @Override
            public String getSubject() {
                return "Custom Notification";
            }

            @Override
            public String getHtmlBody() {
                return """
                    <html>
                    <body>
                        <h2>Hello!</h2>
                        <p>This is a custom email template.</p>
                    </body>
                    </html>
                    """;
            }

            @Override
            public String getTextBody() {
                return "Hello! This is a custom email template.";
            }
        };
        
        try {
            emailService.sendEmail(userEmail, customTemplate);
        } catch (Exception e) {
            System.err.println("Failed: " + e.getMessage());
        }
    }

    /**
     * Example 7: Send email with error handling and retry logic
     */
    public void example7_WithRetry(EmailService emailService) {
        String userEmail = "user@example.com";
        String token = "token123";
        
        sendWithRetry(emailService, userEmail, token, 3);
    }
    
    private void sendWithRetry(EmailService emailService, String email, 
                              String token, int maxRetries) {
        emailService.sendMagicLinkAsync(email, token)
            .exceptionally(error -> {
                if (maxRetries > 0) {
                    System.out.println("Retrying... Attempts left: " + maxRetries);
                    sendWithRetry(emailService, email, token, maxRetries - 1);
                } else {
                    System.err.println("Failed after all retries: " + error.getMessage());
                }
                return null;
            });
    }

    /**
     * Example 8: Using in a REST endpoint
     */
    // @POST
    // @Path("/send-magic-link")
    // public Response sendMagicLinkEndpoint(
    //     @Inject EmailService emailService,
    //     @QueryParam("email") String email
    // ) {
    //     String token = generateToken(); // Your token generation logic
    //     
    //     try {
    //         emailService.sendMagicLink(email, token);
    //         return Response.ok()
    //             .entity(Map.of("message", "Magic link sent!"))
    //             .build();
    //     } catch (Exception e) {
    //         return Response.serverError()
    //             .entity(Map.of("error", "Failed to send email"))
    //             .build();
    //     }
    // }
}
