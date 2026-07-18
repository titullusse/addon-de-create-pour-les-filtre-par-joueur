# 🚀 AUTO-PUSH - Pousser sur GitHub automatiquement

## 📋 Résumé

Les scripts `AUTO-PUSH.sh` (Linux/Mac) et `AUTO-PUSH.bat` (Windows) font **automatiquement** :

1. ✅ Clonage du repo GitHub
2. ✅ Configuration de la branche
3. ✅ Copie des fichiers du projet
4. ✅ Commit avec message descriptif
5. ⚠️ **Te demande de faire le git push** (authentification requise)

---

## 🎯 Avant de commencer

### Prérequis

- ✅ Git installé
- ✅ Compte GitHub avec accès au repo
- ✅ CreatePlayerFilter.zip téléchargé et extrait

### Fichiers nécessaires

**À télécharger** :
- `CreatePlayerFilter.zip` (ou `.tar.gz`)
- `AUTO-PUSH.sh` (Linux/Mac) ou `AUTO-PUSH.bat` (Windows)

**À extraire** :
- `CreatePlayerFilter.zip` dans `~/Downloads/CreatePlayerFilter/`

---

## 🔧 Instructions Windows

### Étape 1 : Préparer les fichiers

1. Télécharge `CreatePlayerFilter.zip`
2. Extrais-le dans `C:\Users\YourUsername\Downloads\CreatePlayerFilter\`
   - Dossier correct = `C:\Users\...\Downloads\CreatePlayerFilter\build.gradle` existe
3. Télécharge `AUTO-PUSH.bat`

### Étape 2 : Exécuter le script

1. Double-clique sur `AUTO-PUSH.bat`
2. Laisse le script s'exécuter (2-5 minutes)
3. Une fenêtre CMD s'ouvre avec des instructions

### Étape 3 : Pousser sur GitHub

Après le script, une fenêtre CMD te montrera :

```
cd C:\Users\YourUsername\Desktop\github-push-cpf\repo
git push origin addon-de-create-pour-les-filtre-par-joueur
```

Copie-colle cette commande dans CMD/PowerShell

### Étape 4 : Authentification

GitHub te demandera :
- Username: `titullusse` (ou ton nom)
- Password: **Token GitHub** (pas ton mot de passe!)

Pour créer un token:
1. Va sur https://github.com/settings/tokens
2. Crée un "Personal Access Token" avec permissions `repo`
3. Utilise ce token comme mot de passe

### Étape 5 : Vérifier

Va sur https://github.com/titullusse/addon-de-create-pour-les-filtre-par-joueur
- Cherche la branche `addon-de-create-pour-les-filtre-par-joueur`
- Vérifie que tes fichiers y sont

---

## 🐧 Instructions Linux/Mac

### Étape 1 : Préparer les fichiers

1. Télécharge `CreatePlayerFilter.zip`
2. Extrais-le:
   ```bash
   unzip CreatePlayerFilter.zip -d ~/Downloads/
   ```
3. Vérifie que le dossier existe:
   ```bash
   ls ~/Downloads/CreatePlayerFilter/build.gradle
   ```
4. Télécharge `AUTO-PUSH.sh`

### Étape 2 : Rendre le script exécutable

```bash
chmod +x AUTO-PUSH.sh
```

### Étape 3 : Exécuter le script

```bash
./AUTO-PUSH.sh
```

**Output** :
```
✓ Git trouvé
✓ Répertoire créé
✓ Repository cloné
✓ Branche prête
✓ Fichiers copiés
✓ Commit créé

