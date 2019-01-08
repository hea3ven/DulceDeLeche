package com.hea3ven.dulcedeleche.modules.redstone.container

import com.hea3ven.dulcedeleche.modules.redstone.inventory.WorkbenchCraftingInventory
import net.minecraft.container.Slot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

class CraftingMachineResultSlot(private val workbenchCraftingInv: WorkbenchCraftingInventory, inventory: Inventory,
        x: Int, y: Int) : Slot(inventory, 0, x, y) {

    override fun canInsert(itemStack_1: ItemStack) = false

    override fun takeStack(amount: Int): ItemStack {
        return super.getStack().copy().apply { this.amount = amount }
    }

    override fun canTakeItems(player: PlayerEntity): Boolean {
        return workbenchCraftingInv.canCraft()
    }

    override fun onTakeItem(player: PlayerEntity?, craftedStack: ItemStack): ItemStack {
        onCrafted(craftedStack)
        return workbenchCraftingInv.craftItem(player, craftedStack)
    }

    override fun doDrawHoveringEffect(): Boolean {
        return true
    }
}