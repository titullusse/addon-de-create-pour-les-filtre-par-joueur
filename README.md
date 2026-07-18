# Create Player Filter

Addon Minecraft **NeoForge 1.21.1** pour trier et distribuer les items **par joueur**, pensé pour être utilisé avec le mod [Create](https://createmod.net/).

## Fonctionnalités

- **Filtre Propriétaire Joueur** (`player_owner_filter`) : item filtre basé sur l'UUID d'un joueur.
- **Coffre de Tri Joueur** (`player_sorting_chest`) : coffre qui n'accepte que les items de son propriétaire et ne s'ouvre que pour lui.
- **Data component `createplayerfilter:owner`** : remplace l'ancien tag NBT (supprimé depuis Minecraft 1.20.5) pour marquer le propriétaire d'un item.
- **Commandes** :
  - `/cpf tag <joueur>` — tague l'item tenu en main avec l'UUID du joueur ciblé
  - `/cpf untag` — retire le tag propriétaire de l'item tenu

## Prérequis

- Java 21+
- Minecraft 1.21.1
- NeoForge 21.1.x
- Create 6.x (optionnel)

## Compilation

```bash
./gradlew build
```

Le jar est généré dans `build/libs/createplayerfilter-1.0.0.jar`. Copiez-le dans le dossier `mods/` de votre instance NeoForge 1.21.1.

Pour lancer un client de test : `./gradlew runClient`

## Structure du projet

```
src/main/java/com/imaginarium/createplayerfilter/   Code source Java
src/main/resources/META-INF/neoforge.mods.toml      Métadonnées du mod
src/main/resources/assets/createplayerfilter/       Modèles, blockstates, traductions (fr/en)
src/main/resources/data/createplayerfilter/         Loot tables
docs/                                               Documentation d'origine (historique)
scripts/                                            Anciens scripts de push GitHub
```

## Licence

MIT
