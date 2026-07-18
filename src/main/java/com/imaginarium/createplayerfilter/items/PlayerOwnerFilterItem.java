package com.imaginarium.createplayerfilter.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import com.imaginarium.createplayerfilter.data.OwnerData;
import com.imaginarium.createplayerfilter.filters.PlayerOwnerFilter;
import com.imaginarium.createplayerfilter.registries.CreatePlayerFilterDataComponents;

import java.util.List;

/**
 * Item filtre liable à un joueur :
 * - clic droit : se lie à soi-même
 * - clic droit accroupi : se délie
 * - clic droit sur un autre joueur : se lie à ce joueur
 * - /cpf bind &lt;joueur&gt; : liaison par commande
 */
public class PlayerOwnerFilterItem extends Item {

    public PlayerOwnerFilterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            if (player.isSecondaryUseActive()) {
                PlayerOwnerFilter.removeOwnerTag(stack);
                player.displayClientMessage(
                        Component.translatable("createplayerfilter.filter.unbound"), true);
            } else {
                PlayerOwnerFilter.tagItemWithOwner(stack, player);
                player.displayClientMessage(
                        Component.translatable("createplayerfilter.filter.bound", player.getName()), true);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player,
                                                  LivingEntity target, InteractionHand hand) {
        if (target instanceof Player targetPlayer) {
            if (!player.level().isClientSide) {
                PlayerOwnerFilter.tagItemWithOwner(stack, targetPlayer);
                player.displayClientMessage(
                        Component.translatable("createplayerfilter.filter.bound", targetPlayer.getName()), true);
            }
            return InteractionResult.sidedSuccess(player.level().isClientSide);
        }
        return super.interactLivingEntity(stack, player, target, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        OwnerData data = stack.get(CreatePlayerFilterDataComponents.OWNER.get());
        if (data != null) {
            tooltip.add(Component.translatable("createplayerfilter.tooltip.owner", data.getDisplayString())
                    .withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable("createplayerfilter.tooltip.unbound")
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
