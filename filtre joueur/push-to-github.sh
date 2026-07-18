#!/bin/bash

# Script pour pusher Create Player Filter sur GitHub
# Usage: ./push-to-github.sh

set -e

REPO_URL="https://github.com/titullusse/addon-de-create-pour-les-filtre-par-joueur.git"
BRANCH="addon-de-create-pour-les-filtre-par-joueur"
SOURCE_DIR="/mnt/user-data/outputs/CreatePlayerFilter"
WORK_DIR="/tmp/github-push"

echo "════════════════════════════════════════════════════════════"
echo "  Pushing Create Player Filter to GitHub"
echo "════════════════════════════════════════════════════════════"
echo ""

# Vérifier que les fichiers source existent
if [ ! -d "$SOURCE_DIR" ]; then
    echo "❌ ERROR: Source directory not found: $SOURCE_DIR"
    exit 1
fi

echo "✓ Source directory found"

# Créer le répertoire de travail
mkdir -p "$WORK_DIR"
cd "$WORK_DIR"

echo "✓ Working directory created: $WORK_DIR"
echo ""

# Cloner le repo
echo "[1/5] Cloning repository..."
if [ -d "repo" ]; then
    rm -rf repo
fi
git clone "$REPO_URL" repo
cd repo

echo "✓ Repository cloned"
echo ""

# Checkout la branche
echo "[2/5] Checking out branch: $BRANCH"
git checkout "$BRANCH" || git checkout -b "$BRANCH"

echo "✓ Branch ready"
echo ""

# Copier les fichiers
echo "[3/5] Copying files from $SOURCE_DIR"
# Copier tous les fichiers sauf .git
rsync -av --exclude='.git' --exclude='build' "$SOURCE_DIR/" .

echo "✓ Files copied"
echo ""

# Vérifier les changements
echo "[4/5] Git status:"
git status

echo ""
echo "════════════════════════════════════════════════════════════"
echo "  Ready to commit and push!"
echo "════════════════════════════════════════════════════════════"
echo ""

# Ajouter les fichiers
git add .

# Commit
echo "[5/5] Creating commit..."
git commit -m "feat: Add Create Player Filter addon v1.0.0

- Player Sorting Chest block for automatic item sorting by player
- Player Owner Filter for Create Mod integration
- NBT tag system for tracking item ownership
- /cpf tag and /cpf untag commands
- Complete documentation in French
- Gradle build configuration (NeoForge 1.21.1)
- Spigot integration support via PersistentDataContainer
- Minecraft 1.21.1 compatibility

Features:
✓ Automatic item distribution by player
✓ Custom storage chests with owner restriction
✓ NBT-based ownership tracking
✓ Create Mod compatible filters
✓ French language support
✓ Modular and extensible architecture

Docs included:
- START_HERE.md (quick start guide)
- COMPILATION.md (build instructions)
- QUICKSTART.md (usage guide)
- README.md (technical documentation)
- INTEGRATION_SPIGOT.md (Spigot integration)

Version: 1.0.0
Target: Minecraft 1.21.1 NeoForge
Created for: Imaginarium Server" || true

echo ""
echo "════════════════════════════════════════════════════════════"
echo "  ✓ READY TO PUSH"
echo "════════════════════════════════════════════════════════════"
echo ""
echo "To push to GitHub, run:"
echo ""
echo "  cd $WORK_DIR/repo"
echo "  git push origin $BRANCH"
echo ""
echo "Then create a Pull Request on GitHub"
echo ""
