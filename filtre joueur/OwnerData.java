package com.imaginarium.createplayerfilter.data;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.UUID;

/**
 * Data component pour tracker le propriétaire d'un item
 * Stocké en tant que UUID du joueur
 */
public class OwnerData {
    public static final Codec<OwnerData> CODEC = net.minecraft.util.ExtraCodecs.UUID
            .xmap(OwnerData::new, ownerData -> ownerData.owner)
            .fieldOf("owner")
            .codec();

    public static final StreamCodec<RegistryFriendlyByteBuf, OwnerData> STREAM_CODEC =
            ByteBufCodecs.STRING_UTF8
                    .map(s -> new OwnerData(UUID.fromString(s)), ownerData -> ownerData.owner.toString())
                    .cast();

    public final UUID owner;

    public OwnerData(UUID owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return owner.toString();
    }

    public boolean matchesOwner(UUID other) {
        return this.owner.equals(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnerData that)) return false;
        return owner.equals(that.owner);
    }

    @Override
    public int hashCode() {
        return owner.hashCode();
    }

    @Override
    public String toString() {
        return "OwnerData{" + "owner=" + owner + '}';
    }
}
