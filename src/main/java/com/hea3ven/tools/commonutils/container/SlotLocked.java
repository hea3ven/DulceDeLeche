package com.hea3ven.tools.commonutils.container;

import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class SlotLocked extends Slot {
    public SlotLocked(Inventory inv, int slot, int x, int y) {
        super(inv, slot, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack onTakeItem(PlayerEntity playerIn, ItemStack stack) {
        return stack;
    }

    @Override
    public ItemStack takeStack(int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean hasStack() {
        return false;
    }
}
