@echo off
cd /d "%~dp0"
echo Compiling Java files...

REM Compile - create classes in current dir
javac -d . src\main\java\*.java

REM Check if compilation succeeded
if %ERRORLEVEL% neq 0 (
    echo Compilation failed!
    pause
    exit /b %ERRORLEVEL%
)

echo Running FileEncryptionTool...
java -cp . com.encryption.FileEncryptionTool
pause
