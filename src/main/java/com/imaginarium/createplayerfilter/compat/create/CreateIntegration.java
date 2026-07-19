package com.imaginarium.createplayerfilter.compat.create;

import com.mojang.logging.LogUtils;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import com.imaginarium.createplayerfilter.CreatePlayerFilterMod;

/**
 * Intégration native avec Create. Cette classe ne doit être chargée que si le
 * mod Create est présent (voir le constructeur de {@link CreatePlayerFilterMod}).
 *
 * L'enregistrement passe par un DeferredRegister : les registres de Create sont
 * construits avec le RegistryBuilder de NeoForge et sont gelés en dehors de la
 * phase RegisterEvent — un Registry.register direct pendant la construction du
 * mod lève "Registry is already frozen".
 */
public class CreateIntegration {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void register(IEventBus modEventBus) {
        DeferredRegister<ItemAttributeType> itemAttributeTypes =
                DeferredRegister.create(CreateRegistries.ITEM_ATTRIBUTE_TYPE, CreatePlayerFilterMod.MODID);
        itemAttributeTypes.register("owned_by", () -> OwnedByAttribute.TYPE);
        itemAttributeTypes.register(modEventBus);

        modEventBus.addListener((FMLCommonSetupEvent event) -> {
            boolean present = CreateBuiltInRegistries.ITEM_ATTRIBUTE_TYPE.containsKey(
                    ResourceLocation.fromNamespaceAndPath(CreatePlayerFilterMod.MODID, "owned_by"));
            LOGGER.info("Create Player Filter : attribut owned_by enregistré = {}", present);
        });
    }
}
