package com.hea3ven.tools.commonutils.container;

import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;

public abstract class ContainerBase extends Container {

    protected ContainerBase(int syncId) {
        super(null, syncId);
    }

    public void addSlots(Inventory inv, int slotOff, int xOff, int yOff, int xSize, int ySize) {
        addSlots(xOff, yOff, xSize, ySize, (slot, x, y) -> new Slot(inv, slotOff + slot, x, y));
    }

    public void addSlots(int xOff, int yOff, int xSize, int ySize, SlotSupplier slotSupplier) {
        for (int y = 0; y < ySize; ++y) {
            for (int x = 0; x < xSize; ++x) {
                addSlot(slotSupplier.get(x + y * xSize, xOff + x * 18, yOff + y * 18));
            }
        }
    }

    @Override
    public boolean canUse(PlayerEntity playerEntity) {
        return true;
    }

}
