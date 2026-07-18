package com.imaginarium.createplayerfilter;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import com.imaginarium.createplayerfilter.registries.CreatePlayerFilterBlocks;
import com.imaginarium.createplayerfilter.registries.CreatePlayerFilterItems;
import com.imaginarium.createplayerfilter.registries.CreatePlayerFilterBlockEntities;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(CreatePlayerFilterMod.MODID)
public class CreatePlayerFilterMod {
    public static final String MODID = "createplayerfilter";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreatePlayerFilterMod(IEventBus modEventBus, ModContainer modContainer) {
        // Enregistrer les events bus
        CreatePlayerFilterItems.register(modEventBus);
        CreatePlayerFilterBlocks.register(modEventBus);
        CreatePlayerFilterBlockEntities.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        LOGGER.info("Create Player Filter initialized!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup for Create Player Filter!");
    }
}
