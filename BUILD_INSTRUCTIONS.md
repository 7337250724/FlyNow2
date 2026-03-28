# How to Build WAR File for Tomcat Deployment

## Option 1: Using Maven Command Line (If Maven is Installed)

### Step 1: Add Maven to PATH (if not already added)

1. Find your Maven installation directory (e.g., `C:\Program Files\Apache\maven\bin`)
2. Add it to Windows PATH:
   - Right-click "This PC" → Properties → Advanced System Settings
   - Click "Environment Variables"
   - Under "System Variables", find "Path" and click "Edit"
   - Click "New" and add your Maven bin directory
   - Click OK on all dialogs

### Step 2: Build the WAR

Open PowerShell or Command Prompt in the project directory:

```powershell
cd C:\Users\Admin\Downloads\FlyNow2\FlyNow2
mvn clean package -DskipTests
```

The WAR file will be created at: `target\FlyNow-0.0.1-SNAPSHOT.war`

## Option 2: Using IntelliJ IDEA

1. Open the project in IntelliJ IDEA
2. Open the Maven tool window (View → Tool Windows → Maven)
3. Expand: `FlyNow` → `Lifecycle`
4. Double-click `clean` then `package`
5. The WAR file will be in `target\FlyNow-0.0.1-SNAPSHOT.war`

## Option 3: Using Eclipse

1. Right-click on the project → Run As → Maven build...
2. In "Goals", enter: `clean package -DskipTests`
3. Click Run
4. The WAR file will be in `target\FlyNow-0.0.1-SNAPSHOT.war`

## Option 4: Using Maven Wrapper (If Available)

If the project has `mvnw` or `mvnw.cmd`:

```powershell
cd C:\Users\Admin\Downloads\FlyNow2\FlyNow2
.\mvnw.cmd clean package -DskipTests
```

## After Building

1. Navigate to: `C:\Users\Admin\Downloads\FlyNow2\FlyNow2\target`
2. You'll find: `FlyNow-0.0.1-SNAPSHOT.war`
3. Copy this file to your Tomcat `webapps` folder
4. Rename to `FlyNow.war` (or `ROOT.war` for root context)
5. Start Tomcat

## Quick Check: Is Maven Installed?

Run this command to check:

```powershell
mvn --version
```

If you get an error, Maven is not installed or not in PATH.

## Install Maven (If Not Installed)

1. Download from: https://maven.apache.org/download.cgi
2. Extract to a folder (e.g., `C:\Program Files\Apache\maven`)
3. Add to PATH as described in Option 1
4. Restart your terminal/PowerShell

