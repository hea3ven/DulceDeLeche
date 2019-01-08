package com.hea3ven.tools.commonutils.container;

import net.minecraft.container.Slot;

@FunctionalInterface
public interface SlotSupplier {
	Slot get(int slot, int x, int y);
}
