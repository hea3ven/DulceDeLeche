package com.hea3ven.dulcedeleche.modules.redstone.container

import com.hea3ven.dulcedeleche.DulceDeLecheMod
import com.hea3ven.dulcedeleche.modules.redstone.block.entity.AssemblerBlockEntity
import net.minecraft.container.ArrayPropertyDelegate
import net.minecraft.container.PropertyDelegate
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.BasicInventory
import net.minecraft.inventory.Inventory

class AssemblerContainer(syncId: Int, player: PlayerInventory, inventory: Inventory,
        private val propertyDelegate: PropertyDelegate) :
        CraftingMachineContainer(DulceDeLecheMod.mod.getContainerInfo("assembler").type, syncId, player, inventory) {

    constructor(syncId: Int, playerInv: PlayerInventory) : this(syncId, playerInv, BasicInventory(
            AssemblerBlockEntity.outputInventoryRange.endInclusive + 1), ArrayPropertyDelegate(1))

    val progress: Float
        get() = AssemblerBlockEntity.calculateProgress(propertyDelegate.get(AssemblerBlockEntity.PROGRESS_PROPERTY))

    init {
        addProperties(propertyDelegate)

        addOutputSlots(inventory, AssemblerBlockEntity.outputInventoryRange.start, 98, 57, 4, 1)
    }

}
