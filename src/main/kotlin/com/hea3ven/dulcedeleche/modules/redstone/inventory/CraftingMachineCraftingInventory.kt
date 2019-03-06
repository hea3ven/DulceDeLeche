package com.hea3ven.dulcedeleche.modules.redstone.inventory

import net.minecraft.container.Container
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

class CraftingMachineCraftingInventory(private val inventory: Inventory, private val container: Container) : Inventory {
    override fun getInvStack(index: Int): ItemStack = inventory.getInvStack(index)

    override fun markDirty() = inventory.markDirty()

    override fun setInvStack(index: Int, stack: ItemStack) {
        if (!stack.isEmpty) {
            stack.amount = 1
        }
        inventory.setInvStack(index, stack)
        container.onContentChanged(this)
    }

    override fun removeInvStack(index: Int): ItemStack = inventory.removeInvStack(index)

    override fun canPlayerUseInv(player: PlayerEntity) = inventory.canPlayerUseInv(player)

    override fun clear() = inventory.clear()

    override fun getInvSize() = 9

    override fun takeInvStack(index: Int, amount: Int): ItemStack {
        val stack = inventory.takeInvStack(index, amount)
        if (!stack.isEmpty) {
            container.onContentChanged(this)
        }
        return stack
    }

    override fun isInvEmpty() = inventory.isInvEmpty

}