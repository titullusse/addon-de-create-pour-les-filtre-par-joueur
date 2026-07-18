package com.imaginarium.createplayerfilter.compat;

import net.minecraft.world.item.ItemStack;

import com.imaginarium.createplayerfilter.filters.PlayerOwnerFilter;

import java.util.UUID;

/**
 * Compatibilité avec les systèmes de filtrage Create.
 * Permet d'utiliser les filtres Create standards avec les items propriétaires.
 */
public class CreateCompatibility {

    /**
     * Retourne si un item match un filtre de propriétaire.
     * Ce système fonctionne en combinaison avec les attribute filters de Create.
     */
    public static boolean matchesPlayerOwnerFilter(ItemStack stack, String ownerUUID) {
        UUID owner = PlayerOwnerFilter.getItemOwner(stack);
        return owner != null && owner.toString().equals(ownerUUID);
    }

    /**
     * Obtient l'attribut "Owner" d'un item pour affichage dans les filtres Create.
     */
    public static String getOwnerAttributeDisplayName(ItemStack stack) {
        UUID owner = PlayerOwnerFilter.getItemOwner(stack);
        if (owner != null) {
            String ownerString = owner.toString();
            return "Owner: " + ownerString.substring(0, Math.min(8, ownerString.length()));
        }
        return "Owner: None";
    }

    /**
     * Retourne true si l'item a un propriétaire.
     */
    public static boolean hasOwnerTag(ItemStack stack) {
        return PlayerOwnerFilter.hasOwner(stack);
    }
}
