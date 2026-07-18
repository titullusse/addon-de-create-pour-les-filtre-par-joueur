@echo off
REM ╔════════════════════════════════════════════════════════════════╗
REM ║                                                                ║
REM ║   SCRIPT AUTOMATISÉ - Pusher Create Player Filter sur GitHub  ║
REM ║                                                                ║
REM ╚════════════════════════════════════════════════════════════════╝

setlocal enabledelayedexpansion

set REPO_URL=https://github.com/titullusse/addon-de-create-pour-les-filtre-par-joueur.git
set BRANCH=addon-de-create-pour-les-filtre-par-joueur
set WORK_DIR=%USERPROFILE%\Desktop\github-push-cpf
set SOURCE_PARENT=%USERPROFILE%\Downloads
set SOURCE=%SOURCE_PARENT%\CreatePlayerFilter

echo.
echo ╔════════════════════════════════════════════════════════════════╗
echo ║                                                                ║
echo ║   Create Player Filter - GitHub Push Script                   ║
echo ║                                                                ║
echo ╚════════════════════════════════════════════════════════════════╝
echo.

REM Vérifier Git
git --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Git n'est pas installé
    echo Télécharger: https://git-scm.com/download/win
    pause
    exit /b 1
)

echo ✓ Git trouvé
echo.

REM Créer répertoire de travail
echo [1/6] Préparation du répertoire de travail...
if exist "%WORK_DIR%" (
    echo   Suppression du répertoire existant...
    rmdir /s /q "%WORK_DIR%"
)
mkdir "%WORK_DIR%"
cd /d "%WORK_DIR%"
echo   ✓ Répertoire créé: %WORK_DIR%
echo.

REM Cloner le repo
echo [2/6] Clonage du repository GitHub...
git clone "%REPO_URL%" repo
if errorlevel 1 (
    echo ❌ Erreur lors du clonage
    pause
    exit /b 1
)
cd repo
echo   ✓ Repository cloné
echo.

REM Checkout ou créer la branche
echo [3/6] Configuration de la branche: %BRANCH%
git fetch origin 2>nul
git rev-parse --verify %BRANCH% >nul 2>&1
if errorlevel 1 (
    echo   Création de la branche...
    git checkout -b %BRANCH%
) else (
    echo   Branche trouvée
    git checkout %BRANCH%
)
echo   ✓ Branche prête
echo.

REM Copier les fichiers
echo [4/6] Copie des fichiers du projet...

REM Vérifier la source
if not exist "%SOURCE%" (
    echo.
    echo ❌ ERREUR: Fichiers source non trouvés
    echo.
    echo Les fichiers doivent être dans:
    echo   %SOURCE%
    echo.
    echo Solution:
    echo   1. Télécharge CreatePlayerFilter.zip depuis les outputs
    echo   2. Extrais-le dans %SOURCE_PARENT%\
    echo.
    pause
    exit /b 1
)

REM Copier les fichiers
xcopy "%SOURCE%\*" "." /E /I /Y /EXCLUDE:exclude.txt >nul 2>&1
if errorlevel 1 (
    echo   Copie avec xcopy échouée, essai alternatif...
    for /r "%SOURCE%" %%F in (*) do (
        set "DEST=%%F"
        set "DEST=!DEST:%SOURCE%=!"
        xcopy "%%F" ".!DEST!" /Y >nul 2>&1
    )
)

echo   ✓ Fichiers copiés
echo.

REM Afficher les changements
echo [5/6] Changements à pousser:
echo.
git status
echo.

REM Commit et push
echo [6/6] Commit et push...
echo.

git add .

REM Vérifier s'il y a des changements
git diff --cached --quiet >nul 2>&1
if errorlevel 1 (
    echo   Création du commit...
    
    git commit -m "feat: Add Create Player Filter addon v1.0.0" ^
        -m "- Player Sorting Chest block for automatic item sorting by player" ^
        -m "- Player Owner Filter for Create Mod integration" ^
        -m "- NBT tag system for tracking item ownership" ^
        -m "- /cpf tag and /cpf untag commands" ^
        -m "- Complete documentation in French" ^
        -m "- Gradle build configuration (NeoForge 1.21.1)" ^
        -m "- Spigot integration support via PersistentDataContainer" ^
        -m "- Minecraft 1.21.1 compatibility" ^
        -m "" ^
        -m "Features:" ^
        -m "✓ Automatic item distribution by player" ^
        -m "✓ Custom storage chests with owner restriction" ^
        -m "✓ NBT-based ownership tracking" ^
        -m "✓ Create Mod compatible filters" ^
        -m "✓ French language support" ^
        -m "✓ Modular and extensible architecture" ^
        -m "" ^
        -m "Version: 1.0.0" ^
        -m "Target: Minecraft 1.21.1 NeoForge" ^
        -m "Created for: Imaginarium Server"
    
    echo.
    echo   ✓ Commit créé
    echo.
) else (
    echo   ℹ️  Aucun changement à commiter
)

echo.
echo ╔════════════════════════════════════════════════════════════════╗
echo ║                                                                ║
echo ║   ✓ PRÊT POUR GITHUB PUSH                                    ║
echo ║                                                                ║
echo ╚════════════════════════════════════════════════════════════════╝
echo.
echo   Répertoire: %WORK_DIR%\repo
echo   Branche: %BRANCH%
echo.
echo   ⚠️  POUR POUSSER SUR GITHUB:
echo.
echo   1. Ouvre un CMD/PowerShell
echo.
echo   2. Exécute:
echo.
echo      cd "%WORK_DIR%\repo"
echo      git push origin %BRANCH%
echo.
echo   3. Entre tes identifiants GitHub si demandé
echo.
echo   4. Vérifie sur: https://github.com/titullusse/addon-de-create-pour-les-filtre-par-joueur
echo.

pause
