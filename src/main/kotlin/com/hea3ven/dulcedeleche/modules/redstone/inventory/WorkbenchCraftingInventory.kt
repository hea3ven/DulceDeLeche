package com.hea3ven.dulcedeleche.modules.redstone.inventory

import com.hea3ven.dulcedeleche.modules.redstone.block.entity.CraftingMachineBlockEntity
import net.minecraft.container.Container
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.util.InventoryUtil

class WorkbenchCraftingInventory(private val workbenchEntity: CraftingMachineBlockEntity,
        private val container: Container) : Inventory {
    override fun getInvStack(index: Int): ItemStack {
        return workbenchEntity.getRecipeStacks()[index]
    }

    override fun markDirty() = workbenchEntity.markDirty()

    override fun setInvStack(index: Int, stack: ItemStack) {
        val recipeStack = stack.copy()
        if (!recipeStack.isEmpty) {
            recipeStack.amount = 1
        }
        workbenchEntity.getRecipeStacks()[index] = recipeStack
        container.onContentChanged(this)
    }

    override fun removeInvStack(index: Int) = InventoryUtil.removeStack(workbenchEntity.getRecipeStacks(), index)!!

    override fun canPlayerUseInv(var1: PlayerEntity) = true

    override fun clear() = workbenchEntity.getRecipeStacks().clear()

    override fun getInvSize() = workbenchEntity.getRecipeStacks().size

    override fun takeInvStack(index: Int, amount: Int): ItemStack {
        val stack = InventoryUtil.splitStack(workbenchEntity.getRecipeStacks(), index, amount)!!
        if (!stack.isEmpty) {
            container.onContentChanged(this)
        }
        return stack
    }

    override fun isInvEmpty() = workbenchEntity.getRecipeStacks().all(ItemStack::isEmpty)

    fun craftItem(player: PlayerEntity?, craftedStack: ItemStack): ItemStack {
        val craftItem = workbenchEntity.craftItem(player, craftedStack)
        container.sendContentUpdates()
        return craftItem
    }

    fun canCraft() = workbenchEntity.canCraft()

}