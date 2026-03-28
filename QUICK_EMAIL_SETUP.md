# Quick Email Setup Guide

## Step 1: Configure Gmail (Recommended)

1. **Enable 2-Step Verification** in your Google Account
2. **Generate App Password**:
   - Go to: https://myaccount.google.com/apppasswords
   - Select "Mail" and "Other (Custom name)"
   - Name it "FlyNow App"
   - Copy the 16-character password

## Step 2: Update application.properties

Edit `src/main/resources/application.properties`:

```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-16-character-app-password-here
```

**Replace:**
- `your-email@gmail.com` with your Gmail address
- `your-16-character-app-password-here` with the App Password you generated

## Step 3: Restart Application

Restart your Spring Boot application for changes to take effect.

## Step 4: Test

1. Make a test booking (package, flight, or hotel)
2. Check the recipient's email inbox
3. Check console logs for email sending status

## Email Features

✅ **Automatic Email Notifications** sent for:
- Package bookings (with transport and hotel selections)
- Standalone flight bookings
- Standalone hotel bookings

✅ **Email Includes:**
- Booking confirmation with reference number
- All booking details (transport, hotel, travelers)
- Price summary
- Important travel information
- Support contact details

## Troubleshooting

**Email not sending?**
- Verify App Password is correct (not regular password)
- Check console logs for errors
- Ensure port 587 is not blocked by firewall
- Check spam/junk folder

**Need help?** See `EMAIL_SETUP.md` for detailed configuration options.

