# Resend Email Integration - Quick Start

## 🚀 Setup (3 Steps)

### 1. Get Your API Key
```bash
# Visit https://resend.com/api-keys and create an API key
# Then set it as an environment variable:
export RESEND_API_KEY="re_your_api_key_here"
```

### 2. Verify Your Domain
- Go to https://resend.com/domains
- Add and verify your domain (e.g., `vno.com`)
- Update `RESEND_FROM_EMAIL` to use your verified domain

### 3. Configure (Optional)
```bash
# Optional: Customize sender email and name
export RESEND_FROM_EMAIL="noreply@yourdomain.com"
export RESEND_FROM_NAME="Your App Name"
```

## 📧 Usage Examples

### Send Magic Link (Simplest)
```java
@Inject
EmailService emailService;

public void sendMagicLink(String email, String token) {
    emailService.sendMagicLink(email, token);
}
```

### Send Welcome Email
```java
WelcomeEmailTemplate template = new WelcomeEmailTemplate(
    "John Doe",           // userName
    "Acme Corp",          // organizationName
    "https://app.vno.com" // dashboardUrl
);

emailService.sendEmail("user@example.com", template, "VNO Team");
```

### Send Invitation
```java
InvitationEmailTemplate template = new InvitationEmailTemplate(
    "Jane Smith",         // inviterName
    "Tech Startup",       // organizationName
    invitationLink,       // invitationLink
    "colleague@example.com" // recipientEmail
);

emailService.sendEmailAsync("colleague@example.com", template);
```

### Async Sending (Non-blocking)
```java
emailService.sendMagicLinkAsync(email, token)
    .thenAccept(emailId -> LOG.info("Sent: " + emailId))
    .exceptionally(error -> {
        LOG.error("Failed: " + error);
        return null;
    });
```

## 📁 Files Created

### Core Components
- `EmailConfig.java` - Resend client configuration
- `EmailService.java` - Main service (sync/async sending)
- `EmailTemplate.java` - Template interface

### Email Templates
- `MagicLinkEmailTemplate.java` - Passwordless auth
- `InvitationEmailTemplate.java` - Team invitations
- `WelcomeEmailTemplate.java` - New user onboarding

### Documentation & Examples
- `EmailServiceExamples.java` - Code examples
- `RESEND_EMAIL_GUIDE.md` - Complete documentation

## 🎨 Template Features

All templates include:
- ✅ Modern, responsive HTML design
- ✅ Plain text fallback
- ✅ Professional styling
- ✅ Mobile-friendly

## 🔧 Configuration (application.yml)

```yaml
resend:
  api-key: ${RESEND_API_KEY:re_dev_placeholder_key}
  from-email: ${RESEND_FROM_EMAIL:noreply@vno.com}
  from-name: ${RESEND_FROM_NAME:VNO}
```

## 📚 Full Documentation

See `RESEND_EMAIL_GUIDE.md` for:
- Creating custom templates
- Batch sending
- Error handling & retry logic
- Scaling considerations
- Testing strategies
- Troubleshooting

## 🛠️ Dependencies

Already added to `build.gradle.kts`:
```kotlin
implementation("com.resend:resend-java:3.0.0")
```

## ✅ Ready to Use!

The integration is complete and ready to use. Just:
1. Set your `RESEND_API_KEY`
2. Verify your domain
3. Start sending emails!

## 📞 Need Help?

- **Implementation questions**: Check `RESEND_EMAIL_GUIDE.md`
- **Code examples**: See `EmailServiceExamples.java`
- **Resend docs**: https://resend.com/docs
