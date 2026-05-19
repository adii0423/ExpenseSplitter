@echo off

echo Compiling...
javac -cp lib/gson-2.10.1.jar -d out src/main/java/splitter/*.java

if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b
)

echo Running...
java -cp out;lib/gson-2.10.1.jar splitter.Main
pause