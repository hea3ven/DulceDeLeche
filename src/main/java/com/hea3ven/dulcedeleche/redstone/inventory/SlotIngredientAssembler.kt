package com.hea3ven.dulcedeleche.redstone.inventory

import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

class SlotIngredientAssembler(val te: TileAssembler, slot: Int, xPosition: Int, yPosition: Int) :
		Slot(te.inv.invCrafting, slot, xPosition, yPosition) {

	override fun isItemValid(stack: ItemStack?): Boolean {
		return te.inv.invCrafting.isItemValidForSlot(slotIndex, stack)
	}
}