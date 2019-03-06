package com.hea3ven.dulcedeleche.modules.redstone.container

import com.hea3ven.dulcedeleche.modules.redstone.block.entity.CraftingMachineBlockEntity
import com.hea3ven.dulcedeleche.modules.redstone.inventory.CraftingMachineCraftingInventory
import com.hea3ven.tools.commonutils.container.GenericContainer
import net.minecraft.container.ContainerType
import net.minecraft.container.SlotActionType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import kotlin.math.max

open class CraftingMachineContainer(containerType: ContainerType<*>, syncId: Int, playerInv: PlayerInventory,
        inventory: Inventory) : GenericContainer(syncId, containerType) {

    private val craftingInv = CraftingMachineCraftingInventory(inventory, this)

    init {
        addGenericSlots(craftingInv, CraftingMachineBlockEntity.recipeInventoryRange.first, 30, 17, 3, 3)
        //        addGenericSlots(124, 35, 1, 1) { _, x, y ->
        //            CraftingMachineResultSlot(craftingInv, resultInv, x, y)
        //        }
        addGenericSlots(inventory, CraftingMachineBlockEntity.inputInventoryRange.first, 8, 84, 9, 2)
        addPlayerSlots(playerInv, 8, 133)
    }

    override fun onSlotClick(index: Int, clickType: Int, actionType: SlotActionType, player: PlayerEntity): ItemStack {
        if (index in 0..8) {
            if (actionType == SlotActionType.PICKUP || (actionType == SlotActionType.QUICK_CRAFT && index != -999)) {
                val slot = slotList[index]
                if (slot != null) {
                    slot.stack = player.inventory.cursorStack.copy()
                    slot.markDirty()
                }
            }
            return ItemStack.EMPTY
        } else {
            return super.onSlotClick(index, clickType, actionType, player)
        }
    }

    override fun insertItem(stack: ItemStack, fromIndex: Int, toIndex: Int, reverse: Boolean): Boolean {
        return super.insertItem(stack, max(9, fromIndex), max(9, toIndex), reverse)
    }

}