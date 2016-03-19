package com.hea3ven.dulcedeleche.redstone.inventory

import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler
import com.hea3ven.tools.commonutils.inventory.GenericContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.ClickType
import net.minecraft.item.ItemStack

class ContainerAssembler(val te: TileAssembler, playerInv: InventoryPlayer) : GenericContainer() {
	init {
		addSlots(
				SlotType.INPUT, 0, 8, 17, 3, 3, SlotIngredientAssembler::class.java, te)
		addOutputSlots(te.inv, 0, 102, 35, 1, 1)
		addOutputSlots(te.inv, 1, 131, 26, 2, 2)
		addPlayerSlots(playerInv)
		setUpdateHandler(te)
	}

	override fun slotClick(slotId: Int, dragType: Int, clickType: ClickType,
			playerIn: EntityPlayer?): ItemStack? {
		if ( slotId < 9 && clickType == ClickType.THROW && dragType == 0)
			te.inv.settingRecipe = true
		val result = super.slotClick(slotId, dragType, clickType, playerIn)
		te.inv.settingRecipe = false
		return result
	}
}