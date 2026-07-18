package com.imaginarium.createplayerfilter.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import com.imaginarium.createplayerfilter.blocks.entity.PlayerSortingChestBlockEntity;
import com.imaginarium.createplayerfilter.registries.CreatePlayerFilterBlockEntities;

public class PlayerSortingChestBlock extends ChestBlock {

    public PlayerSortingChestBlock(Properties properties) {
        super(properties, CreatePlayerFilterBlockEntities.PLAYER_SORTING_CHEST_ENTITY::get);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PlayerSortingChestBlockEntity(pos, state);
    }

    // Rendu par modèle JSON classique : pas besoin de BlockEntityRenderer dédié
    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof PlayerSortingChestBlockEntity chest && !chest.canAccess(player)) {
            if (!level.isClientSide) {
                player.displayClientMessage(
                        Component.translatable("createplayerfilter.chest.access_denied"), true);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, level, pos, player, hit);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state,
                            @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (placer instanceof Player player && !level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof PlayerSortingChestBlockEntity chest) {
                chest.setOwner(player.getUUID());
            }
        }
    }
}
