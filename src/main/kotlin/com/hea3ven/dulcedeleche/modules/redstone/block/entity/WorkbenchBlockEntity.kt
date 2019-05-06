package com.hea3ven.dulcedeleche.modules.redstone.block.entity

import com.hea3ven.dulcedeleche.modules.redstone.container.WorkbenchContainer
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.container.Container
import net.minecraft.container.PropertyDelegate
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.chat.TranslatableComponent

class WorkbenchBlockEntity(blockEntityType: BlockEntityType<WorkbenchBlockEntity>) :
        CraftingMachineBlockEntity(0, blockEntityType) {

    private val propertyDelegate = object : PropertyDelegate {
        override fun size() = 1

        override fun get(key: Int) = when (key) {
            CAN_CRAFT_PROPERTY -> if (canCraft(null)) 1 else 0
            else -> throw IndexOutOfBoundsException("key $key")
        }

        override fun set(key: Int, value: Int) {
            if (key != CAN_CRAFT_PROPERTY) {
                throw IndexOutOfBoundsException("key $key")
            }
        }
    }

    override fun getContainerName() = TranslatableComponent("container.workbench")

    override fun createContainer(syncId: Int, player: PlayerInventory): Container {
        return WorkbenchContainer(syncId, player, this, propertyDelegate)
    }

    companion object {

        const val CAN_CRAFT_PROPERTY = 0

    }

}

