package com.hea3ven.dulcedeleche.modules.redstone.block.entity

import com.hea3ven.dulcedeleche.modules.redstone.container.WorkbenchContainer
import com.hea3ven.tools.commonutils.container.GenericContainer
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.TranslatableTextComponent
import net.minecraft.util.Identifier
import net.minecraft.util.PacketByteBuf

class WorkbenchBlockEntity(blockEntityType: BlockEntityType<WorkbenchBlockEntity>) :
        CraftingMachineBlockEntity(0, blockEntityType) {

    override fun getContainerName() = TranslatableTextComponent("container.workbench")

    companion object {
        fun createContainer(syncId: Int, identifier: Identifier, playerEntity: PlayerEntity,
                reader: PacketByteBuf): GenericContainer {
            val pos = reader.readBlockPos()
            val assemblerEntity = playerEntity.world.getBlockEntity(pos) as WorkbenchBlockEntity
            return WorkbenchContainer(syncId, assemblerEntity, playerEntity)
        }
    }
}

