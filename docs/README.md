# Create Player Filter - Addon pour Imaginarium

Un addon **Create 1.21.1 NeoForge** qui permet de trier et stocker les items par joueur via des filtres personnalisés.

## 🎯 Fonctionnalités

- **Player Owner Filter** : Filtre Create personnalisé pour trier les items par propriétaire
- **Player Sorting Chest** : Coffre spécialisé pour le tri automatique par joueur
- **NBT Owner Tags** : Système de tag NBT pour identifier les propriétaires d'items
- **Compatible Create** : Fonctionne avec les déployeurs, brass tunnels et smart chutes
- **Commandes** : `/cpf tag <joueur>` et `/cpf untag`

## 📦 Installation

1. Placer le JAR compilé dans le dossier `mods/` de ta version Minecraft 1.21.1 NeoForge
2. Lancer le jeu avec Forge/NeoForge 1.21.1

### Dépendances requises :
- **Create Mod** (0.5.1.o+ pour 1.21.1)
- **NeoForge** (1.21.1)
- **Minecraft** 1.21.1

## 🛠️ Compilation

```bash
./gradlew build
```

Le JAR sera généré dans `build/libs/`

## 📖 Utilisation

### 1. Tagger un item avec un propriétaire

Tenez l'item en main et exécutez :
```
/cpf tag <nom_du_joueur>
```

Exemple :
```
/cpf tag Marc33
```

### 2. Retirer le tag propriétaire

```
/cpf untag
```

### 3. Utiliser le Player Sorting Chest

- Placer le bloc `Player Sorting Chest`
- Automatiquement associé au joueur qui le place
- Seul le propriétaire peut l'ouvrir
- Les items avec le même owner sont automatiquement triés dedans

### 4. Intégration avec Create

Utiliser le **Player Owner Filter** dans les systèmes Create :
- Déployeurs (Deployers)
- Brass Tunnels
- Smart Chutes
- Autres blocs de distribution Create

## 🏗️ Architecture

```
createplayerfilter/
├── blocks/
│   ├── PlayerSortingChestBlock.java
│   └── entity/
│       └── PlayerSortingChestBlockEntity.java
├── filters/
│   └── PlayerOwnerFilter.java
├── compat/
│   └── CreateCompatibility.java
├── commands/
│   └── OwnerTagCommand.java
├── data/
│   └── OwnerData.java
└── registries/
    ├── CreatePlayerFilterItems.java
    ├── CreatePlayerFilterBlocks.java
    └── CreatePlayerFilterBlockEntities.java
```

## 📝 Système de filtrage

### Tag NBT Structure
```json
{
  "Owner": "550e8400-e29b-41d4-a716-446655440000"
}
```

### Exemple de filtre Create

1. Créer un item `Player Owner Filter`
2. Configurer le UUID du joueur
3. Placer dans le slot de filtre d'un bloc Create
4. Les items avec le même owner seront filtrés

## 🔧 Customisation

Tu peux modifier :
- Les permissions d'accès au coffre dans `PlayerSortingChestBlock.java`
- Le format du tag NBT dans `PlayerOwnerFilter.java`
- Les messages dans `OwnerTagCommand.java`

## ⚠️ Notes

- Les items sans tag "Owner" ne passeront PAS le filtre
- Seul le propriétaire peut ouvrir son coffre
- Compatible avec les plugins Spigot/Paper (en combinaison)
- Le système fonctionne en multijoueur

## 🤝 Support Imaginarium

Pour intégrer avec tes autres plugins :

```java
// Tagger un item depuis un plugin Spigot
ItemStack item = new ItemStack(Material.DIAMOND);
String owner = player.getUniqueId().toString();
item.getItemMeta().getPersistentDataContainer()
    .set(new NamespacedKey("createplayerfilter", "owner"), PersistentDataType.STRING, owner);
```

## 📄 License

MIT - Libre d'utilisation et de modification

---

**Créé pour le serveur Imaginarium** 🎮
