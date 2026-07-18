package com.imaginarium.createplayerfilter.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.imaginarium.createplayerfilter.CreatePlayerFilterMod;
import com.imaginarium.createplayerfilter.blocks.entity.PlayerSortingChestBlockEntity;

public class CreatePlayerFilterBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreatePlayerFilterMod.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PlayerSortingChestBlockEntity>>
            PLAYER_SORTING_CHEST_ENTITY = BLOCK_ENTITIES.register("player_sorting_chest",
                    () -> BlockEntityType.Builder.of(
                            PlayerSortingChestBlockEntity::new,
                            CreatePlayerFilterBlocks.PLAYER_SORTING_CHEST.get()
                    ).build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
