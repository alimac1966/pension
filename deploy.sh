@echo off
setlocal enabledelayedexpansion

REM ================================
REM CONFIGURATION
REM ================================
set PROJECT_ID=pension-planner-1966
set REGION=europe-west1
set REPO=quarkus
set IMAGE_NAME=pension-planner

REM ================================
REM Read current semantic version
REM ================================
if not exist version.txt (
    echo 1.0.0 > version.txt
)

set /p VERSION=<version.txt

echo Current version: %VERSION%

REM ================================
REM Parse semantic version
REM ================================
for /f "tokens=1-3 delims=." %%a in ("%VERSION%") do (
    set MAJOR=%%a
    set MINOR=%%b
    set PATCH=%%c
)

REM ================================
REM Increment patch version
REM ================================
set /a PATCH=%PATCH%+1
set NEW_VERSION=%MAJOR%.%MINOR%.%PATCH%

echo New version: %NEW_VERSION%

REM Save new version
echo %NEW_VERSION% > version.txt

REM ================================
echo Building Quarkus application...
REM ================================
call mvnw package -DskipTests
IF %ERRORLEVEL% NEQ 0 (
    echo Maven build failed!
    exit /b 1
)

REM ================================
echo Building Docker image...
REM ================================
docker build --no-cache -t %REGION%-docker.pkg.dev/%PROJECT_ID%/%REPO%/%IMAGE_NAME%:%NEW_VERSION% .
IF %ERRORLEVEL% NEQ 0 (
    echo Docker build failed!
    exit /b 1
)

REM ================================
echo Authenticating Docker with Artifact Registry...
REM ================================
REM  gcloud auth configure-docker %REGION%-docker.pkg.dev
REM  IF %ERRORLEVEL% NEQ 0 (
REM      echo Docker auth failed!
REM      exit /b 1
REM  )

REM ================================
echo Pushing image to Artifact Registry...
REM ================================
docker push %REGION%-docker.pkg.dev/%PROJECT_ID%/%REPO%/%IMAGE_NAME%:%NEW_VERSION%
IF %ERRORLEVEL% NEQ 0 (
    echo Docker push failed!
    exit /b 1
)

REM ================================
echo Deploying to Cloud Run...
REM ================================
echo Deploying image: %REGION%-docker.pkg.dev/%PROJECT_ID%/%REPO%/%IMAGE_NAME%:%NEW_VERSION%

gcloud run deploy %IMAGE_NAME% ^
  --image %REGION%-docker.pkg.dev/%PROJECT_ID%/%REPO%/%IMAGE_NAME%:%NEW_VERSION% ^
  --platform managed ^
  --region %REGION% ^
  --allow-unauthenticated

IF %ERRORLEVEL% NEQ 0 (
    echo Cloud Run deployment failed!
    exit /b 1
)

echo ============================================
echo Deployment complete!
echo Version deployed: %NEW_VERSION%
echo ============================================
endlocal
