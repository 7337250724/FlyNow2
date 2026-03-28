@echo off
echo Building WAR file for Tomcat deployment...
echo.

REM Check if JAVA_HOME is set
if "%JAVA_HOME%"=="" (
    echo ERROR: JAVA_HOME is not set!
    echo.
    echo Please set JAVA_HOME first:
    echo 1. Find your Java installation (e.g., C:\Program Files\Java\jdk-17)
    echo 2. Set JAVA_HOME environment variable in Windows
    echo    OR run: set JAVA_HOME=C:\Program Files\Java\jdk-17
    echo.
    echo Then run this script again.
    echo.
    pause
    exit /b 1
)

echo Using Java from: %JAVA_HOME%
echo.

REM Use Maven Wrapper if available, otherwise use system Maven
if exist "mvnw.cmd" (
    call mvnw.cmd clean package -DskipTests
) else (
    echo Maven Wrapper not found. Trying system Maven...
    call mvn clean package -DskipTests
)

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo BUILD SUCCESSFUL!
    echo ========================================
    echo.
    echo WAR file created at: target\FlyNow-0.0.1-SNAPSHOT.war
    echo.
    echo To deploy:
    echo 1. Copy the WAR file to your Tomcat webapps folder
    echo 2. Rename it to FlyNow.war (or ROOT.war for root context)
    echo 3. Start Tomcat
    echo.
) else (
    echo.
    echo ========================================
    echo BUILD FAILED!
    echo ========================================
    echo.
    echo Check the error messages above.
    echo Make sure JAVA_HOME is set correctly.
    echo.
)

pause

