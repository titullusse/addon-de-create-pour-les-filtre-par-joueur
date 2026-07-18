package com.imaginarium.createplayerfilter.registries;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.imaginarium.createplayerfilter.CreatePlayerFilterMod;
import com.imaginarium.createplayerfilter.blocks.PlayerSortingChestBlock;

public class CreatePlayerFilterBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CreatePlayerFilterMod.MODID);

    // Coffre de tri par joueur
    public static final DeferredBlock<Block> PLAYER_SORTING_CHEST = BLOCKS.register("player_sorting_chest",
            () -> new PlayerSortingChestBlock(Block.Properties.ofFullCopy(Blocks.CHEST)));

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
