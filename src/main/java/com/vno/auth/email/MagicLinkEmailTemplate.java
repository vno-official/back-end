package com.vno.auth.email;

/**
 * Email template for magic link authentication.
 */
public class MagicLinkEmailTemplate implements EmailTemplate {
    
    private final String magicLink;
    private final String recipientName;
    
    public MagicLinkEmailTemplate(String magicLink, String recipientName) {
        this.magicLink = magicLink;
        this.recipientName = recipientName != null ? recipientName : "there";
    }
    
    public MagicLinkEmailTemplate(String magicLink) {
        this(magicLink, null);
    }
    
    @Override
    public String getSubject() {
        return "Sign in to VNO";
    }
    
    @Override
    public String getHtmlBody() {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body {
                        font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
                        line-height: 1.6;
                        color: #333;
                        max-width: 600px;
                        margin: 0 auto;
                        padding: 20px;
                    }
                    .container {
                        background-color: #ffffff;
                        border-radius: 8px;
                        padding: 40px;
                        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                    }
                    h2 {
                        color: #2563eb;
                        margin-top: 0;
                    }
                    .button {
                        display: inline-block;
                        padding: 12px 24px;
                        background-color: #2563eb;
                        color: #ffffff;
                        text-decoration: none;
                        border-radius: 6px;
                        margin: 20px 0;
                        font-weight: 600;
                    }
                    .button:hover {
                        background-color: #1d4ed8;
                    }
                    .footer {
                        margin-top: 30px;
                        padding-top: 20px;
                        border-top: 1px solid #e5e7eb;
                        font-size: 14px;
                        color: #6b7280;
                    }
                    .warning {
                        background-color: #fef3c7;
                        border-left: 4px solid #f59e0b;
                        padding: 12px;
                        margin: 20px 0;
                        border-radius: 4px;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>🔐 Your Magic Link</h2>
                    <p>Hi %s,</p>
                    <p>Click the button below to sign in to your VNO account:</p>
                    <a href="%s" class="button">Sign In to VNO</a>
                    <div class="warning">
                        <strong>⏱️ This link will expire in 15 minutes.</strong>
                    </div>
                    <p>If you didn't request this link, you can safely ignore this email.</p>
                    <div class="footer">
                        <p>For security reasons, this link can only be used once.</p>
                        <p>If you're having trouble clicking the button, copy and paste this URL into your browser:</p>
                        <p style="word-break: break-all; color: #2563eb;">%s</p>
                    </div>
                </div>
            </body>
            </html>
            """, recipientName, magicLink, magicLink);
    }
    
    @Override
    public String getTextBody() {
        return String.format("""
            Your Magic Link
            
            Hi %s,
            
            Click the link below to sign in to your VNO account:
            %s
            
            This link will expire in 15 minutes.
            
            If you didn't request this link, you can safely ignore this email.
            
            For security reasons, this link can only be used once.
            """, recipientName, magicLink);
    }
}
