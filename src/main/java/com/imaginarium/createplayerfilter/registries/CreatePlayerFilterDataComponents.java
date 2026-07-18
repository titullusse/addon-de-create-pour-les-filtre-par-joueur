package com.imaginarium.createplayerfilter.registries;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.imaginarium.createplayerfilter.CreatePlayerFilterMod;
import com.imaginarium.createplayerfilter.data.OwnerData;

public class CreatePlayerFilterDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, CreatePlayerFilterMod.MODID);

    // Composant "owner" : remplace l'ancien tag NBT (supprimé en 1.20.5+)
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<OwnerData>> OWNER =
            DATA_COMPONENTS.register("owner", () -> DataComponentType.<OwnerData>builder()
                    .persistent(OwnerData.CODEC)
                    .networkSynchronized(OwnerData.STREAM_CODEC)
                    .build());

    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }
}
