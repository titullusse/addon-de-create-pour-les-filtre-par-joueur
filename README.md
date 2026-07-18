# Create Player Filter

Addon Minecraft **NeoForge 1.21.1** pour trier et distribuer les items **par joueur**, pensé pour être utilisé avec le mod [Create](https://createmod.net/).

## Fonctionnalités

- **Filtre Propriétaire Joueur** (`player_owner_filter`) : item filtre liable à un joueur.
  - Clic droit : se lie à vous · Accroupi + clic droit : se délie · Clic droit sur un joueur : se lie à lui
  - Le tooltip affiche le joueur lié
- **Coffre de Tri Joueur** (`player_sorting_chest`) :
  - Ne s'ouvre que pour son propriétaire (celui qui l'a posé)
  - Les insertions automatiques (hoppers, tunnels/bras/goulottes Create, pipes moddés) sont **filtrées** : seuls les items appartenant au propriétaire entrent
- **Intégration Create** (si Create 6.x est installé) : les items tagués exposent un attribut **« appartient à &lt;joueur&gt; »** sélectionnable dans les attribute filters de Create (tunnels de laiton, smart chutes, bras mécaniques, déployeurs...).
- **Data component `createplayerfilter:owner`** : remplace l'ancien tag NBT (supprimé depuis Minecraft 1.20.5) ; stocke l'UUID + le nom du joueur. Tout item tagué affiche « Propriétaire : X » dans son tooltip.
- **Commandes** :
  - `/cpf tag <joueur>` — tague n'importe quel item tenu en main avec le joueur ciblé
  - `/cpf untag` — retire le tag propriétaire de l'item tenu
  - `/cpf bind <joueur>` — lie le Filtre Propriétaire Joueur tenu en main à un autre joueur

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
