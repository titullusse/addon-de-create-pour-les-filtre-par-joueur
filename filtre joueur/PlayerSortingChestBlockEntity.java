package com.imaginarium.createplayerfilter.blocks.entity;

import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import com.imaginarium.createplayerfilter.registries.CreatePlayerFilterBlockEntities;

import java.util.UUID;

public class PlayerSortingChestBlockEntity extends ChestBlockEntity {
    private UUID owner;
    private static final String OWNER_TAG = "Owner";

    public PlayerSortingChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
        this.owner = null;
    }

    public void setOwner(UUID ownerUUID) {
        this.owner = ownerUUID;
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public UUID getOwner() {
        return this.owner;
    }

    public boolean isOwner(Player player) {
        return this.owner != null && this.owner.equals(player.getUUID());
    }

    @Override
    public void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (this.owner != null) {
            pTag.putUUID(OWNER_TAG, this.owner);
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.hasUUID(OWNER_TAG)) {
            this.owner = pTag.getUUID(OWNER_TAG);
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        if (this.owner != null) {
            tag.putUUID(OWNER_TAG, this.owner);
        }
        return tag;
    }

    @Override
    public Packet<?> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /**
     * Filtre les items pour accepter uniquement ceux qui appartiennent au propriétaire du coffre
     */
    public boolean canAcceptItem(ItemStack stack) {
        if (this.owner == null) {
            return false;
        }

        // Vérifier si l'item a une composante owner
        if (stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains("Owner", 8)) { // Type 8 = String
                String ownerString = tag.getString("Owner");
                try {
                    UUID itemOwner = UUID.fromString(ownerString);
                    return itemOwner.equals(this.owner);
                } catch (IllegalArgumentException e) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Retourne si cet item peut être extrait (propriétaire du coffre = owner de l'item)
     */
    public boolean canExtractItem(ItemStack stack) {
        return canAcceptItem(stack);
    }

    @Override
    public String getDisplayName() {
        if (this.owner != null) {
            return "§6[" + this.owner.toString().substring(0, 8) + "...] §rSortage Joueur";
        }
        return "Coffre de Tri Joueur";
    }
}