✓ PRÊT POUR GITHUB PUSH
```

### Étape 4 : Pousser sur GitHub

Le script te dit exactement quoi faire :

```bash
cd ~/Desktop/github-push-cpf/repo
git push origin addon-de-create-pour-les-filtre-par-joueur
```

### Étape 5 : Authentification

```bash
# GitHub te demande:
Username for 'https://github.com': titullusse
Password for 'https://titullusse@github.com': [TON_TOKEN]
```

**Token depuis**: https://github.com/settings/tokens

### Étape 6 : Vérifier

```bash
# Va vérifier sur GitHub
open https://github.com/titullusse/addon-de-create-pour-les-filtre-par-joueur
```

---

## 📂 Structure des fichiers attendue

**Windows** :
```
C:\Users\YourUsername\
├── Downloads\
│   └── CreatePlayerFilter\
│       ├── build.gradle
│       ├── START_HERE.md
│       ├── src\
│       └── ...
└── Desktop\
    └── AUTO-PUSH.bat
```

**Linux/Mac** :
```
~/
├── Downloads/
│   └── CreatePlayerFilter/
│       ├── build.gradle
│       ├── START_HERE.md
│       ├── src/
│       └── ...
└── AUTO-PUSH.sh
```

---

## ⚠️ Dépannage

### "Git not found"
```bash
# Windows: https://git-scm.com/download/win
# Linux: sudo apt install git
# Mac: brew install git
```

### "Source directory not found"
```
Erreur: Les fichiers CreatePlayerFilter ne sont pas au bon endroit

Solution:
  1. Télécharge CreatePlayerFilter.zip
  2. Extrais dans:
     - Windows: C:\Users\YourUsername\Downloads\CreatePlayerFilter\
     - Linux/Mac: ~/Downloads/CreatePlayerFilter/
  3. Relance le script
```

### "Unable to access GitHub"
```bash
# Vérifier la connexion internet
ping github.com

# Vérifier Git config
git config --global user.name "titullusse"
git config --global user.email "your.email@example.com"
```

### "Authentication failed"
```bash
# Tu as probablement entré ton mot de passe au lieu du token
# Solution: Créer un token GitHub:
# https://github.com/settings/tokens

# Ensuite, utiliser le token comme mot de passe
```

### "Branch already exists"
```bash
# C'est normal, le script va juste checkout la branche existante
# Continue avec le push
```

### "Push rejected"
```bash
# Possible causes:
# 1. Pas d'accès au repo (vérifier les permissions)
# 2. Branche protégée (vérifier les settings GitHub)
# 3. Conflits de fichiers (faire un pull et merge)
```

---

## ✨ Résumé des étapes

### Version courte

**Windows** :
```
1. Double-clique AUTO-PUSH.bat
2. Attends ~2-5 minutes
3. Exécute la commande `git push` affichée
4. Entre ton token GitHub
5. Vérifie sur GitHub ✅
```

**Linux/Mac** :
```
1. chmod +x AUTO-PUSH.sh
2. ./AUTO-PUSH.sh
3. Attends ~2-5 minutes
4. Exécute la commande `git push` affichée
5. Entre ton token GitHub
6. Vérifie sur GitHub ✅
```

---

## 🎯 Résultat final

Une fois `git push` réussi:

```
✓ Branche créée/mise à jour: addon-de-create-pour-les-filtre-par-joueur
✓ Tous les fichiers poussés sur GitHub
✓ Commit avec message descriptif présent
✓ Prêt pour développement futur
```

---

## 📞 Questions?

### "Le script est trop lent?"
- Normal! Première exécution clone tout
- Les prochaines seront plus rapides

### "Je peux modifier le projet après?"
- Oui! Modifie les fichiers, puis:
  ```bash
  cd ~/Desktop/github-push-cpf/repo
  git add .
  git commit -m "Mes modifications"
  git push origin addon-de-create-pour-les-filtre-par-joueur
  ```

### "Comment créer une Pull Request?"
- Va sur GitHub → Compare & pull request → Create pull request

### "Je veux pousser une autre branche?"
- Modifie `BRANCH=` au début du script

---

**C'est tout! Les scripts font le gros du travail! 🚀**

Besoin d'aide? Consulte la section "Dépannage" ci-dessus.
