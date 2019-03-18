package com.hea3ven.dulcedeleche.modules.redstone.container

import com.hea3ven.dulcedeleche.DulceDeLecheMod
import com.hea3ven.dulcedeleche.modules.redstone.block.entity.CraftingMachineBlockEntity
import com.hea3ven.dulcedeleche.modules.redstone.inventory.CraftingMachineInventory
import net.minecraft.container.ArrayPropertyDelegate
import net.minecraft.container.PropertyDelegate
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.BasicInventory
import net.minecraft.item.ItemStack

class WorkbenchContainer(syncId: Int, playerInv: PlayerInventory, inventory: CraftingMachineInventory,
        propertyDelegate: PropertyDelegate) :
        CraftingMachineContainer(DulceDeLecheMod.mod.getContainerInfo("workbench").type, syncId, playerInv, inventory) {

    constructor(syncId: Int, playerInv: PlayerInventory) : this(syncId, playerInv, object :
            BasicInventory(CraftingMachineBlockEntity.inputInventoryRange.endInclusive + 1), CraftingMachineInventory {
        override fun craftItem(player: PlayerEntity?): ItemStack {
            return getInvStack(CraftingMachineBlockEntity.recipeResultIndex)
        }
    }, ArrayPropertyDelegate(1))

    private val resultSlotId: Int

    init {
        addProperties(propertyDelegate)

        resultSlotId = addSlot(
                WorkbenchResultSlot(inventory, CraftingMachineBlockEntity.recipeResultIndex, 124, 35, propertyDelegate,
                                    this)).id

    }

    override fun transferSlot(player: PlayerEntity, index: Int): ItemStack {
        if (index == resultSlotId) {
            val slot = slotList[index]
            if (slot != null && slot.hasStack()) {
                var craftedItem = slot.takeStack(slot.stack.amount)
                var crafted = craftedItem.amount
                while (slot.canTakeItems(player) && crafted <= 64) {
                    val originalCraftedItem = craftedItem.copy()
                    if (!insertItem(craftedItem, 9, 9 + 9 * 6, false)) {
                        break
                    }
                    slot.onTakeItem(player, craftedItem)

                    if (craftedItem.amount == originalCraftedItem.amount) {
                        break
                    }
                    craftedItem = slot.takeStack(slot.stack.amount)
                    crafted += craftedItem.amount
                }
                return ItemStack.EMPTY
            } else {
                throw IllegalStateException("No such slot")
            }
        } else {
            return super.transferSlot(player, index)
        }
    }

}