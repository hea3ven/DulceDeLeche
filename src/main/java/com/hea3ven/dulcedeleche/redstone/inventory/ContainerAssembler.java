package com.hea3ven.dulcedeleche.redstone.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler;
import com.hea3ven.tools.commonutils.inventory.ContainerBase;

public class ContainerAssembler extends ContainerBase {

	private TileAssembler te;

	public ContainerAssembler(InventoryPlayer playerInv, TileAssembler te) {
		this.te = te;

		addInventoryGrid(te.getCraftingInventory(), 0, 8, 17, 3, 3);

		addSlotToContainer(new SlotCraftingAssembler(playerInv.player, te, 9, 102, 35));

		addInventoryGrid(0, 131, 26, 2, 2, SlotOutput.class, te.getExtraOutputInventory());

		addInventoryGrid(playerInv, 9, 8, 84, 3, 9);

		addInventoryGrid(playerInv, 0, 8, 142, 1, 9);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.getCraftingInventory().isUseableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack extraStack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			extraStack = slotStack.copy();

			if (index < 14) {
				if (!this.mergeItemStack(slotStack, 14, 50, true)) {
					return null;
				}
			} else if (!this.mergeItemStack(slotStack, 0, 8, false)) {
				return null;
			}

			if (slotStack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (slotStack.stackSize == extraStack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(playerIn, slotStack);
		}

		return extraStack;
	}
}
