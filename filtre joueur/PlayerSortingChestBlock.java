package com.imaginarium.createplayerfilter.blocks;

import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.InteractionHand;

import com.imaginarium.createplayerfilter.blocks.entity.PlayerSortingChestBlockEntity;

public class PlayerSortingChestBlock extends ChestBlock {
    
    public PlayerSortingChestBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PlayerSortingChestBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, 
                                  Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PlayerSortingChestBlockEntity chest) {
                // Ouverture du coffre uniquement pour l'owner
                if (chest.isOwner(pPlayer)) {
                    pPlayer.openMenu(chest);
                } else {
                    pPlayer.displayClientMessage(
                        net.minecraft.network.chat.Component.literal("§cCe coffre n'est pas le vôtre!"),
                        true
                    );
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, 
                            LivingEntity pPlacer, ItemStack pStack) {
        if (pPlacer instanceof Player player && !pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PlayerSortingChestBlockEntity chest) {
                chest.setOwner(player.getUUID());
            }
        }
    }
}
