package com.hea3ven.dulcedeleche.modules.redstone.block

import com.hea3ven.dulcedeleche.DulceDeLecheMod
import com.hea3ven.dulcedeleche.modules.redstone.block.entity.AssemblerBlockEntity
import com.hea3ven.tools.commonutils.block.MachineBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateFactory
import net.minecraft.state.property.DirectionProperty
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

class AssemblerBlock(screenId: Identifier, settings: Block.Settings) : MachineBlock(settings, screenId) {

    init {
        defaultState = stateFactory.defaultState.with(FACING, Direction.NORTH)
    }

    override fun createBlockEntity(view: BlockView): BlockEntity {
        return AssemblerBlockEntity(DulceDeLecheMod.mod.getBlockInfo("assembler").getBlockEntityType())
    }

    override fun getPlacementState(itemPlacementContext_1: ItemPlacementContext): BlockState {
        return defaultState.with(FACING, itemPlacementContext_1.playerHorizontalFacing.opposite)

    }

    @Suppress("OverridingDeprecatedMember")
    override fun rotate(state: BlockState, rotation: BlockRotation): BlockState = state.with(FACING, rotation.rotate(
            state.get(FACING)))

    @Suppress("OverridingDeprecatedMember")
    override fun mirror(state: BlockState, mirror: BlockMirror): BlockState = state.rotate(
            mirror.getRotation(state.get(FACING)))

    override fun appendProperties(builder: StateFactory.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    // TODO: drop stacks from output inventory

    companion object {
        val FACING: DirectionProperty = HorizontalFacingBlock.FACING
    }
}