package com.imaginarium.createplayerfilter.compat;

import com.simibubi.create.content.logistics.filter.FilterScreenHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

/**
 * Compatibilité avec les systèmes de filtrage Create
 * Permet d'utiliser les filtres Create standards avec les items propriétaires
 */
public class CreateCompatibility {
    
    /**
     * Retourne si un item match un filtre de propriétaire
     * Ce système fonctionne en combinaison avec les attribute filters de Create
     */
    public static boolean matchesPlayerOwnerFilter(ItemStack stack, String ownerUUID) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("Owner", 8)) {
            return tag.getString("Owner").equals(ownerUUID);
        }
        return false;
    }

    /**
     * Obtient l'attribut "Owner" d'un item pour affichage dans les filtres Create
     */
    public static String getOwnerAttributeDisplayName(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("Owner", 8)) {
            String owner = tag.getString("Owner");
            return "Owner: " + owner.substring(0, Math.min(8, owner.length()));
        }
        return "Owner: None";
    }

    /**
     * Retourne true si l'item a un tag propriétaire
     */
    public static boolean hasOwnerTag(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains("Owner", 8);
    }
}
