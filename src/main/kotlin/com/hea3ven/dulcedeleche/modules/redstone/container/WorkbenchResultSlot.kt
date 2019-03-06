package com.hea3ven.dulcedeleche.modules.redstone.container

import com.hea3ven.dulcedeleche.modules.redstone.block.entity.WorkbenchBlockEntity
import com.hea3ven.dulcedeleche.modules.redstone.inventory.CraftingMachineInventory
import net.minecraft.container.Container
import net.minecraft.container.PropertyDelegate
import net.minecraft.container.Slot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack

class WorkbenchResultSlot(private val craftingMachineInv: CraftingMachineInventory, slot: Int, x: Int, y: Int,
        private val propertyDelegate: PropertyDelegate, private val container: Container) :
        Slot(craftingMachineInv, slot, x, y) {

    override fun canInsert(itemStack_1: ItemStack?): Boolean {
        return false
    }

    override fun takeStack(amount: Int): ItemStack {
        return stack.copy().apply { this.amount = amount }
    }

    override fun onTakeItem(player: PlayerEntity, craftedStack: ItemStack): ItemStack {
        val stack = super.onTakeItem(player, craftedStack)
        val resultStack = craftingMachineInv.craftItem(player)
        if (resultStack.isEmpty) {
            return ItemStack.EMPTY
        }
        container.onContentChanged(craftingMachineInv)
        return resultStack
    }

    override fun canTakeItems(player: PlayerEntity): Boolean {
        return propertyDelegate.get(WorkbenchBlockEntity.CAN_CRAFT_PROPERTY) == 1
    }

}
