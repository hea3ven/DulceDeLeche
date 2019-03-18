package com.hea3ven.dulcedeleche.modules.redstone.block

import com.hea3ven.dulcedeleche.DulceDeLecheMod
import com.hea3ven.dulcedeleche.modules.redstone.block.entity.WorkbenchBlockEntity
import com.hea3ven.tools.commonutils.block.MachineBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateFactory
import net.minecraft.state.property.DirectionProperty
import net.minecraft.util.Identifier
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView

class WorkbenchBlock(screenId: Identifier, settings: Block.Settings) : MachineBlock(settings, screenId) {

    init {
        defaultState = stateFactory.defaultState.with(FACING, Direction.NORTH)
    }

    override fun createBlockEntity(view: BlockView): BlockEntity {
        return WorkbenchBlockEntity(DulceDeLecheMod.mod.getBlockInfo("workbench").getBlockEntityType())
    }

    override fun getPlacementState(context: ItemPlacementContext): BlockState {
        return defaultState.with(FACING, context.playerHorizontalFacing.opposite)

    }

    @Suppress("OverridingDeprecatedMember")
    override fun rotate(state: BlockState, rotation: Rotation): BlockState = state.with(FACING, rotation.rotate(
            state.get(FACING)))

    @Suppress("OverridingDeprecatedMember")
    override fun mirror(state: BlockState, mirror: Mirror): BlockState = state.rotate(
            mirror.getRotation(state.get(FACING)))

    override fun appendProperties(builder: StateFactory.Builder<Block, BlockState>) {
        builder.with(FACING)
    }


    //    override fun onBlockRemoved(blockState
    //    : BlockState, world: World, blockPos: BlockPos,
    //            blockState2: BlockState, boolean_1: Boolean) {
    //        if (blockState.block != blockState2.block) {
    //            val blockEntity = world.getBlockEntity(blockPos);
    //            if (blockEntity is Inventory) {
    //                ItemScatterer.spawn(world, blockPos, blockEntity);
    //                world.updateHorizontalAdjacent(blockPos, this);
    //            }

    //            super.onBlockRemoved(blockState, world, blockPos, blockState2, boolean_1);
    //        }
    //    }

    //    override fun activate(blockState_1: BlockState, world_1: World, blockPos_1: BlockPos,
    //            playerEntity_1: PlayerEntity, hand_1: Hand, direction_1: Direction, float_1: Float, float_2: Float,
    //            float_3: Float): Boolean {
    //        if (world_1.isClient) {
    //            return true;
    //        } else {
    //            val lockableContainer_1 = world_1.getBlockEntity(blockPos_1)
    //            if (lockableContainer_1 is WorkbenchBlockEntity) {
    //                playerEntity_1.openInventory(lockableContainer_1);
    //                // playerEntity_1.incrementStat(this.method_9755());
    //            }

    //            return true;
    //        }
    //    }

    //    override fun canPlaceAtSide(blockState_1: BlockState?, blockView_1: BlockView?, blockPos_1: BlockPos?,
    //            placementEnvironment_1: PlacementEnvironment?): Boolean {
    //        return false
    //    }

    companion object {
        val FACING: DirectionProperty = HorizontalFacingBlock.field_11177
    }
}