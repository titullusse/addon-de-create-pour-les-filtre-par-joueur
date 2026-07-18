package com.imaginarium.createplayerfilter.compat.create;

import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import com.imaginarium.createplayerfilter.CreatePlayerFilterMod;

/**
 * Intégration native avec Create. Cette classe ne doit être chargée que si le
 * mod Create est présent (voir le constructeur de {@link CreatePlayerFilterMod}).
 */
public class CreateIntegration {

    public static void init() {
        Registry.register(CreateBuiltInRegistries.ITEM_ATTRIBUTE_TYPE,
                ResourceLocation.fromNamespaceAndPath(CreatePlayerFilterMod.MODID, "owned_by"),
                OwnedByAttribute.TYPE);
    }
}
