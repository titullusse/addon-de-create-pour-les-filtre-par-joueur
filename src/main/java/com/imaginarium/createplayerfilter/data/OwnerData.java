package com.imaginarium.createplayerfilter.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.UUID;

/**
 * Data component qui stocke le propriétaire d'un item :
 * UUID du joueur + nom en cache pour l'affichage (tooltips, filtres Create).
 */
public record OwnerData(UUID owner, String name) {
    public static final Codec<OwnerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            UUIDUtil.CODEC.fieldOf("owner").forGetter(OwnerData::owner),
            Codec.STRING.optionalFieldOf("name", "").forGetter(OwnerData::name)
    ).apply(instance, OwnerData::new));

    public static final StreamCodec<ByteBuf, OwnerData> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, OwnerData::owner,
            ByteBufCodecs.STRING_UTF8, OwnerData::name,
            OwnerData::new);

    public OwnerData(UUID owner) {
        this(owner, "");
    }

    public boolean matchesOwner(UUID other) {
        return this.owner.equals(other);
    }

    /** Nom du joueur si connu, sinon les 8 premiers caractères de l'UUID. */
    public String getDisplayString() {
        return !this.name.isEmpty() ? this.name : this.owner.toString().substring(0, 8);
    }
}
