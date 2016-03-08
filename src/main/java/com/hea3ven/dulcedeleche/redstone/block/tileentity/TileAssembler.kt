package com.hea3ven.dulcedeleche.redstone.block.tileentity

import com.hea3ven.dulcedeleche.redstone.inventory.ContainerAssembler
import com.hea3ven.dulcedeleche.redstone.inventory.InventoryAssembler
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

class TileAssembler : TileMachine(), ITickable, IUpdateHandler {
	val inv = InventoryAssembler(this)

	private val itemHandler = ItemHandlerComposite()
			.addInputItemHandler(InvWrapper(inv.invCrafting))
			.addOutputItemHandler(inv)

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
		return ContainerAssembler(this, playerInv)
	}

	override fun hasCapability(capability: Capability<*>?, facing: EnumFacing?): Boolean {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
				super.hasCapability(capability, facing)
	}

	@Suppress("UNCHECKED_CAST")
	override fun <T : Any?> getCapability(capability: Capability<T>?, facing: EnumFacing?): T {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return itemHandler as T
		}
		return super.getCapability(capability, facing)
	}

	override fun writeToNBT(compound: NBTTagCompound) {
		super.writeToNBT(compound)

		compound.setInteger("Progress", progress)
		inv.writeToNBT(compound)
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		super.readFromNBT(compound)

		progress = compound.getInteger("Progress")
		inv.readFromNBT(compound)
	}

	companion object {
		const val MAX_PROGRESS = 200
	}
}

