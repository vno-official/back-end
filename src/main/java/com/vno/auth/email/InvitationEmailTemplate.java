package com.vno.auth.email;

/**
 * Email template for invitation emails.
 */
public class InvitationEmailTemplate implements EmailTemplate {
    
    private final String inviterName;
    private final String organizationName;
    private final String invitationLink;
    private final String recipientEmail;
    
    public InvitationEmailTemplate(String inviterName, String organizationName, 
                                   String invitationLink, String recipientEmail) {
        this.inviterName = inviterName;
        this.organizationName = organizationName;
        this.invitationLink = invitationLink;
        this.recipientEmail = recipientEmail;
    }
    
    @Override
    public String getSubject() {
        return String.format("%s invited you to join %s on VNO", inviterName, organizationName);
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
                    .org-name {
                        font-weight: 600;
                        color: #2563eb;
                    }
                    .button {
                        display: inline-block;
                        padding: 12px 24px;
                        background-color: #10b981;
                        color: #ffffff;
                        text-decoration: none;
                        border-radius: 6px;
                        margin: 20px 0;
                        font-weight: 600;
                    }
                    .button:hover {
                        background-color: #059669;
                    }
                    .footer {
                        margin-top: 30px;
                        padding-top: 20px;
                        border-top: 1px solid #e5e7eb;
                        font-size: 14px;
                        color: #6b7280;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>🎉 You've been invited!</h2>
                    <p><strong>%s</strong> has invited you to join <span class="org-name">%s</span> on VNO.</p>
                    <p>Click the button below to accept the invitation and get started:</p>
                    <a href="%s" class="button">Accept Invitation</a>
                    <div class="footer">
                        <p>This invitation was sent to %s</p>
                        <p>If you weren't expecting this invitation, you can safely ignore this email.</p>
                    </div>
                </div>
            </body>
            </html>
            """, inviterName, organizationName, invitationLink, recipientEmail);
    }
    
    @Override
    public String getTextBody() {
        return String.format("""
            You've been invited!
            
            %s has invited you to join %s on VNO.
            
            Click the link below to accept the invitation:
            %s
            
            This invitation was sent to %s
            
            If you weren't expecting this invitation, you can safely ignore this email.
            """, inviterName, organizationName, invitationLink, recipientEmail);
    }
}
