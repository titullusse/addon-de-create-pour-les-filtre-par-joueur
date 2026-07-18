# 📤 Guide - Pusher Create Player Filter sur GitHub

## 🎯 Objectif

Pousser le projet **Create Player Filter** sur ton repo GitHub à la branche `addon-de-create-pour-les-filtre-par-joueur`

---

## 📍 Chemin du projet source

```
/mnt/user-data/outputs/CreatePlayerFilter/
```

---

## 🔗 Infos GitHub

- **URL du repo** : https://github.com/titullusse/addon-de-create-pour-les-filtre-par-joueur
- **Branche cible** : `addon-de-create-pour-les-filtre-par-joueur`

---

## ⚡ Méthode rapide (CLI)

### 1️⃣ Cloner le repo

```bash
git clone https://github.com/titullusse/addon-de-create-pour-les-filtre-par-joueur.git
cd addon-de-create-pour-les-filtre-par-joueur
git checkout addon-de-create-pour-les-filtre-par-joueur
```

### 2️⃣ Copier les fichiers

**Depuis** : `/mnt/user-data/outputs/CreatePlayerFilter/`  
**Vers** : Le repo cloné

#### Linux/Mac :
```bash
cp -r /mnt/user-data/outputs/CreatePlayerFilter/* .
```

#### Windows :
```bash
xcopy /mnt/user-data/outputs/CreatePlayerFilter\* . /E /I /Y
```

### 3️⃣ Pousser sur GitHub

```bash
git add .
git commit -m "feat: Add Create Player Filter addon v1.0.0"
git push origin addon-de-create-pour-les-filtre-par-joueur
```

---

## 🤖 Méthode automatisée

### Windows

```bash
# Double-clique sur
push-to-github.bat
```

Puis, exécute :
```bash
cd %TEMP%\github-push\repo
git push origin addon-de-create-pour-les-filtre-par-joueur
```

### Linux/Mac

```bash
chmod +x push-to-github.sh
./push-to-github.sh
```

Puis, exécute :
```bash
cd /tmp/github-push/repo
git push origin addon-de-create-pour-les-filtre-par-joueur
```

---

## 📋 Étapes détaillées (Manuel)

### Étape 1 : Cloner le repository

```bash
cd ~/Desktop  # Ou un dossier de ton choix
git clone https://github.com/titullusse/addon-de-create-pour-les-filtre-par-joueur.git
cd addon-de-create-pour-les-filtre-par-joueur
```

### Étape 2 : Créer ou checkout la branche

```bash
# Si la branche n'existe pas, la créer :
git checkout -b addon-de-create-pour-les-filtre-par-joueur

# Si elle existe déjà :
git checkout addon-de-create-pour-les-filtre-par-joueur
```

### Étape 3 : Copier les fichiers

**Linux/Mac** :
```bash
# Copier tous les fichiers du projet
cp -r /mnt/user-data/outputs/CreatePlayerFilter/* .
```

**Windows** :
```bash
# Copier en utilisant l'explorateur:
# 1. Ouvrir /mnt/user-data/outputs/CreatePlayerFilter/
# 2. Sélectionner tous les fichiers (Ctrl+A)
# 3. Copier (Ctrl+C)
# 4. Aller dans le dossier du repo GitHub
# 5. Coller (Ctrl+V)

# Ou en ligne de commande:
xcopy "C:\Users\YourUsername\Downloads\CreatePlayerFilter\*" "." /E /I /Y
```

### Étape 4 : Vérifier les changements

```bash
git status
```

Output :
```
On branch addon-de-create-pour-les-filtre-par-joueur

Untracked files:
  (use "git add <file>..." to include in what will be committed)
        COMPILATION.md
        COMPILE.bat
        COMPILE.sh
        README.md
        START_HERE.md
        build.gradle
        src/
        ...
```

### Étape 5 : Ajouter et commit

