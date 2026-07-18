#!/bin/bash

#╔════════════════════════════════════════════════════════════════╗
#║                                                                ║
#║   SCRIPT AUTOMATISÉ - Pusher Create Player Filter sur GitHub  ║
#║                                                                ║
#╚════════════════════════════════════════════════════════════════╝

set -e

# Configuration
REPO_URL="https://github.com/titullusse/addon-de-create-pour-les-filtre-par-joueur.git"
BRANCH="addon-de-create-pour-les-filtre-par-joueur"
WORK_DIR="$HOME/Desktop/github-push-cpf"

echo ""
echo "╔════════════════════════════════════════════════════════════════╗"
echo "║                                                                ║"
echo "║   Create Player Filter - GitHub Push Script                   ║"
echo "║                                                                ║"
echo "╚════════════════════════════════════════════════════════════════╝"
echo ""

# Vérifier Git
if ! command -v git &> /dev/null; then
    echo "❌ Git n'est pas installé"
    echo "Installer Git: brew install git (Mac) ou apt install git (Linux)"
    exit 1
fi

echo "✓ Git trouvé: $(git --version)"
echo ""

# Créer répertoire de travail
echo "[1/6] Préparation du répertoire de travail..."
if [ -d "$WORK_DIR" ]; then
    echo "  ⚠️  Suppression du répertoire existant..."
    rm -rf "$WORK_DIR"
fi
mkdir -p "$WORK_DIR"
cd "$WORK_DIR"
echo "  ✓ Répertoire créé: $WORK_DIR"
echo ""

# Cloner le repo
echo "[2/6] Clonage du repository GitHub..."
git clone "$REPO_URL" repo
cd repo
echo "  ✓ Repository cloné"
echo ""

# Checkout ou créer la branche
echo "[3/6] Configuration de la branche: $BRANCH"
git fetch origin 2>/dev/null || true
if git rev-parse --verify "$BRANCH" 2>/dev/null; then
    echo "  ✓ Branche '$BRANCH' trouvée"
    git checkout "$BRANCH"
else
    echo "  ℹ️  Branche '$BRANCH' n'existe pas, création..."
    git checkout -b "$BRANCH"
fi
echo ""

# Copier les fichiers
echo "[4/6] Copie des fichiers du projet..."
SOURCE="/mnt/user-data/outputs/CreatePlayerFilter"

# Vérifier que la source existe
if [ ! -d "$SOURCE" ]; then
    echo "  ⚠️  Source locale non trouvée, tu dois télécharger CreatePlayerFilter.zip"
    echo "  Puis extraire et remplacer le chemin SOURCE dans ce script"
    exit 1
fi

# Copier (en ignorant .git et build)
rsync -av --exclude='.git' --exclude='build' --exclude='*.jar' "$SOURCE/" . > /dev/null 2>&1 || \
    cp -r "$SOURCE"/* .

echo "  ✓ Fichiers copiés"
echo ""

# Afficher les changements
echo "[5/6] Changements à pousser:"
echo ""
git status
echo ""

# Commit et push
echo "[6/6] Commit et push..."
echo ""

# Ajouter tous les fichiers
git add .

# Vérifier s'il y a des changements
if ! git diff --cached --quiet; then
    echo "  Création du commit..."
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
Created for: Imaginarium Server"
    
    echo ""
    echo "  Commit créé ✓"
    echo ""
    
    echo "  ⚠️  PUSH VERS GITHUB REQUIS"
    echo ""
    echo "  Pour pousser, exécute:"
    echo ""
    echo "    cd $WORK_DIR/repo"
    echo "    git push origin $BRANCH"
    echo ""
    echo "  OU (avec authentification automatique):"
    echo ""
    echo "    cd $WORK_DIR/repo"
    echo "    git push --set-upstream origin $BRANCH"
    echo ""
else
    echo "  ℹ️  Aucun changement à commiter"
fi

echo ""
echo "╔════════════════════════════════════════════════════════════════╗"
echo "║                                                                ║"
echo "║   ✓ PRÊT POUR GITHUB PUSH                                    ║"
echo "║                                                                ║"
echo "╚════════════════════════════════════════════════════════════════╝"
echo ""
echo "  Répertoire: $WORK_DIR/repo"
echo "  Branche: $BRANCH"
echo ""
echo "  Prochaine étape:"
echo "    cd $WORK_DIR/repo"
echo "    git push origin $BRANCH"
echo ""
