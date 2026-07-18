package com.imaginarium.createplayerfilter.filters;

import com.simibubi.create.foundation.item.ItemHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import java.util.UUID;

/**
 * Filtre Create pour les items avec tag NBT "Owner"
 * Filtre les items si leur UUID owner correspond à celui spécifié
 */
public class PlayerOwnerFilter {
    private UUID targetOwner;

    public PlayerOwnerFilter(UUID owner) {
        this.targetOwner = owner;
    }

    /**
     * Vérifie si un item passe le filtre owner
     */
    public boolean test(ItemStack stack) {
        if (stack.isEmpty() || this.targetOwner == null) {
            return false;
        }

        // Vérifier le tag NBT de l'item pour le tag "Owner"
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("Owner", 8)) { // Type 8 = String UUID
            String ownerString = tag.getString("Owner");
            try {
                UUID itemOwner = UUID.fromString(ownerString);
                return itemOwner.equals(this.targetOwner);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        // Essayer aussi le format legacy (si jamais)
        if (tag != null && tag.contains("CustomModelData")) {
            // Exemple : tu peux aussi checker d'autres tags si nécessaire
        }

        return false;
    }

    /**
     * Défini le propriétaire cible
     */
    public void setTargetOwner(UUID owner) {
        this.targetOwner = owner;
    }

    /**
     * Retourne le UUID du propriétaire
     */
    public UUID getTargetOwner() {
        return this.targetOwner;
    }

    /**
     * Helper pour ajouter le tag owner à un item
     */
    public static void tagItemWithOwner(ItemStack stack, UUID owner) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString("Owner", owner.toString());
    }

    /**
     * Retire le tag owner d'un item
     */
    public static void removeOwnerTag(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            tag.remove("Owner");
        }
    }

    /**
     * Récupère l'owner d'un item (ou null)
     */
    public static UUID getItemOwner(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("Owner", 8)) {
            try {
                return UUID.fromString(tag.getString("Owner"));
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
