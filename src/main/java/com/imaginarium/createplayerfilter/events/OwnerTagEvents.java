package com.imaginarium.createplayerfilter.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

import com.imaginarium.createplayerfilter.CreatePlayerFilterMod;
import com.imaginarium.createplayerfilter.config.CreatePlayerFilterConfig;
import com.imaginarium.createplayerfilter.filters.PlayerOwnerFilter;
import com.imaginarium.createplayerfilter.items.PlayerOwnerFilterItem;

/**
 * Cycle de vie du tag propriétaire :
 * - au ramassage, l'item est tagué avec l'UUID du joueur qui le ramasse ;
 * - au lâcher, le tag est réinitialisé pour que n'importe qui puisse
 *   récupérer l'item et le stocker dans son coffre perso.
 *
 * Le Filtre Propriétaire Joueur est exclu des deux : sa liaison est un
 * réglage explicite (clic droit / /cpf bind) qui doit survivre au lâcher.
 * Chaque comportement est désactivable dans la config commune.
 */
@EventBusSubscriber(modid = CreatePlayerFilterMod.MODID)
public class OwnerTagEvents {

    @SubscribeEvent
    public static void onItemPickup(ItemEntityPickupEvent.Pre event) {
        if (!CreatePlayerFilterConfig.AUTO_TAG_ON_PICKUP.get()) {
            return;
        }
        Player player = event.getPlayer();
        if (player.level().isClientSide) {
            return;
        }
        ItemStack stack = event.getItemEntity().getItem();
        if (stack.isEmpty()
                || stack.getItem() instanceof PlayerOwnerFilterItem
                || PlayerOwnerFilter.hasOwner(stack)) {
            return;
        }
        PlayerOwnerFilter.tagItemWithOwner(stack, player);
    }

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        if (!CreatePlayerFilterConfig.CLEAR_TAG_ON_DROP.get()) {
            return;
        }
        if (event.getPlayer().level().isClientSide) {
            return;
        }
        ItemStack stack = event.getEntity().getItem();
        if (stack.isEmpty() || stack.getItem() instanceof PlayerOwnerFilterItem) {
            return;
        }
        PlayerOwnerFilter.removeOwnerTag(stack);
    }
}
