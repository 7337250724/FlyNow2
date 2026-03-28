@echo off
REM ============================================
REM Direct Tomcat Startup - No Window Close
REM ============================================

set TOMCAT_HOME=C:\Users\Admin\Downloads\apache-tomcat-9.0.112-windows-x64\apache-tomcat-9.0.112
set JAVA_HOME=C:\Program Files\Java\jre1.8.0_471
set CATALINA_HOME=%TOMCAT_HOME%
set CATALINA_BASE=%TOMCAT_HOME%
set PATH=%JAVA_HOME%\bin;%PATH%

echo.
echo ============================================
echo Starting Apache Tomcat
echo ============================================
echo JAVA_HOME: %JAVA_HOME%
echo CATALINA_HOME: %CATALINA_HOME%
echo Port: 8081
echo.
echo ============================================
echo CREDENTIALS
echo ============================================
echo Username: admin
echo Password: admin123
echo Manager: http://localhost:8081/manager/html
echo ============================================
echo.

REM Verify Java
"%JAVA_HOME%\bin\java.exe" -version
if errorlevel 1 (
    echo ERROR: Java not accessible!
    pause
    exit /b 1
)

REM Change to Tomcat directory
cd /d "%TOMCAT_HOME%"

REM Start Tomcat in current window (so we can see errors)
echo.
echo Starting Tomcat...
echo.
call bin\catalina.bat run

REM This will keep the window open and show output
pause

