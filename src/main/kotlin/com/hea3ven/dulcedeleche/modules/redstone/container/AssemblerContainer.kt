package com.hea3ven.dulcedeleche.modules.redstone.container

import com.hea3ven.dulcedeleche.modules.redstone.block.entity.AssemblerBlockEntity
import net.minecraft.container.PropertyDelegate
import net.minecraft.entity.player.PlayerEntity

class AssemblerContainer(syncId: Int, craftingMachineEntity: AssemblerBlockEntity, player: PlayerEntity,
        private val propertyDelegate: PropertyDelegate) :
        CraftingMachineContainer(syncId, player, craftingMachineEntity) {

    val progress: Float
        get() = AssemblerBlockEntity.calculateProgress(propertyDelegate.get(AssemblerBlockEntity.PROGRESS_PROPERTY))

    init {
        addOutputSlots(craftingMachineEntity, 18, 98, 57, 4, 1)
        addProperties(propertyDelegate)
    }

}
