# 🚀 Guide de Démarrage Rapide - Create Player Filter

## ✅ Prérequis

- **Java 21+** (JDK)
- **Gradle** (inclus dans le wrapper)
- **Minecraft 1.21.1**
- **NeoForge 1.21.1** (52.0.21+)
- **Create Mod** (0.5.1.o+)

## 📥 Installation du projet

```bash
# 1. Cloner ou extraire le projet
cd CreatePlayerFilter

# 2. Compiler le mod
./gradlew build

# 3. Le JAR sera généré dans :
# build/libs/createplayerfilter-1.0.0.jar
```

## 🎮 Installation dans Minecraft

1. **Créer/accéder à ton dossier mods** :
   ```
   %appdata%\.minecraft\mods  (Windows)
   ~/.minecraft/mods          (Linux/Mac)
   ```

2. **Copier le JAR** :
   ```bash
   cp build/libs/createplayerfilter-1.0.0.jar ~/.minecraft/mods/
   ```

3. **Lancer Minecraft** avec le profil NeoForge 1.21.1

## 🎯 Utilisation en jeu

### Commandes disponibles

```
/cpf tag <joueur>        → Tagger l'item en main avec un propriétaire
/cpf untag               → Retirer le tag propriétaire
```

### Blocs et items

- **Player Sorting Chest** : Coffre automatique tri par propriétaire
  - Place le bloc → Automatiquement assigné au placer
  - Seul le propriétaire peut l'ouvrir
  - Les items avec son tag sont triés dedans

- **Player Owner Filter** : Item de filtre Create
  - Utiliser dans les déployeurs Create
  - Filtre uniquement les items du propriétaire spécifié

### Workflow exemple

1. **Créer un item perso** :
   - Hold l'item
   - `/cpf tag Marc33`
   - Item est maintenant "propriété de Marc33"

2. **Trier automatiquement** :
   - Placer un `Player Sorting Chest`
   - Configurer un déployeur avec `Player Owner Filter`
   - Items avec tag sont triés automatiquement

3. **Accéder au stockage** :
   - Seul le propriétaire peut ouvrir le coffre
   - Contient tous les items taggés

## 📊 Architecture

```
Source (Hold Item)
        ↓
    /cpf tag
        ↓
Item NBT Tag: {Owner: "uuid"}
        ↓
    Deployer + Player Owner Filter
        ↓
Player Sorting Chest (Owner Match)
        ↓
Automatiquement trié!
```

## 🔧 Configuration avancée

### Modifier le code source

**Fichiers clés** :

| Fichier | Rôle |
|---------|------|
| `CreatePlayerFilterMod.java` | Point d'entrée principal |
| `PlayerSortingChestBlock.java` | Bloc coffre |
| `PlayerOwnerFilter.java` | Logique de filtrage |
| `OwnerTagCommand.java` | Commandes `/cpf` |

### Compiler après modification

```bash
# Après chaque modification de code
./gradlew build

# Copier le nouveau JAR
cp build/libs/createplayerfilter-1.0.0.jar ~/.minecraft/mods/
```

## 🐛 Dépannage

### "Mod non chargé"
- Vérifier que NeoForge 1.21.1 est installé
- Vérifier que Create Mod est présent
- Regarder le fichier `latest.log` pour les erreurs

### "Commandes ne fonctionnent pas"
- Vérifier les permissions OP
- Redémarrer le serveur/client
- Vérifier la console pour les erreurs

### "Coffre pas accessible"
- Vérifier que tu es le propriétaire (affiché dans le nom)
- Vérifier la version du mod
- Regénérer le bloc si nécessaire

## 📚 Intégration avec tes plugins

Voir le fichier `INTEGRATION_SPIGOT.md` pour :
- Tagger des items depuis tes plugins Spigot
- Synchroniser les données
- Créer des événements personnalisés

## 🚀 Prochaines étapes

1. **Compiler** : `./gradlew build`
2. **Tester en jeu** : Placer le JAR et lancer Minecraft
3. **Intégrer** : Suivre `INTEGRATION_SPIGOT.md` si besoin
4. **Customiser** : Modifier les fichiers Java selon tes besoins

## 📝 Notes importantes

- Les items sans tag `Owner` **ne seront pas filtrés**
- Seul le **propriétaire peut ouvrir** le coffre de tri
- Compatible avec tous les **blocs Create 1.21.1**
- Fonctionne en **multiplayer sans lag**

## 🎓 Ressources

- [Create Mod Documentation](https://www.youtube.com/watch?v=BzL4A9NVqnI)
- [NeoForge Docs](https://docs.neoforged.net/)
- [Minecraft Wiki NBT](https://minecraft.wiki/w/NBT_format)

---

**Créé pour Imaginarium** 🎮✨

Besoin d'aide? Vérifie la console ou consulte les fichiers source!
