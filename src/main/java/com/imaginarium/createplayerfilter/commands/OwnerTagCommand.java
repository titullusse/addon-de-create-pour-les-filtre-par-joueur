package com.imaginarium.createplayerfilter.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import com.imaginarium.createplayerfilter.CreatePlayerFilterMod;
import com.imaginarium.createplayerfilter.filters.PlayerOwnerFilter;
import com.imaginarium.createplayerfilter.items.PlayerOwnerFilterItem;

@EventBusSubscriber(modid = CreatePlayerFilterMod.MODID)
public class OwnerTagCommand {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
            Commands.literal("cpf")
                .then(Commands.literal("tag")
                    .then(Commands.argument("player", EntityArgument.player())
                        .executes(ctx -> {
                            CommandSourceStack source = ctx.getSource();
                            ServerPlayer targetPlayer = EntityArgument.getPlayer(ctx, "player");
                            ServerPlayer executor = source.getPlayer();

                            if (executor == null) {
                                source.sendFailure(Component.translatable("createplayerfilter.command.player_only"));
                                return 0;
                            }

                            ItemStack held = executor.getMainHandItem();
                            if (held.isEmpty()) {
                                source.sendFailure(Component.translatable("createplayerfilter.command.tag.no_item"));
                                return 0;
                            }

                            // Tagger l'item avec l'UUID (et le nom) du joueur cible
                            PlayerOwnerFilter.tagItemWithOwner(held, targetPlayer);
                            source.sendSuccess(() -> Component.translatable(
                                    "createplayerfilter.command.tag.success", targetPlayer.getName()), true);
                            return 1;
                        })
                    )
                )
                .then(Commands.literal("bind")
                    .then(Commands.argument("player", EntityArgument.player())
                        .executes(ctx -> {
                            CommandSourceStack source = ctx.getSource();
                            ServerPlayer targetPlayer = EntityArgument.getPlayer(ctx, "player");
                            ServerPlayer executor = source.getPlayer();

                            if (executor == null) {
                                source.sendFailure(Component.translatable("createplayerfilter.command.player_only"));
                                return 0;
                            }

                            ItemStack held = executor.getMainHandItem();
                            if (!(held.getItem() instanceof PlayerOwnerFilterItem)) {
                                source.sendFailure(Component.translatable("createplayerfilter.command.bind.not_filter"));
                                return 0;
                            }

                            PlayerOwnerFilter.tagItemWithOwner(held, targetPlayer);
                            source.sendSuccess(() -> Component.translatable(
                                    "createplayerfilter.filter.bound", targetPlayer.getName()), true);
                            return 1;
                        })
                    )
                )
                .then(Commands.literal("untag")
                    .executes(ctx -> {
                        CommandSourceStack source = ctx.getSource();
                        ServerPlayer executor = source.getPlayer();

                        if (executor == null) {
                            source.sendFailure(Component.translatable("createplayerfilter.command.player_only"));
                            return 0;
                        }

                        ItemStack held = executor.getMainHandItem();
                        if (held.isEmpty()) {
                            source.sendFailure(Component.translatable("createplayerfilter.command.tag.no_item"));
                            return 0;
                        }

                        PlayerOwnerFilter.removeOwnerTag(held);
                        source.sendSuccess(() -> Component.translatable(
                                "createplayerfilter.command.untag.success"), true);
                        return 1;
                    })
                )
        );
    }
}
