package com.imaginarium.createplayerfilter;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import com.imaginarium.createplayerfilter.blocks.entity.FilteredInvWrapper;
import com.imaginarium.createplayerfilter.compat.create.CreateIntegration;
import com.imaginarium.createplayerfilter.registries.CreatePlayerFilterBlockEntities;
import com.imaginarium.createplayerfilter.registries.CreatePlayerFilterBlocks;
import com.imaginarium.createplayerfilter.registries.CreatePlayerFilterDataComponents;
import com.imaginarium.createplayerfilter.registries.CreatePlayerFilterItems;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

@Mod(CreatePlayerFilterMod.MODID)
public class CreatePlayerFilterMod {
    public static final String MODID = "createplayerfilter";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreatePlayerFilterMod(IEventBus modEventBus) {
        CreatePlayerFilterDataComponents.register(modEventBus);
        CreatePlayerFilterBlocks.register(modEventBus);
        CreatePlayerFilterItems.register(modEventBus);
        CreatePlayerFilterBlockEntities.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerCapabilities);

        if (ModList.get().isLoaded("create")) {
            CreateIntegration.init();
            LOGGER.info("Create détecté : attribut de filtre \"appartient à\" enregistré");
        }

        LOGGER.info("Create Player Filter initialized!");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(CreatePlayerFilterItems.PLAYER_OWNER_FILTER.get());
        }
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(CreatePlayerFilterItems.PLAYER_SORTING_CHEST.get());
        }
    }

    // Expose l'inventaire du coffre aux machines (Create, pipes...) avec filtrage par propriétaire
    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                CreatePlayerFilterBlockEntities.PLAYER_SORTING_CHEST_ENTITY.get(),
                (chest, side) -> new FilteredInvWrapper(chest));
    }
}
