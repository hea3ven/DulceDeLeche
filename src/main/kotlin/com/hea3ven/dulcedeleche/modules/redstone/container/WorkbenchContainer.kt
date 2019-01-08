package com.hea3ven.dulcedeleche.modules.redstone.container

import com.hea3ven.dulcedeleche.modules.redstone.block.entity.WorkbenchBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack

class WorkbenchContainer(syncId: Int, craftingMachineEntity: WorkbenchBlockEntity, player: PlayerEntity) :
        CraftingMachineContainer(syncId, player, craftingMachineEntity) {

    override fun transferSlot(player: PlayerEntity, index: Int): ItemStack {
        if (index == 9) {
            val slot = slotList[index]
            if (slot != null && slot.hasStack()) {
                var craftedItem = slot.takeStack(slot.stack.amount)
                var crafted = craftedItem.amount
                while (slot.canTakeItems(player) && crafted < 64) {
                    val originalCraftedItem = craftedItem.copy()
                    if (!insertItem(craftedItem, 10, 10 + 9 * 6, false)) {
                        break
                    }
                    slot.onTakeItem(null, craftedItem)

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