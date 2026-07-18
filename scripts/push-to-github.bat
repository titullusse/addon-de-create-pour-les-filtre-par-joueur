@echo off
REM Script pour pusher Create Player Filter sur GitHub (Windows)
REM Usage: push-to-github.bat

setlocal enabledelayedexpansion

set REPO_URL=https://github.com/titullusse/addon-de-create-pour-les-filtre-par-joueur.git
set BRANCH=addon-de-create-pour-les-filtre-par-joueur
set SOURCE_DIR=%USERPROFILE%\Downloads\CreatePlayerFilter
set WORK_DIR=%TEMP%\github-push

echo.
echo ════════════════════════════════════════════════════════════
echo.
echo   Pushing Create Player Filter to GitHub
echo.
echo ════════════════════════════════════════════════════════════
echo.

REM Vérifier que Git est installé
git --version >nul 2>&1
if errorlevel 1 (
    echo ❌ ERROR: Git is not installed or not in PATH
    echo Install Git from: https://git-scm.com/download/win
    pause
    exit /b 1
)

echo ✓ Git found
echo.

REM Vérifier que les fichiers source existent
if not exist "%SOURCE_DIR%" (
    echo ❌ ERROR: Source directory not found: %SOURCE_DIR%
    echo.
    echo Veuillez d'abord télécharger et extraire CreatePlayerFilter.zip
    echo dans: %USERPROFILE%\Downloads\
    pause
    exit /b 1
)

echo ✓ Source directory found: %SOURCE_DIR%
echo.

REM Créer le répertoire de travail
if exist "%WORK_DIR%" (
    rmdir /s /q "%WORK_DIR%"
)
mkdir "%WORK_DIR%"
cd /d "%WORK_DIR%"

echo ✓ Working directory created
echo.

REM Cloner le repo
echo [1/5] Cloning repository...
git clone "%REPO_URL%" repo
if errorlevel 1 (
    echo ❌ ERROR: Failed to clone repository
    pause
    exit /b 1
)

cd repo
echo ✓ Repository cloned
echo.

REM Checkout la branche
echo [2/5] Checking out branch: %BRANCH%
git checkout %BRANCH% 2>nul || (
    git checkout -b %BRANCH%
)

echo ✓ Branch ready
echo.

REM Copier les fichiers
echo [3/5] Copying files from %SOURCE_DIR%
REM Supprimer les fichiers existants (sauf .git)
for /d %%D in (*) do (
    if not "%%D"==".git" (
        rmdir /s /q "%%D"
    )
)
for %%F in (*) do (
    if not "%%F"==".git" (
        del "%%F"
    )
)

REM Copier les nouveaux fichiers
xcopy "%SOURCE_DIR%\*" "." /E /I /Y

echo ✓ Files copied
echo.

REM Afficher le statut
echo [4/5] Git status:
git status
echo.

REM Ajouter les fichiers
git add .

REM Commit
echo [5/5] Creating commit...
git commit -m "feat: Add Create Player Filter addon v1.0.0" -m "- Player Sorting Chest block for automatic item sorting by player" -m "- Player Owner Filter for Create Mod integration" -m "- NBT tag system for tracking item ownership" -m "- /cpf tag and /cpf untag commands" -m "- Complete documentation in French" -m "- Gradle build configuration (NeoForge 1.21.1)" -m "- Spigot integration support via PersistentDataContainer" -m "- Minecraft 1.21.1 compatibility" 2>nul

echo.
echo ════════════════════════════════════════════════════════════
echo.
echo   ✓ READY TO PUSH
echo.
echo ════════════════════════════════════════════════════════════
echo.
echo To push to GitHub, run:
echo.
echo   cd %WORK_DIR%\repo
echo   git push origin %BRANCH%
echo.
echo Then create a Pull Request on GitHub
echo.
pause
