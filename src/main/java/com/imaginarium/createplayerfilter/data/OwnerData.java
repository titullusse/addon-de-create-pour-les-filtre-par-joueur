package com.imaginarium.createplayerfilter.data;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;

import java.util.UUID;

/**
 * Data component qui stocke le propriétaire d'un item (UUID du joueur).
 */
public record OwnerData(UUID owner) {
    public static final Codec<OwnerData> CODEC =
            UUIDUtil.CODEC.xmap(OwnerData::new, OwnerData::owner);

    public static final StreamCodec<ByteBuf, OwnerData> STREAM_CODEC =
            UUIDUtil.STREAM_CODEC.map(OwnerData::new, OwnerData::owner);

    public boolean matchesOwner(UUID other) {
        return this.owner.equals(other);
    }
}
