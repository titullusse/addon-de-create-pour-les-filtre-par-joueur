#!/bin/bash

# Script de compilation Create Player Filter pour Linux/Mac
# Assurez-vous que Java 21+ est installé

echo ""
echo "===================================="
echo "Create Player Filter Compilation"
echo "===================================="
echo ""

# Vérifier Java
if ! command -v java &> /dev/null; then
    echo "ERROR: Java n'est pas installé ou pas dans PATH"
    echo "Installer Java 21 depuis: https://www.oracle.com/java/technologies/downloads/"
    exit 1
fi

echo "Java version:"
java -version
echo ""

# Vérifier le répertoire
if [ ! -f "build.gradle" ]; then
    echo "ERROR: build.gradle non trouvé"
    echo "Assurez-vous de lancer ce script depuis le répertoire CreatePlayerFilter"
    exit 1
fi

# Compiler
echo "[1/2] Téléchargement des dépendances..."
./gradlew dependencies 2>&1 | grep -E "^|Downloaded|FAILED" || true

echo ""
echo "[2/2] Compilation du mod..."
./gradlew build

echo ""
# Vérifier le résultat
if [ -f "build/libs/createplayerfilter-1.0.0.jar" ]; then
    echo ""
    echo "===================================="
    echo "✓ COMPILATION RÉUSSIE!"
    echo "===================================="
    echo ""
    echo "Fichier généré:"
    echo "  build/libs/createplayerfilter-1.0.0.jar"
    echo ""
    echo "Installation:"
    echo "  1. Copier le JAR dans ~/.minecraft/mods/"
    echo "  2. Lancer Minecraft avec NeoForge 1.21.1"
    echo ""
else
    echo ""
    echo "===================================="
    echo "✗ ERREUR DE COMPILATION"
    echo "===================================="
    echo "Vérifiez les erreurs ci-dessus"
    exit 1
fi
