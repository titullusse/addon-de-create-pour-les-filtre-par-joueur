package com.imaginarium.createplayerfilter.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Configuration commune (config/createplayerfilter-common.toml).
 */
public class CreatePlayerFilterConfig {
    public static final ModConfigSpec SPEC;

    /** Taguer automatiquement les items ramassés avec l'UUID du joueur. */
    public static final ModConfigSpec.BooleanValue AUTO_TAG_ON_PICKUP;

    /** Retirer le tag propriétaire quand un joueur lâche un item. */
    public static final ModConfigSpec.BooleanValue CLEAR_TAG_ON_DROP;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("ownerTag");

        AUTO_TAG_ON_PICKUP = builder
                .comment("Taguer automatiquement tout item ramassé avec l'UUID du joueur qui le ramasse.",
                        "Automatically tag any picked up item with the picking player's UUID.")
                .define("autoTagOnPickup", true);

        CLEAR_TAG_ON_DROP = builder
                .comment("Retirer le tag propriétaire quand un joueur lâche un item au sol,",
                        "pour que n'importe qui puisse le récupérer et le stocker dans son coffre perso.",
                        "Clear the owner tag when a player drops an item, so anyone can claim it.")
                .define("clearTagOnDrop", true);

        builder.pop();

        SPEC = builder.build();
    }
}
