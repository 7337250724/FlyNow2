@echo off
echo ========================================
echo FlyNow WAR Builder for Tomcat
echo ========================================
echo.

REM Try to find Java automatically
set JAVA_FOUND=0

REM Check common Java locations
if exist "C:\Program Files\Java\jdk-21" (
    set JAVA_HOME=C:\Program Files\Java\jdk-21
    set JAVA_FOUND=1
    goto :found
)

if exist "C:\Program Files\Java\jdk-17" (
    set JAVA_HOME=C:\Program Files\Java\jdk-17
    set JAVA_FOUND=1
    goto :found
)

if exist "C:\Program Files\Eclipse Adoptium\jdk-21" (
    set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21
    set JAVA_FOUND=1
    goto :found
)

if exist "C:\Program Files\Eclipse Adoptium\jdk-17" (
    set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17
    set JAVA_FOUND=1
    goto :found
)

:found
if %JAVA_FOUND%==0 (
    echo ERROR: Java not found in common locations!
    echo.
    echo Please set JAVA_HOME manually:
    echo 1. Find your Java installation (e.g., C:\Program Files\Java\jdk-17)
    echo 2. Edit this file and set JAVA_HOME at the top
    echo 3. Or set it in Windows Environment Variables
    echo.
    pause
    exit /b 1
)

echo Found Java at: %JAVA_HOME%
echo.

REM Set JAVA_HOME for this session
set "JAVA_HOME=%JAVA_HOME%"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Building WAR file...
echo.

call mvnw.cmd clean package -DskipTests

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo BUILD SUCCESSFUL!
    echo ========================================
    echo.
    echo WAR file location: target\FlyNow-0.0.1-SNAPSHOT.war
    echo.
    echo To deploy:
    echo 1. Copy the WAR file to your Tomcat webapps folder
    echo 2. Rename to FlyNow.war (or ROOT.war for root context)
    echo 3. Start Tomcat
    echo.
) else (
    echo.
    echo ========================================
    echo BUILD FAILED!
    echo ========================================
    echo.
    echo Check the error messages above.
    echo.
)

pause

