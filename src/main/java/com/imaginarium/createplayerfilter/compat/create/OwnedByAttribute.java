package com.imaginarium.createplayerfilter.compat.create;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttribute;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;
import io.netty.buffer.ByteBuf;
import net.minecraft.Util;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import com.imaginarium.createplayerfilter.data.OwnerData;
import com.imaginarium.createplayerfilter.filters.PlayerOwnerFilter;

import java.util.List;
import java.util.UUID;

/**
 * Attribut Create "appartient à &lt;joueur&gt;" : rend le tag propriétaire
 * sélectionnable dans les attribute filters de Create (tunnels, bras
 * mécaniques, smart chutes, déployeurs...).
 */
public record OwnedByAttribute(UUID owner, String name) implements ItemAttribute {
    public static final MapCodec<OwnedByAttribute> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            UUIDUtil.CODEC.fieldOf("owner").forGetter(OwnedByAttribute::owner),
            Codec.STRING.optionalFieldOf("name", "").forGetter(OwnedByAttribute::name)
    ).apply(instance, OwnedByAttribute::new));

    public static final StreamCodec<ByteBuf, OwnedByAttribute> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, OwnedByAttribute::owner,
            ByteBufCodecs.STRING_UTF8, OwnedByAttribute::name,
            OwnedByAttribute::new);

    public static final ItemAttributeType TYPE = new Type();

    @Override
    public boolean appliesTo(ItemStack stack, Level level) {
        return this.owner.equals(PlayerOwnerFilter.getItemOwner(stack));
    }

    @Override
    public ItemAttributeType getType() {
        return TYPE;
    }

    @Override
    public String getTranslationKey() {
        return "createplayerfilter_owned_by";
    }

    @Override
    public Object[] getTranslationParameters() {
        return new Object[]{ new OwnerData(this.owner, this.name).getDisplayString() };
    }

    public static class Type implements ItemAttributeType {
        @Override
        public ItemAttribute createAttribute() {
            return new OwnedByAttribute(Util.NIL_UUID, "");
        }

        @Override
        public List<ItemAttribute> getAllAttributes(ItemStack stack, Level level) {
            OwnerData data = PlayerOwnerFilter.getOwnerData(stack);
            return data == null ? List.of() : List.of(new OwnedByAttribute(data.owner(), data.name()));
        }

        @Override
        public MapCodec<? extends ItemAttribute> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, ? extends ItemAttribute> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
