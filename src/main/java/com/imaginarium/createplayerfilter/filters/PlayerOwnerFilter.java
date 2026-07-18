package com.imaginarium.createplayerfilter.filters;

import net.minecraft.world.item.ItemStack;

import com.imaginarium.createplayerfilter.data.OwnerData;
import com.imaginarium.createplayerfilter.registries.CreatePlayerFilterDataComponents;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Filtre pour les items portant le data component "owner".
 * Un item passe le filtre si son UUID propriétaire correspond à celui spécifié.
 * (Depuis 1.20.5 les items utilisent des data components, plus des tags NBT.)
 */
public class PlayerOwnerFilter {
    private UUID targetOwner;

    public PlayerOwnerFilter(UUID owner) {
        this.targetOwner = owner;
    }

    /**
     * Vérifie si un item passe le filtre owner.
     */
    public boolean test(ItemStack stack) {
        if (stack.isEmpty() || this.targetOwner == null) {
            return false;
        }
        return this.targetOwner.equals(getItemOwner(stack));
    }

    /**
     * Définit le propriétaire cible.
     */
    public void setTargetOwner(UUID owner) {
        this.targetOwner = owner;
    }

    /**
     * Retourne le UUID du propriétaire cible.
     */
    public UUID getTargetOwner() {
        return this.targetOwner;
    }

    /**
     * Helper pour ajouter le composant owner à un item.
     */
    public static void tagItemWithOwner(ItemStack stack, UUID owner) {
        stack.set(CreatePlayerFilterDataComponents.OWNER.get(), new OwnerData(owner));
    }

    /**
     * Retire le composant owner d'un item.
     */
    public static void removeOwnerTag(ItemStack stack) {
        stack.remove(CreatePlayerFilterDataComponents.OWNER.get());
    }

    /**
     * Récupère l'owner d'un item (ou null).
     */
    @Nullable
    public static UUID getItemOwner(ItemStack stack) {
        OwnerData data = stack.get(CreatePlayerFilterDataComponents.OWNER.get());
        return data != null ? data.owner() : null;
    }

    /**
     * Retourne true si l'item a un propriétaire.
     */
    public static boolean hasOwner(ItemStack stack) {
        return stack.has(CreatePlayerFilterDataComponents.OWNER.get());
    }
}
