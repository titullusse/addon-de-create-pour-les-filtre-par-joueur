package com.imaginarium.createplayerfilter.commands;

import com.imaginarium.createplayerfilter.filters.PlayerOwnerFilter;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = "createplayerfilter", bus = Mod.EventBusSubscriber.Bus.FORGE)
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
                            Player targetPlayer = EntityArgument.getPlayer(ctx, "player");
                            Player executor = source.getPlayer();
                            
                            if (executor == null) {
                                source.sendFailure(Component.literal("Seul un joueur peut exécuter cette commande"));
                                return 0;
                            }
                            
                            ItemStack held = executor.getMainHandItem();
                            if (held.isEmpty()) {
                                executor.displayClientMessage(
                                    Component.literal("§cVous devez tenir un item!"),
                                    true
                                );
                                return 0;
                            }
                            
                            // Tagger l'item avec l'UUID du joueur cible
                            PlayerOwnerFilter.tagItemWithOwner(held, targetPlayer.getUUID());
                            executor.displayClientMessage(
                                Component.literal("§aItem tagué au propriétaire: §e" + targetPlayer.getName().getString()),
                                true
                            );
                            return 1;
                        })
                    )
                )
                .then(Commands.literal("untag")
                    .executes(ctx -> {
                        Player executor = ctx.getSource().getPlayer();
                        if (executor == null) {
                            ctx.getSource().sendFailure(Component.literal("Seul un joueur peut exécuter cette commande"));
                            return 0;
                        }
                        
                        ItemStack held = executor.getMainHandItem();
                        if (held.isEmpty()) {
                            executor.displayClientMessage(
                                Component.literal("§cVous devez tenir un item!"),
                                true
                            );
                            return 0;
                        }
                        
                        PlayerOwnerFilter.removeOwnerTag(held);
                        executor.displayClientMessage(
                            Component.literal("§aTag propriétaire retiré"),
                            true
                        );
                        return 1;
                    })
                )
        );
    }
}