```bash
# Ajouter tous les fichiers
git add .

# Commit avec un message descriptif
git commit -m "feat: Add Create Player Filter addon v1.0.0

- Player Sorting Chest block for automatic item sorting by player
- Player Owner Filter for Create Mod integration
- NBT tag system for tracking item ownership
- /cpf tag and /cpf untag commands
- Complete documentation in French
- Gradle build configuration (NeoForge 1.21.1)
- Spigot integration support
- Minecraft 1.21.1 compatibility"
```

### Étape 6 : Pousser sur GitHub

```bash
git push origin addon-de-create-pour-les-filtre-par-joueur
```

Si tu dois entrer tes identifiants :
```bash
# Avec authentification par token (recommandé)
git push https://[TON_TOKEN]@github.com/titullusse/addon-de-create-pour-les-filtre-par-joueur.git
```

---

## ✅ Vérifier que c'est poussé

1. Va sur : https://github.com/titullusse/addon-de-create-pour-les-filtre-par-joueur
2. Clique sur la branche dropdown
3. Cherche `addon-de-create-pour-les-filtre-par-joueur`
4. Vérifie que tes fichiers y sont

---

## 📊 Fichiers qui seront poussés

```
addon-de-create-pour-les-filtre-par-joueur/
├── START_HERE.md
├── COMPILATION.md
├── QUICKSTART.md
├── README.md
├── INTEGRATION_SPIGOT.md
├── build.gradle
├── settings.gradle
├── gradle.properties
├── COMPILE.bat
├── COMPILE.sh
├── gradlew
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
└── src/
    └── main/
        ├── java/
        │   └── com/imaginarium/createplayerfilter/
        │       ├── CreatePlayerFilterMod.java
        │       ├── blocks/
        │       ├── filters/
        │       ├── commands/
        │       ├── registries/
        │       ├── compat/
        │       └── data/
        └── resources/
            ├── META-INF/
            │   └── mods.toml
            └── assets/
                └── createplayerfilter/
```

---

## 🆘 Dépannage

### "ERROR: Repository not found"
```bash
# Vérifier l'accès
git clone https://github.com/titullusse/addon-de-create-pour-les-filtre-par-joueur.git
# Si erreur: vérifier l'URL et les permissions GitHub
```

### "Permission denied (publickey)"
```bash
# Configurer SSH key ou utiliser HTTPS avec token
git config --global credential.helper store
git push origin addon-de-create-pour-les-filtre-par-joueur
```

### "Branch does not exist"
```bash
# Créer la branche localement d'abord
git checkout -b addon-de-create-pour-les-filtre-par-joueur
git push -u origin addon-de-create-pour-les-filtre-par-joueur
```

### "Changes not being pushed"
```bash
# Vérifier le status
git status

# Si files non staged:
git add .
git commit -m "Initial commit"
git push origin addon-de-create-pour-les-filtre-par-joueur
```

---

## 📝 Commit message complet

```
feat: Add Create Player Filter addon v1.0.0

DESCRIPTION:
Create Player Filter est un addon Create Mod pour Minecraft 1.21.1
qui permet de trier automatiquement les items par propriétaire.

FEATURES:
- ✓ Player Sorting Chest (coffre trié par joueur)
- ✓ Player Owner Filter (filtre Create personnalisé)
- ✓ Commandes /cpf tag et /cpf untag
- ✓ Système de tags NBT pour l'ownership
- ✓ Compatible avec les déployeurs Create
- ✓ Intégration Spigot (PersistentDataContainer)
- ✓ Documentation complète en français

TECHNICAL:
- Minecraft: 1.21.1
- Loader: NeoForge 52.0.21+
- Create Mod: 0.5.1.o+
- Java: 21+

WHAT'S INCLUDED:
- Code source complet (10 fichiers Java)
- Documentation (5 fichiers Markdown)
- Configuration Gradle
- Scripts de compilation (Windows/Linux/Mac)
- Ressources et traductions
- Exemples d'intégration

For Imaginarium Server
Version 1.0.0
```

---

## 🎉 C'est fini!

Une fois poussé, tu peux :
1. Créer une Pull Request si nécessaire
2. Fusionner la branche avec `main` ou `dev`
3. Commencer à développer les versions futures

---

**Besoin d'aide?** Consulte les sections "Dépannage" ci-dessus.
