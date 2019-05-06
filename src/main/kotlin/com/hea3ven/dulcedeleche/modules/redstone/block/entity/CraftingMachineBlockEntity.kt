package com.hea3ven.dulcedeleche.modules.redstone.block.entity

import com.hea3ven.dulcedeleche.modules.redstone.inventory.CraftingMachineInventory
import com.hea3ven.tools.commonutils.block.entity.MachineBlockEntity
import com.hea3ven.tools.commonutils.util.InventoryExtraUtil
import com.hea3ven.tools.commonutils.util.ItemStackUtil
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.container.Container
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.BasicInventory
import net.minecraft.inventory.CraftingInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.recipe.CraftingRecipe
import net.minecraft.recipe.RecipeType
import net.minecraft.util.DefaultedList

abstract class CraftingMachineBlockEntity(additionalSlots: Int, blockEntityType: BlockEntityType<*>) :
        MachineBlockEntity(blockEntityType), CraftingMachineInventory {

    private val inventory = DefaultedList.create(28 + additionalSlots, ItemStack.EMPTY)!!

    //    private val recipe = DefaultedList.create(9, ItemStack.EMPTY)!!

    override fun getInvStack(index: Int) = inventory[index]
    override fun canPlayerUseInv(player: PlayerEntity) = true
    override fun removeInvStack(index: Int) = Inventories.removeStack(inventory, index)!!
    override fun clear() = inventory.clear()
    override fun isInvEmpty() = inventory.all(ItemStack::isEmpty)
    override fun takeInvStack(index: Int, amount: Int): ItemStack {
        if (index == recipeResultIndex) {
            return inventory[index].copy()
        } else {
            return Inventories.splitStack(inventory, index, amount)!!
        }
    }

    override fun setInvStack(index: Int, stack: ItemStack) {
        if (index in recipeInventoryRange) {
            val recipeStack = stack.copy()
            if (!recipeStack.isEmpty) {
                recipeStack.amount = 1
            }
            inventory[index] = recipeStack

            inventory[9] = getRecipe()?.output ?: ItemStack.EMPTY
            markDirty()
        } else {
            if (stack.amount > invMaxStackAmount) {
                stack.amount = invMaxStackAmount
            }
            inventory[index] = stack
        }
    }

    override fun getInvSize() = inventory.size

    //    fun getRecipeStacks() = recipe

    override fun toTag(tag: CompoundTag): CompoundTag {
        super.toTag(tag)
        Inventories.toTag(tag, inventory)
        //        InventoryExtraUtil.serialize(tag, recipe, "Recipe")
        return tag
    }

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)
        if (tag.containsKey("Recipe")) {
            // Convert from pre 1.2.0 format
            val recipe = DefaultedList.create(9, ItemStack.EMPTY)
            InventoryExtraUtil.deserialize(tag, recipe, "Recipe")
            val oldInventory = DefaultedList.create(inventory.size - 10, ItemStack.EMPTY)
            Inventories.fromTag(tag, oldInventory)
            recipe.forEachIndexed { index, stack -> inventory[index] = stack }
            oldInventory.forEachIndexed { index, stack -> inventory[index + 10] = stack }
        } else {
            inventory.clear()
            Inventories.fromTag(tag, inventory)
        }
    }

    fun canCraft(player: PlayerEntity?): Boolean {
        val invCopy = BasicInventory(*inventory.map(ItemStack::copy).toTypedArray())
        return !tryCraftItem(player, invCopy).isEmpty
    }

    override fun craftItem(player: PlayerEntity?): ItemStack {
        if (world?.isClient == false) {
            markDirty()
            return tryCraftItem(player, this)
        }
        return inventory[recipeResultIndex]
    }

    private fun tryCraftItem(player: PlayerEntity?, inventory: Inventory): ItemStack {
        val recipe = getRecipe() ?: return ItemStack.EMPTY

        if (!onCraftItem(recipe, player, inventory)) {
            return ItemStack.EMPTY
        }

        return recipe.output.copy()
    }

    protected open fun onCraftItem(recipe: CraftingRecipe, player: PlayerEntity?, inventory: Inventory): Boolean {
        if (!consumeItems(inventory)) {
            return false
        }

        val remainingStacks = recipe.getRemainingStacks(getCraftingInventory())
        if (!storeRemainders(remainingStacks, inventory, player)) {
            return false
        }

        return true
    }

    private fun consumeItems(inventory: Inventory): Boolean {
        for (index in recipeInventoryRange) {
            val recipeStack = inventory.getInvStack(index)
            if (!recipeStack.isEmpty && !consumeItem(recipeStack, inventory)) {
                // TODO: Test if this works
                // not enough ingredients to craft
                return false
            }
        }
        return true
    }

    private fun consumeItem(stack: ItemStack, inventory: Inventory): Boolean {
        for (invIndex in inputInventoryRange) {
            val invStack = inventory.getInvStack(invIndex)
            if (ItemStackUtil.areStacksCombinable(stack, invStack)) {
                invStack.amount -= 1
                return true
            }
        }
        // TODO: consume from player inventory
        return false
    }

    private fun storeRemainders(remainingStacks: DefaultedList<ItemStack>, inventory: Inventory,
            player: PlayerEntity?): Boolean {
        for (remainingStack in remainingStacks) {
            if (!remainingStack.isEmpty) {
                if (!storeRemainder(remainingStack, inventory, player)) {
                    return false
                }
            }
        }
        return true
    }

    private fun storeRemainder(remainingStack: ItemStack, inventory: Inventory, player: PlayerEntity?): Boolean {
        for (invIndex in inputInventoryRange) {
            val originalStack = inventory.getInvStack(invIndex)
            if (originalStack.isEmpty) {
                inventory.setInvStack(invIndex, remainingStack)
            } else if (ItemStackUtil.areStacksCombinable(originalStack, remainingStack)) {
                remainingStack.addAmount(originalStack.amount)
                inventory.setInvStack(invIndex, remainingStack)
            } else if (player != null) {
                if (!player.inventory.insertStack(remainingStack)) {
                    player.dropItem(remainingStack, false)
                }
            } else {
                return false
            }
        }
        return true
    }

    private fun getCraftingInventory(): CraftingInventory {
        val craftInv = CraftingInventory(object : Container(null, 0) {
            override fun canUse(var1: PlayerEntity?) = false
            override fun getType() = null
        }, 3, 3)
        recipeInventoryRange.forEachIndexed { index, i -> craftInv.setInvStack(index, getInvStack(i).copy()) }
        return craftInv
    }

    private fun getRecipe(): CraftingRecipe? {
        val craftInv = getCraftingInventory()
        return world?.recipeManager?.getFirstMatch(RecipeType.CRAFTING, craftInv, world)?.orElse(null)

    }

    companion object {
        val recipeInventoryRange = 0 until 9
        val recipeResultIndex = recipeInventoryRange.endInclusive + 1
        val inputInventoryRange = (recipeResultIndex + 1) until 28
    }
}
