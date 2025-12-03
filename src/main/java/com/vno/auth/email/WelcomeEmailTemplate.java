package com.vno.auth.email;

/**
 * Email template for welcome emails to new users.
 */
public class WelcomeEmailTemplate implements EmailTemplate {
    
    private final String userName;
    private final String organizationName;
    private final String dashboardUrl;
    
    public WelcomeEmailTemplate(String userName, String organizationName, String dashboardUrl) {
        this.userName = userName != null ? userName : "there";
        this.organizationName = organizationName;
        this.dashboardUrl = dashboardUrl;
    }
    
    @Override
    public String getSubject() {
        return "Welcome to VNO! 🎉";
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
                    .features {
                        background-color: #f3f4f6;
                        border-radius: 6px;
                        padding: 20px;
                        margin: 20px 0;
                    }
                    .features ul {
                        margin: 0;
                        padding-left: 20px;
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
                    <h2>🎉 Welcome to VNO!</h2>
                    <p>Hi %s,</p>
                    <p>We're excited to have you on board! Your organization <strong>%s</strong> is all set up and ready to go.</p>
                    
                    <div class="features">
                        <h3>Here's what you can do:</h3>
                        <ul>
                            <li>Create and manage workspaces</li>
                            <li>Collaborate with your team</li>
                            <li>Organize your pages and content</li>
                            <li>Invite team members</li>
                        </ul>
                    </div>
                    
                    <p>Ready to get started?</p>
                    <a href="%s" class="button">Go to Dashboard</a>
                    
                    <div class="footer">
                        <p>Need help? Check out our documentation or reach out to our support team.</p>
                        <p>Happy collaborating! 🚀</p>
                    </div>
                </div>
            </body>
            </html>
            """, userName, organizationName, dashboardUrl);
    }
    
    @Override
    public String getTextBody() {
        return String.format("""
            Welcome to VNO!
            
            Hi %s,
            
            We're excited to have you on board! Your organization %s is all set up and ready to go.
            
            Here's what you can do:
            - Create and manage workspaces
            - Collaborate with your team
            - Organize your pages and content
            - Invite team members
            
            Ready to get started? Visit your dashboard:
            %s
            
            Need help? Check out our documentation or reach out to our support team.
            
            Happy collaborating!
            """, userName, organizationName, dashboardUrl);
    }
}
