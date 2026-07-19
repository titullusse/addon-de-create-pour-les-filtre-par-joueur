package com.imaginarium.createplayerfilter.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

import com.imaginarium.createplayerfilter.CreatePlayerFilterMod;
import com.imaginarium.createplayerfilter.filters.PlayerOwnerFilter;
import com.imaginarium.createplayerfilter.items.PlayerOwnerFilterItem;

/**
 * Tague automatiquement tout item ramassé avec l'UUID du joueur qui le ramasse.
 *
 * Deux exceptions :
 * - les items qui ont déjà un propriétaire gardent leur tag (ramasser l'item
 *   d'un autre joueur ne le vole pas — le tri le renverra à son propriétaire) ;
 * - le Filtre Propriétaire Joueur, dont la liaison doit rester un geste explicite.
 */
@EventBusSubscriber(modid = CreatePlayerFilterMod.MODID)
public class PickupTagHandler {

    @SubscribeEvent
    public static void onItemPickup(ItemEntityPickupEvent.Pre event) {
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
}
