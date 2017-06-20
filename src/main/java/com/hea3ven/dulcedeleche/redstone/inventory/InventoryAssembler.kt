package com.hea3ven.dulcedeleche.redstone.inventory

import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.CraftingManager
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.NonNullList
import net.minecraftforge.items.ItemHandlerHelper
import net.minecraftforge.items.ItemStackHandler

class InventoryAssembler(val te: TileAssembler) : ItemStackHandler(14) {
	val invCrafting: InventoryCrafting = object : InventoryCrafting(object : Container() {
		override fun canInteractWith(playerIn: EntityPlayer?): Boolean {
			return true
		}

		override fun onCraftMatrixChanged(inventoryIn: IInventory?) {
			updateRecipe()
		}
	}, 3, 3) {
	}

	private val tmpInvCrafting: InventoryCrafting = InventoryCrafting(object : Container() {
		override fun canInteractWith(playerIn: EntityPlayer?): Boolean {
			return false
		}
	}, 3, 3)

	var canCraft: Boolean = false
		private set


	var recipeOutput: ItemStack = ItemStack.EMPTY
		private set

	private var recipeExtraOutput: NonNullList<ItemStack> = NonNullList.create()

	private var crafting: Boolean = false

	var editingRecipe: Boolean = false

	private fun updateRecipe() {
		recipeOutput = CraftingManager.findMatchingResult(invCrafting, te.world)
		if (!recipeOutput.isEmpty)
			recipeExtraOutput = CraftingManager.getRemainingItems(invCrafting, te.world)
		canCraft = calculateCanCraft()
	}

	private fun syncToTmpInvCrafting() {
		for (i in 0..8)
			tmpInvCrafting.setInventorySlotContents(i, getStackInSlot(i).copy())
	}

	private fun syncFromTmpInvCrafting() {
		for (i in 0..8)
			setStackInSlot(i, tmpInvCrafting.getStackInSlot(i))
	}

	private fun invMatchesRecipe(): Boolean {
		if (recipeOutput.isEmpty)
			return false
		for (i in 0..8) {
			val stack = getStackInSlot(i)
			if (!ItemStack.areItemsEqual(stack, invCrafting.getStackInSlot(i)))
				return false
		}
		return true
	}

	internal fun craft(player: EntityPlayer?) {
		val output = recipeOutput
		crafting = true

		syncToTmpInvCrafting()
		net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player)
		val extraStacks = CraftingManager.getRemainingItems(tmpInvCrafting, te.world)
		net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null)
		syncFromTmpInvCrafting()

		for (i in 0..8) {
			extractItem(i, 1, false)
		}

		for (extraStack in extraStacks) {
			if (!extraStack.isEmpty) {
				for (j in 0..EXTRA_OUTPUT_SLOT_COUNT - 1) {
					if(insertItem(EXTRA_OUTPUT_SLOT_START + j, extraStack, false).isEmpty)
						break
				}
			}
		}

		insertItem(OUTPUT_SLOT, output, false)
		canCraft = calculateCanCraft()
		crafting = false
	}

	fun calculateCanCraft(): Boolean {
		if(recipeOutput.isEmpty)
			return false
		val out = recipeOutput

		if (!insertItem(OUTPUT_SLOT, out, true).isEmpty)
			return false

		if (!invMatchesRecipe())
			return false

		if (recipeExtraOutput.isEmpty())
			return true

		val extraOutput = NonNullList.create<ItemStack>()
		for (stack in recipeExtraOutput)
			extraOutput.add(stack.copy())
		val tmpInv = ItemStackHandler(EXTRA_OUTPUT_SLOT_COUNT)
		for (i in 0..EXTRA_OUTPUT_SLOT_COUNT - 1)
			tmpInv.setStackInSlot(i, getStackInSlot(EXTRA_OUTPUT_SLOT_START + i).copy())
		for (stack in extraOutput) {
			var slotStack = stack
			for (i in 0..EXTRA_OUTPUT_SLOT_COUNT - 1) {
				slotStack = tmpInv.insertItem(i, stack, false)
				if (slotStack.isEmpty)
					break
			}
			if (!slotStack.isEmpty)
				return false
		}
		return true
	}

	override fun serializeNBT(): NBTTagCompound? {
		val nbt = super.serializeNBT() ?: NBTTagCompound()

		val tmpInv = ItemStackHandler(9)
		for (i in 0..8)
			tmpInv.setStackInSlot(i, invCrafting.getStackInSlot(i))
		nbt.setTag("Recipe", tmpInv.serializeNBT())
		return nbt
	}

	override fun deserializeNBT(nbt: NBTTagCompound) {
		super.deserializeNBT(nbt)

		val tmpInv = ItemStackHandler(9)
		tmpInv.deserializeNBT(nbt.getCompoundTag("Recipe"))
		for (i in 0..8)
			invCrafting.setInventorySlotContents(i, tmpInv.getStackInSlot(i))

		canCraft = calculateCanCraft()
	}

	override fun onContentsChanged(slot: Int) {
		if (!crafting && slot < 9) {
			val stack = getStackInSlot(slot)
			if (!stack.isEmpty)
				invCrafting.setInventorySlotContents(slot, ItemHandlerHelper.copyStackWithSize(stack, 1))
		}
		canCraft = calculateCanCraft()
		te.markDirty()
	}

	fun clearRecipeSlot(slot: Int) {
		invCrafting.setInventorySlotContents(slot, ItemStack.EMPTY)
	}

	override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
		if (slot < 9) {
			if (!editingRecipe) {
				if (invCrafting.getStackInSlot(slot).isEmpty)
					return stack
				if (!ItemStack.areItemsEqual(invCrafting.getStackInSlot(slot), stack))
					return stack
			}
		}
		return super.insertItem(slot, stack, simulate)
	}

	companion object {
		val OUTPUT_SLOT = 9
		val EXTRA_OUTPUT_SLOT_START = 10
		val EXTRA_OUTPUT_SLOT_COUNT = 4
		val EXTRA_OUTPUT_SLOT_END = EXTRA_OUTPUT_SLOT_START + EXTRA_OUTPUT_SLOT_COUNT - 1
	}
}