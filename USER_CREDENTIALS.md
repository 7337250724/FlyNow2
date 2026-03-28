# User Credentials

## Admin Account
- **Username:** `admin`
- **Password:** `admin123`
- **Email:** admin@flynow.com
- **Role:** ADMIN
- **Access:** Full admin panel access at `/admin.html`

## Regular User Account
- **Username:** `ravi@1234`
- **Password:** `123456789`
- **Email:** ravi@flynow.com
- **Role:** USER
- **Access:** User dashboard and features

## Implementation Details

### Data Initializer
A `DataInitializer` class has been created that automatically creates these users when the application starts:
- Location: `src/main/java/com/example/demo/config/DataInitializer.java`
- Runs on application startup
- Only creates users if they don't already exist (prevents duplicates)

### User Service Updates
- Updated `UserService.loginUser()` to check the database instead of hardcoded admin
- Now validates user credentials from the database
- Checks if user is active before allowing login

### How to Use

1. **Start the application** - The users will be automatically created on first startup
2. **Login as Admin:**
   - Go to `http://localhost:8081/login`
   - Username: `admin`
   - Password: `admin123`
   - You'll be redirected to `/admin.html`

3. **Login as User:**
   - Go to `http://localhost:8081/login`
   - Username: `ravi@1234`
   - Password: `123456789`
   - You'll be redirected to `/dashboard`

### Notes
- Users are stored in the database (MySQL)
- Passwords are stored in plain text (for development only - should be hashed in production)
- The admin user has role "ADMIN" and regular users have role "USER"
- Both users are set to active by default

