package com.hea3ven.dulcedeleche.redstone.inventory

import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler
import com.hea3ven.tools.commonutils.util.InventoryUtil
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.CraftingManager
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagList
import net.minecraftforge.common.util.Constants
import net.minecraftforge.items.ItemHandlerHelper
import net.minecraftforge.items.ItemStackHandler

class InventoryAssembler(val te: TileAssembler) : ItemStackHandler(5) {
	val invCrafting: InventoryCrafting = object : InventoryCrafting(object : Container() {
		override fun canInteractWith(playerIn: EntityPlayer?): Boolean {
			return true
		}

		override fun onCraftMatrixChanged(inventoryIn: IInventory?) {
			updateRecipe()
		}
	}, 3, 3) {
		override fun isItemValidForSlot(slot: Int, stack: ItemStack?): Boolean {
			if (recipe == null)
				return true
			if (settingRecipe)
				return true

			return ItemHandlerHelper.canItemStacksStack(recipe!![slot], stack)
		}

		override fun markDirty() {
			te.markDirty()
		}
	}

	private val tmpInvCrafting: InventoryCrafting = InventoryCrafting(object : Container() {
		override fun canInteractWith(playerIn: EntityPlayer?): Boolean {
			return false
		}
	}, 3, 3)

	var canCraft: Boolean = false
		private set

	internal var settingRecipe: Boolean = false

	var recipeOutput: ItemStack? = null
		private set

	var recipe: Array<ItemStack?>? = null
		private set

	private var crafting: Boolean = false

	private var recipeExtraOutput: Array<ItemStack>? = null

	private fun updateRecipe() {
		recipeOutput = CraftingManager.getInstance().findMatchingRecipe(invCrafting, te.world)
		if (!crafting) {
			if (!invMatchesRecipe()) {
				if (recipeOutput != null) {
					recipe = (0..8).map {
						invCrafting.getStackInSlot(it)?.copy()?.apply { stackSize = 1 }
					}.toTypedArray()
					syncTmpInvCrafting()
					recipeExtraOutput = CraftingManager.getInstance().getRemainingItems(invCrafting, te.world)
							.filter { it != null }.map { it!! }.toTypedArray()
				} else {
					recipe = null
					recipeExtraOutput = null
				}
			}
		}
		canCraft = calculateCanCraft()
	}

	private fun syncTmpInvCrafting() {
		for (i in 0..8)
			tmpInvCrafting.setInventorySlotContents(i, invCrafting.getStackInSlot(i)?.copy())
	}

	private fun invMatchesRecipe(): Boolean {
		if (recipe == null)
			return false
		for (i in 0..8) {
			val stack = invCrafting.getStackInSlot(i)
			if (stack != null && !ItemStack.areItemsEqual(stack, recipe!![i]))
				return false
		}
		return true
	}

	internal fun craft(player: EntityPlayer?) {
		val output = recipeOutput;
		crafting = true

		net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player);
		val extraStacks = CraftingManager.getInstance().getRemainingItems(invCrafting, te.world);
		net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);

		for (i in 0..extraStacks.size - 1) {
			val invStack = invCrafting.getStackInSlot(i);
			if (invStack != null) {
				invCrafting.decrStackSize(i, 1);
			}

			var extraStack = extraStacks[i];
			if (extraStack != null) {
				for (j in 0..3) {
					extraStack = insertItem(1 + j, extraStack, false)
					if (extraStack == null)
						break
				}
			}
		}

		insertItem(0, output, false);
		crafting = false
	}

	override fun onContentsChanged(slot: Int) {
		canCraft = calculateCanCraft()
		te.markDirty()
	}

	fun calculateCanCraft(): Boolean {
		if (recipe == null || recipeOutput == null)
			return false

		if (insertItem(0, recipeOutput, true) != null)
			return false

		if (recipeExtraOutput == null)
			return true

		val extraOutput = recipeExtraOutput!!.map { it.copy() }
		val tmpInv = ItemStackHandler(4)
		for (i in 1..4)
			tmpInv.setStackInSlot(i - 1, getStackInSlot(i)?.copy())
		for (stack in extraOutput) {
			var slotStack = stack
			for (i in 0..3) {
				slotStack = tmpInv.insertItem(i, stack, false)
				if (slotStack == null)
					break
			}
			if (slotStack != null)
				return false
		}
		return true
	}

	fun writeToNBT(compound: NBTTagCompound) {
		val nbt = compound
		nbt.setTag("CraftingInventory", InventoryUtil.serializeNBT(invCrafting))
		nbt.setTag("OutputInventory", serializeNBT())

		if (recipe != null) {
			nbt.setTag("Recipe", NBTTagList().apply {
				for (i in 0..8) {
					val stack = recipe!![i]
					if (stack != null) {
						appendTag(NBTTagCompound().apply {
							setByte("Slot", i.toByte())
							stack.writeToNBT(this)
						})
					}
				}
			})
		}
	}

	fun readFromNBT(nbt: NBTTagCompound) {
		InventoryUtil.deserializeNBT(invCrafting, nbt.getCompoundTag("CraftingInventory"))
		deserializeNBT(nbt.getCompoundTag("OutputInventory"))

		if (nbt.hasKey("Recipe")) {
			val newRecipe = arrayOfNulls<ItemStack?>(9)
			val slots = nbt.getTagList("Recipe", Constants.NBT.TAG_COMPOUND)
			(0..slots.tagCount() - 1).map { slots.getCompoundTagAt(it) }.forEach {
				newRecipe[it.getByte("Slot").toInt()] = ItemStack.loadItemStackFromNBT(it)
			}

			(0..8).forEach { tmpInvCrafting.setInventorySlotContents(it, newRecipe[it]?.copy()) }
			recipeOutput = CraftingManager.getInstance().findMatchingRecipe(tmpInvCrafting, te.world)
			if (recipeOutput != null) {
				recipeExtraOutput = CraftingManager.getInstance().getRemainingItems(tmpInvCrafting, te.world)
						.filter { it != null }.map { it!! }.toTypedArray()
			} else {
				recipe = null
				recipeExtraOutput = null
			}
			canCraft = calculateCanCraft()
		}
	}
}