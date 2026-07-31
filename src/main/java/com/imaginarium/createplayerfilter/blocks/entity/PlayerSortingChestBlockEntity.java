package com.imaginarium.createplayerfilter.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import com.imaginarium.createplayerfilter.filters.PlayerOwnerFilter;
import com.imaginarium.createplayerfilter.registries.CreatePlayerFilterBlockEntities;

import java.util.UUID;

public class PlayerSortingChestBlockEntity extends ChestBlockEntity {
    private static final String OWNER_TAG = "Owner";
    private UUID owner;

    public PlayerSortingChestBlockEntity(BlockPos pos, BlockState blockState) {
        super(CreatePlayerFilterBlockEntities.PLAYER_SORTING_CHEST_ENTITY.get(), pos, blockState);
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

    /** Un coffre sans propriétaire reste accessible à tous. */
    public boolean canAccess(Player player) {
        return this.owner == null || this.isOwner(player);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.owner != null) {
            tag.putUUID(OWNER_TAG, this.owner);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.owner = tag.hasUUID(OWNER_TAG) ? tag.getUUID(OWNER_TAG) : null;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        if (this.owner != null) {
            tag.putUUID(OWNER_TAG, this.owner);
        }
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /**
     * Filtre les items pour accepter uniquement ceux qui appartiennent au propriétaire du coffre.
     */
    public boolean canAcceptItem(ItemStack stack) {
        if (this.owner == null) {
            return false;
        }
        return this.owner.equals(PlayerOwnerFilter.getItemOwner(stack));
    }

    /**
     * Retourne si cet item peut être extrait (propriétaire du coffre = owner de l'item).
     */
    public boolean canExtractItem(ItemStack stack) {
        return canAcceptItem(stack);
    }

    /**
     * Vérifié par les hoppers vanilla lors de l'insertion automatique :
     * seuls les items appartenant au propriétaire du coffre sont acceptés.
     * (L'insertion manuelle par le propriétaire via l'interface reste libre.)
     */
    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return canAcceptItem(stack);
    }

    // --- Sons de tonneau ---
    // On court-circuite le compteur d'ouvreurs de ChestBlockEntity (qui joue les
    // sons de coffre et anime un couvercle que notre modèle n'a pas) pour jouer
    // les sons du tonneau, cohérents avec l'apparence du bloc.

    @Override
    public void startOpen(Player player) {
        playBarrelSound(player, SoundEvents.BARREL_OPEN);
    }

    @Override
    public void stopOpen(Player player) {
        playBarrelSound(player, SoundEvents.BARREL_CLOSE);
    }

    private void playBarrelSound(Player player, SoundEvent sound) {
        if (this.level == null || this.remove || player.isSpectator()) {
            return;
        }
        this.level.playSound(null, this.worldPosition, sound, SoundSource.BLOCKS,
                0.5F, this.level.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.createplayerfilter.player_sorting_chest");
    }
}
