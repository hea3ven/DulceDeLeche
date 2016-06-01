package com.hea3ven.dulcedeleche.redstone.block.tileentity

import com.hea3ven.dulcedeleche.redstone.inventory.InventoryAssembler
import com.hea3ven.dulcedeleche.redstone.inventory.SlotIngredientAssembler
import com.hea3ven.tools.commonutils.inventory.GenericContainer
import com.hea3ven.tools.commonutils.inventory.IUpdateHandler
import com.hea3ven.tools.commonutils.inventory.ItemHandlerComposite
import com.hea3ven.tools.commonutils.tileentity.TileMachine
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.wrapper.InvWrapper
import net.minecraftforge.items.wrapper.RangedWrapper

class TileAssembler : TileMachine(), ITickable, IUpdateHandler {
	val inv = InventoryAssembler(this)

	private val itemHandler = ItemHandlerComposite()
			.addInputItemHandler(RangedWrapper(inv, 0, 9))
			.addOutputItemHandler(RangedWrapper(inv, InventoryAssembler.OUTPUT_SLOT,
					InventoryAssembler.EXTRA_OUTPUT_SLOT_END))

	var progress = 0

	override fun update() {
		if (!world.isRemote) {
			if (inv.canCraft) {
				progress++
				if (progress >= MAX_PROGRESS) {
					inv.craft(null)
					progress = 0
				}
			} else
				progress = 0
		}
	}

	override fun getFieldCount() = 1

	override fun setField(id: Int, data: Int) {
		progress = data
	}

	override fun getField(i: Int) = progress

	fun getContainer(playerInv: InventoryPlayer): Container {
		return GenericContainer()
				.addGenericSlots(8, 17, 3, 3, { slot, x, y -> SlotIngredientAssembler(this, slot, x, y) })
				.addOutputSlots(inv, 9, 102, 35, 1, 1)
				.addOutputSlots(inv, 10, 131, 26, 2, 2)
				.addPlayerSlots(playerInv)
				.addGenericSlots(InvWrapper(inv.invCrafting), 0, 5000, 0, 9 * 9, 1)
				.setUpdateHandler(this)
	}

	override fun hasCapability(capability: Capability<*>?, facing: EnumFacing?): Boolean {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
				super.hasCapability(capability, facing)
	}

	override fun <T : Any?> getCapability(capability: Capability<T>?, facing: EnumFacing?): T {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler)
		return super.getCapability(capability, facing)
	}

	override fun writeToNBT(compound: NBTTagCompound) {
		super.writeToNBT(compound)

		compound.setInteger("Progress", progress)
		compound.setTag("Inventory", inv.serializeNBT());
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)

		progress = compound.getInteger("Progress")
		inv.deserializeNBT(compound.getCompoundTag("Inventory"))
	}

	companion object {
		const val MAX_PROGRESS = 200
	}
}

