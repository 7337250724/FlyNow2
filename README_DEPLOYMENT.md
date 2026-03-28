# Quick Deployment Guide

## Current Issue

You're getting an error because:
1. **JAVA_HOME is not set** - Java needs to be configured
2. **Maven might not be in PATH** - But we can use Maven Wrapper (`mvnw.cmd`)

## Quick Solution

### Option 1: Use the Smart Build Script (Easiest)

1. Navigate to: `C:\Users\Admin\Downloads\FlyNow2\FlyNow2`
2. Double-click: `build-war-with-java.bat`
3. This script will try to find Java automatically

### Option 2: Set JAVA_HOME Manually

1. **Find your Java installation:**
   - Check: `C:\Program Files\Java\`
   - Check: `C:\Program Files\Eclipse Adoptium\`
   - Or search for "java.exe" on your system

2. **Set JAVA_HOME in PowerShell:**
   ```powershell
   $env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
   ```
   (Replace with your actual Java path)

3. **Build the WAR:**
   ```powershell
   cd C:\Users\Admin\Downloads\FlyNow2\FlyNow2
   .\mvnw.cmd clean package -DskipTests
   ```

### Option 3: Use an IDE (Recommended if Java setup is complex)

**IntelliJ IDEA:**
1. Open the project
2. View → Tool Windows → Maven
3. Run: `clean` then `package`

**Eclipse:**
1. Right-click project → Run As → Maven build...
2. Goals: `clean package -DskipTests`
3. Run

## After Building

The WAR file will be at:
```
C:\Users\Admin\Downloads\FlyNow2\FlyNow2\target\FlyNow-0.0.1-SNAPSHOT.war
```

## Deploy to Tomcat

1. **Stop Tomcat** (if running)
2. **Copy the WAR file** to: `[TOMCAT_HOME]\webapps\`
3. **Rename** to `FlyNow.war` (or `ROOT.war` for root context)
4. **Start Tomcat**
5. **Access** at: `http://localhost:8080/FlyNow`

## Need Help?

- See `SETUP_JAVA_MAVEN.md` for detailed Java setup
- See `DEPLOYMENT_GUIDE.md` for full deployment guide
- See `BUILD_INSTRUCTIONS.md` for build options

