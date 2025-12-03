package com.vno.auth.email;

/**
 * Interface for email templates.
 * Implement this interface to create different email templates.
 */
public interface EmailTemplate {
    
    /**
     * Get the email subject
     */
    String getSubject();
    
    /**
     * Get the HTML body of the email
     */
    String getHtmlBody();
    
    /**
     * Get the plain text body (optional, for fallback)
     */
    default String getTextBody() {
        return null;
    }
}
