package com.imaginarium.createplayerfilter.blocks.entity;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

/**
 * Wrapper d'inventaire exposé via la capability ItemHandler : les machines
 * (Create, hoppers moddés, pipes...) ne peuvent insérer que les items
 * appartenant au propriétaire du coffre.
 */
public class FilteredInvWrapper extends InvWrapper {
    private final PlayerSortingChestBlockEntity chest;

    public FilteredInvWrapper(PlayerSortingChestBlockEntity chest) {
        super(chest);
        this.chest = chest;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return this.chest.canAcceptItem(stack) && super.isItemValid(slot, stack);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!this.chest.canAcceptItem(stack)) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
}
