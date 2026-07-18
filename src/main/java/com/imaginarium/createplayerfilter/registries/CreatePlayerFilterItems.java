package com.imaginarium.createplayerfilter.registries;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.imaginarium.createplayerfilter.CreatePlayerFilterMod;

public class CreatePlayerFilterItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(CreatePlayerFilterMod.MODID);

    // Item du filtre joueur
    public static final DeferredItem<Item> PLAYER_OWNER_FILTER = ITEMS.register("player_owner_filter",
            () -> new Item(new Item.Properties().stacksTo(1)));

    // BlockItem du coffre de tri (sinon le bloc est inobtenable en jeu)
    public static final DeferredItem<BlockItem> PLAYER_SORTING_CHEST =
            ITEMS.registerSimpleBlockItem(CreatePlayerFilterBlocks.PLAYER_SORTING_CHEST);

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
