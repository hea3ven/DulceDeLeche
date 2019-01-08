package com.hea3ven.tools.commonutils.container;

import net.minecraft.container.Slot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class SlotOutput extends Slot {

    public SlotOutput(Inventory inv, int index, int xPosition, int yPosition) {
        super(inv, index, xPosition, yPosition);
    }

    @Override
    public boolean canInsert(ItemStack itemStack_1) {
        return false;
    }
}
