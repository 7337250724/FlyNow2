# Setup Java and Build Instructions

## Issue: JAVA_HOME Not Set

You need to set the JAVA_HOME environment variable to build the project.

## Step 1: Find Your Java Installation

Run this command to find where Java is installed:

```powershell
where java
```

Or check common locations:
- `C:\Program Files\Java\jdk-17`
- `C:\Program Files\Java\jdk-21`
- `C:\Program Files\Eclipse Adoptium\jdk-17`
- `C:\Program Files\Eclipse Adoptium\jdk-21`

## Step 2: Set JAVA_HOME

### Method A: Temporary (Current Session Only)

```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
```

Replace `jdk-17` with your actual Java version folder.

### Method B: Permanent (Recommended)

1. Right-click "This PC" → Properties
2. Click "Advanced System Settings"
3. Click "Environment Variables"
4. Under "System Variables", click "New"
5. Variable name: `JAVA_HOME`
6. Variable value: Path to your JDK (e.g., `C:\Program Files\Java\jdk-17`)
7. Click OK
8. **Restart PowerShell/Command Prompt**

## Step 3: Verify Setup

```powershell
echo $env:JAVA_HOME
java -version
```

## Step 4: Build the WAR File

After setting JAVA_HOME, run:

```powershell
cd C:\Users\Admin\Downloads\FlyNow2\FlyNow2
.\mvnw.cmd clean package -DskipTests
```

## Alternative: Use Full Path to Java

If you can't set JAVA_HOME, you can specify it directly:

```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
.\mvnw.cmd clean package -DskipTests
```

## If Java is Not Installed

1. Download Java 17 or 21 from:
   - Oracle: https://www.oracle.com/java/technologies/downloads/
   - Eclipse Adoptium: https://adoptium.net/
2. Install it
3. Set JAVA_HOME as described above
4. Restart your terminal

## Quick Build Script

Create a file `build.bat` in the project root:

```batch
@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-17
call mvnw.cmd clean package -DskipTests
pause
```

Replace `jdk-17` with your actual Java version.

