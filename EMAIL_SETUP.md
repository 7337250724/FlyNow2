# Email Configuration Guide

## Overview
The FlyNow application sends real-time email notifications to users when they book packages, flights, buses, trains, or hotels.

## Email Service Setup

### 1. Gmail Configuration (Recommended for Development)

#### Step 1: Enable 2-Factor Authentication
1. Go to your Google Account settings
2. Navigate to Security
3. Enable 2-Step Verification

#### Step 2: Generate App Password
1. Go to Google Account → Security
2. Under "2-Step Verification", click "App passwords"
3. Select "Mail" and "Other (Custom name)"
4. Enter "FlyNow App" as the name
5. Click "Generate"
6. Copy the 16-character password (you'll need this)

#### Step 3: Update application.properties
Edit `src/main/resources/application.properties`:

```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-16-character-app-password
```

**Important:** Use the App Password, NOT your regular Gmail password!

### 2. Other Email Providers

#### Outlook/Hotmail
```properties
spring.mail.host=smtp-mail.outlook.com
spring.mail.port=587
spring.mail.username=your-email@outlook.com
spring.mail.password=your-password
```

#### Yahoo Mail
```properties
spring.mail.host=smtp.mail.yahoo.com
spring.mail.port=587
spring.mail.username=your-email@yahoo.com
spring.mail.password=your-app-password
```

#### Custom SMTP Server
```properties
spring.mail.host=your-smtp-server.com
spring.mail.port=587
spring.mail.username=your-email@domain.com
spring.mail.password=your-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### 3. Testing Email Configuration

After updating the properties, restart the application and make a test booking. Check:
1. Console logs for email sending status
2. The recipient's inbox (and spam folder)
3. Application logs for any errors

## Email Features

### What Gets Sent
- **Booking Confirmation Email** sent immediately after booking is created
- Includes:
  - Booking reference number
  - Package details
  - Transport details (Flight/Bus/Train)
  - Hotel details
  - Traveler information
  - Price summary
  - Important travel information
  - Support contact details

### Email Template
- Location: `src/main/resources/templates/email/booking-confirmation.html`
- Professional HTML design
- Responsive layout
- Includes all booking details
- Branded with FlyNow colors

## Troubleshooting

### Email Not Sending

1. **Check Credentials**
   - Verify username and password in `application.properties`
   - For Gmail, ensure you're using App Password, not regular password

2. **Check Firewall/Network**
   - Ensure port 587 is not blocked
   - Check if your network allows SMTP connections

3. **Check Logs**
   - Look for error messages in console
   - Check for authentication failures

4. **Gmail Specific Issues**
   - Make sure "Less secure app access" is enabled (if not using App Password)
   - Or use App Password (recommended)

### Common Errors

**"Authentication failed"**
- Wrong password
- Using regular password instead of App Password (Gmail)
- Account locked or suspended

**"Connection timeout"**
- Firewall blocking port 587
- Wrong SMTP host
- Network issues

**"Email sent but not received"**
- Check spam/junk folder
- Verify recipient email address
- Check email provider's delivery logs

## Production Recommendations

1. **Use Professional Email Service**
   - Consider services like SendGrid, Mailgun, or AWS SES
   - Better deliverability and tracking

2. **Email Queue**
   - For high volume, use message queue (RabbitMQ, Kafka)
   - Prevents email sending from blocking booking process

3. **Email Templates**
   - Keep templates in version control
   - Use template variables for personalization

4. **Monitoring**
   - Monitor email delivery rates
   - Set up alerts for email failures
   - Track bounce rates

## Security Notes

- Never commit email passwords to version control
- Use environment variables for sensitive data
- Consider using Spring Cloud Config for production
- Rotate passwords regularly

