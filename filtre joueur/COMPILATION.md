# 🔨 Guide de Compilation - Create Player Filter

## ⚡ Compilation rapide (2 min)

### Windows

1. **Double-clique sur** `COMPILE.bat`
2. **Attend la fin** (prendreTx quelques minutes la première fois)
3. **Cherche le fichier** :
   ```
   build\libs\createplayerfilter-1.0.0.jar
   ```

### Linux / Mac

1. **Ouvre le terminal** dans le dossier `CreatePlayerFilter`
2. **Exécute** :
   ```bash
   chmod +x COMPILE.sh
   ./COMPILE.sh
   ```
3. **Cherche le fichier** :
   ```
   build/libs/createplayerfilter-1.0.0.jar
   ```

---

## ✅ Prérequis

**Java 21+** (obligatoire)

```bash
# Vérifier la version
java -version
```

**Doit afficher** : `openjdk version "21"` ou supérieur

👉 [Télécharger Java 21](https://www.oracle.com/java/technologies/downloads/)

---

## 📋 Étapes détaillées (Manuel)

Si les scripts ne fonctionnent pas, fais ça manuellement :

### Windows - CMD

```cmd
cd C:\chemin\vers\CreatePlayerFilter
gradlew.bat build
```

### Linux/Mac - Terminal

```bash
cd /chemin/vers/CreatePlayerFilter
chmod +x gradlew
./gradlew build
```

---

## 📊 Résultat attendu

### ✓ Succès

```
BUILD SUCCESSFUL

Résultat:
  build/libs/createplayerfilter-1.0.0.jar (1.2 MB)
```

### ✗ Erreur "Java not found"

```bash
# Ajouter Java au PATH si nécessaire
# Windows: Redémarrer après installation de Java
# Linux/Mac: export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
```

---

## 📦 Après compilation

### Installation dans Minecraft

1. **Trouver le dossier mods** :
   - Windows: `%appdata%\.minecraft\mods`
   - Linux: `~/.minecraft/mods`
   - Mac: `~/Library/Application Support/minecraft/mods`

2. **Copier le JAR** :
   ```
   createplayerfilter-1.0.0.jar → mods/
   ```

3. **Lancer Minecraft** :
   - Sélectionner profil NeoForge 1.21.1
   - Launcher

4. **Vérifier** :
   - Chercher "createplayerfilter" dans les mods chargés
   - Teste `/cpf tag <joueur>` en jeu

---

## 🆘 Dépannage

### "Gradle not found"
```bash
# Utiliser la version incluse
./gradlew build    # Linux/Mac
gradlew.bat build  # Windows
```

### "Java version too old"
```bash
# Télécharger Java 21:
# https://www.oracle.com/java/technologies/downloads/
```

### "Cannot download dependencies"
```bash
# Vérifier la connexion internet
# Réessayer: ./gradlew build --refresh-dependencies
```

### Le fichier JAR n'est pas généré
```bash
# Chercher les erreurs:
./gradlew build --stacktrace
```

---

## 🎯 Commande complète (cas extrême)

Si rien ne fonctionne, essaie ça :

```bash
./gradlew clean build --no-daemon --stacktrace
```

Ça va :
- `clean` : Nettoyer les anciens fichiers
- `build` : Compiler
- `--no-daemon` : Sans cache
- `--stacktrace` : Afficher les détails d'erreur

---

## 💡 Tips

- **Première compilation** : Plus lente (télécharge tout)
- **Compilations suivantes** : Beaucoup plus rapides
- **Cache Gradle** : Se trouve dans `~/.gradle/`
- **Forcer rechargement** : `./gradlew --refresh-dependencies build`

---

## ✨ Résultat final

Une fois compilé, tu auras :

```
CreatePlayerFilter/
├── build/
│   └── libs/
│       └── createplayerfilter-1.0.0.jar  ← C'EST LUI!
├── COMPILE.bat
├── COMPILE.sh
└── ...
```

📌 **Copie simplement le JAR dans `mods/` et c'est bon!** 🎮

---

**Besoin d'aide?** Regarde la console pour les messages d'erreur ou essaie les commandes de dépannage ci-dessus.
