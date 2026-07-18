# Guide d'intégration Create Player Filter avec tes plugins Spigot

## 🔗 Synchronisation Spigot ↔ Create Player Filter

### Option 1 : Utiliser les NBT Tags (Recommandé)

Si tu veux que tes plugins Spigot et cet addon Create fonctionnent ensemble :

#### Dans tes plugins Spigot :

```java
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;

public class PlayerItemUtils {
    private static final NamespacedKey OWNER_KEY = 
        new NamespacedKey("createplayerfilter", "owner");
    
    /**
     * Tagger un item avec son propriétaire
     */
    public static void tagItemWithOwner(ItemStack item, Player owner) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(OWNER_KEY, PersistentDataType.STRING, owner.getUniqueId().toString());
            item.setItemMeta(meta);
        }
    }
    
    /**
     * Récupérer le propriétaire d'un item
     */
    public static String getItemOwner(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            if (container.has(OWNER_KEY, PersistentDataType.STRING)) {
                return container.get(OWNER_KEY, PersistentDataType.STRING);
            }
        }
        return null;
    }
    
    /**
     * Vérifier si l'item appartient au joueur
     */
    public static boolean isOwnedBy(ItemStack item, Player player) {
        String owner = getItemOwner(item);
        return owner != null && owner.equals(player.getUniqueId().toString());
    }
    
    /**
     * Retirer le tag propriétaire
     */
    public static void removeOwnerTag(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.remove(OWNER_KEY);
            item.setItemMeta(meta);
        }
    }
}
```

### Option 2 : Événement personnalisé (Pour tes autres plugins)

Créer un événement Spigot custom :

```java
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerItemOwnerEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player owner;
    private final ItemStack item;
    
    public PlayerItemOwnerEvent(Player owner, ItemStack item) {
        this.owner = owner;
        this.item = item;
    }
    
    public Player getOwner() {
        return owner;
    }
    
    public ItemStack getItem() {
        return item;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
```

### Option 3 : Événement d'entrée dans un coffre

```java
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;

public class PlayerSortingChestListener implements Listener {
    
    @EventHandler
    public void onChestOpen(InventoryOpenEvent event) {
        Inventory inv = event.getInventory();
        Player player = (Player) event.getPlayer();
        
        // Vérifier si c'est un coffre de tri joueur
        // Cette logique dépend de comment tu stockes les données
        
        if (inv.getHolder() instanceof PlayerSortingChestBlockEntity) {
            // Vérifier que c'est le propriétaire
            // ...
        }
    }
}
```

## 📊 Architecture recommandée pour Imaginarium

```
Imaginarium/
├── spigot-plugins/
│   ├── ImaginariumCore/
│   │   └── util/
│   │       └── PlayerItemUtils.java (comme ci-dessus)
│   ├── ImaginariumSkills/
│   ├── ImaginariumCoin/
│   └── ImaginariumGuilds/
│
└── neoforge-addons/
    └── CreatePlayerFilter/
        ├── filters/
        ├── blocks/
        └── compat/
```

## 🎯 Cas d'usage pour ton serveur

### Exemple 1 : Quêtes personnelles

```java
public class QuestRewardHandler {
    
    public void giveQuestItem(Player player, ItemStack reward) {
        // Tagger l'item comme propriété du joueur
        PlayerItemUtils.tagItemWithOwner(reward, player);
        
        // Ajouter à l'inventaire
        player.getInventory().addItem(reward);
        
        player.sendMessage("§aQuête complétée! Item personnel reçu.");
    }
}
```

### Exemple 2 : Système de banque personnelle

```java
public class PersonalBankManager {
    
    public void depositItem(Player player, ItemStack item) {
        // Vérifier que l'item appartient au joueur
        if (!PlayerItemUtils.isOwnedBy(item, player)) {
            PlayerItemUtils.tagItemWithOwner(item, player);
        }
        
        // Déposer dans une zone stockage
        // (puis Create Player Filter tri automatiquement)
    }
    
    public void withdrawItem(Player player, ItemStack item) {
        // Vérifier ownership avant retrait
        if (PlayerItemUtils.isOwnedBy(item, player)) {
            player.getInventory().addItem(item);
        } else {
            player.sendMessage("§cCet item n'est pas le vôtre!");
        }
    }
}
```

### Exemple 3 : Système de guild avec storage collectif

```java
public class GuildStorageManager {
    
    public void addItemToGuildStorage(Player player, ItemStack item) {
        // Marquer avec les infos guild + propriétaire
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(
                new NamespacedKey("imaginarium", "guild_owner"),
                PersistentDataType.STRING,
                player.getUniqueId().toString()
            );
            item.setItemMeta(meta);
        }
        
        // Ensuite Create Player Filter peut trier par guild + owner
    }
}
```

## 🔧 Configuration recommandée

Dans ton `config.yml` Spigot :

```yaml
imaginarium:
  create-player-filter:
    enabled: true
    auto-tag-items: true
    tag-quest-rewards: true
    tag-purchased-items: true
    
  personal-storage:
    enabled: true
    use-player-sorting-chest: true
    allow-multiple-chests: false
```

## 📡 Communication Spigot → Minecraft Forge

Pour lire les données depuis les plugins Spigot :

```java
// Dans un plugin Spigot
public class CreateDataSync {
    
    public void syncItemOwner(Player player, ItemStack item) {
        // Créer/modifier le NBT tag
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        
        // Le tag sera synchronisé avec Forge via le NBT native de Minecraft
        pdc.set(
            new NamespacedKey("createplayerfilter", "owner"),
            PersistentDataType.STRING,
            player.getUniqueId().toString()
        );
        item.setItemMeta(meta);
    }
}
```

## ⚠️ Notes importantes

1. **Minecraft NBT vs Bukkit PDC** : Bukkit convertit automatiquement les `PersistentDataContainer` en NBT sur la sauvegarde
2. **Synchronisation** : Les changements faits dans Bukkit se reflètent automatiquement dans Forge
3. **Performance** : Les filtres Create fonctionnent côté Forge donc pas de lag Bukkit
4. **Compatibilité** : Fonctionne avec Paper, Spigot et leurs variantes

---

**Pour questions ou intégration avancée, consulte les docs :**
- [Bukkit PersistentDataContainer](https://hub.spigotmc.org/javadocs/spigot/)
- [Create Mod API](https://github.com/Creators-of-Create/Create/wiki)
- [NeoForge Docs](https://docs.neoforged.net/)
