package com.imaginarium.createplayerfilter.client;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import com.imaginarium.createplayerfilter.CreatePlayerFilterMod;
import com.imaginarium.createplayerfilter.data.OwnerData;
import com.imaginarium.createplayerfilter.filters.PlayerOwnerFilter;
import com.imaginarium.createplayerfilter.items.PlayerOwnerFilterItem;

@EventBusSubscriber(modid = CreatePlayerFilterMod.MODID, value = Dist.CLIENT)
public class ClientEvents {

    /**
     * Affiche "Propriétaire : X" sur tout item tagué (le filtre gère son propre tooltip).
     * Visible uniquement avec les tooltips avancés (F3+H) pour ne pas encombrer
     * l'affichage maintenant que la plupart des items ramassés sont tagués.
     */
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (!event.getFlags().isAdvanced()) {
            return;
        }
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof PlayerOwnerFilterItem) {
            return;
        }
        OwnerData data = PlayerOwnerFilter.getOwnerData(stack);
        if (data != null) {
            event.getToolTip().add(Component.translatable("createplayerfilter.tooltip.owner", data.getDisplayString())
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}
