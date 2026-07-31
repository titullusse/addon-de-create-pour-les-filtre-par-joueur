package com.imaginarium.createplayerfilter.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
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

    // --- Pas de fusion en double coffre ---
    // Deux coffres adjacents peuvent appartenir à des joueurs différents :
    // chacun doit garder son propre inventaire et son propre propriétaire.

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(TYPE, ChestType.SINGLE)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                     LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        // On ignore volontairement la logique d'appairage de ChestBlock
        return state;
    }

    @Override
    public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(
            BlockState state, Level level, BlockPos pos, boolean overrideBlocked) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof PlayerSortingChestBlockEntity chest) {
            return new DoubleBlockCombiner.NeighborCombineResult.Single<>(chest);
        }
        return DoubleBlockCombiner.Combiner::acceptNone;
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
