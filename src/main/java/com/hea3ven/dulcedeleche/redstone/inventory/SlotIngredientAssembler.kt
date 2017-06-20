package com.hea3ven.dulcedeleche.redstone.inventory

import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler
import com.hea3ven.tools.commonutils.inventory.AdvancedSlotWrapper
import com.hea3ven.tools.commonutils.inventory.ContainerBase
import com.hea3ven.tools.commonutils.inventory.IAdvancedSlot
import com.hea3ven.tools.commonutils.inventory.SlotItemHandlerBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

class SlotIngredientAssembler(val te: TileAssembler, slot: Int, xPosition: Int, yPosition: Int) :
		SlotItemHandlerBase(te.inv, slot, xPosition, yPosition), IAdvancedSlot {

	val delegate = AdvancedSlotWrapper(this)

	override fun onQuickMove(container: ContainerBase, player: EntityPlayer?, clickedButton: Int):
			ItemStack? {
		return delegate.onQuickMove(container, player, clickedButton)
	}

	override fun onPickUp(player: EntityPlayer?, clickedButton: Int): ItemStack? {
		val slotStack = stack
		val playerStack = player!!.inventory.itemStack

		if (slotStack.isEmpty && playerStack.isEmpty) {
			te.inv.clearRecipeSlot(slotNumber)
		}
		te.inv.editingRecipe = true
		val result = delegate.onPickUp(player, clickedButton)
		te.inv.editingRecipe = false
		return result
	}

	override fun onSwapPlayerStack(clickedButton: Int, player: EntityPlayer?, equipSlot: Int) {
		te.inv.editingRecipe = true
		val result = delegate.onSwapPlayerStack(clickedButton, player, equipSlot)
		te.inv.editingRecipe = false
		return result
	}

	override fun onClone(player: EntityPlayer?) {
		return delegate.onClone(player)
	}

	override fun onThrow(player: EntityPlayer?, clickedButton: Int) {
		return delegate.onThrow(player, clickedButton)
	}

	override fun onPickUpAll(container: ContainerBase?, player: EntityPlayer?, clickedButton: Int) {
		return delegate.onPickUpAll(container, player, clickedButton)
	}

	override fun canDragIntoSlot(): Boolean {
		te.inv.editingRecipe = true
		val result = delegate.canDragIntoSlot()
		te.inv.editingRecipe = false
		return result
	}

	override fun canTransferFromSlot(): Boolean {
		return delegate.canTransferFromSlot()
	}

	override fun transferFrom(slot: IAdvancedSlot?): Boolean {
		return delegate.transferFrom(slot)
	}

	override fun getImmutableStack(): ItemStack? {
		return delegate.immutableStack
	}

	override fun extract(size: Int): ItemStack? {
		return delegate.extract(size)
	}

}