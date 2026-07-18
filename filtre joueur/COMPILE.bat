@echo off
REM Script de compilation Create Player Filter pour Windows
REM Assurez-vous que Java 21+ est installé

echo.
echo ====================================
echo Create Player Filter Compilation
echo ====================================
echo.

REM Vérifier Java
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java n'est pas installé ou pas dans PATH
    echo Installer Java 21 depuis: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

echo [1/3] Téléchargement des dépendances...
call gradlew.bat dependencies

echo.
echo [2/3] Compilation du mod...
call gradlew.bat build

echo.
if exist "build\libs\createplayerfilter-1.0.0.jar" (
    echo.
    echo ====================================
    echo ✓ COMPILATION RÉUSSIE!
    echo ====================================
    echo.
    echo Fichier généré:
    echo   build\libs\createplayerfilter-1.0.0.jar
    echo.
    echo Installation:
    echo   1. Copier le JAR dans %%appdata%%\.minecraft\mods\
    echo   2. Lancer Minecraft avec NeoForge 1.21.1
    echo.
    pause
) else (
    echo.
    echo ====================================
    echo ✗ ERREUR DE COMPILATION
    echo ====================================
    echo Vérifiez les erreurs ci-dessus
    pause
    exit /b 1
)
