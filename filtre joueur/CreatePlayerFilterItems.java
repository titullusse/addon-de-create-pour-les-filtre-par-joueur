package com.imaginarium.createplayerfilter.registries;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import com.imaginarium.createplayerfilter.CreatePlayerFilterMod;

public class CreatePlayerFilterItems {
    public static final DeferredRegister.Items ITEMS = 
        DeferredRegister.createItems(CreatePlayerFilterMod.MODID);

    // Item du filtre joueur
    public static final DeferredItem<Item> PLAYER_OWNER_FILTER = ITEMS.register("player_owner_filter",
            () -> new Item(new Item.Properties()
                    .setId(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(CreatePlayerFilterMod.MODID, "player_owner_filter"))
                    .stacksTo(1)
            )
    );

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
