package com.hea3ven.dulcedeleche.modules.enchantments

import com.hea3ven.dulcedeleche.DulceDeLecheMod
import net.minecraft.block.BlockState
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.item.ItemStack
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

fun onBlockBreak(interactionManager: IServerPlayerInteractionManager, pos: BlockPos) {
    val areaLevel = interactionManager.player.itemsHand.map {
        EnchantmentHelper.getLevel(DulceDeLecheMod.mod.getEnchantmentInfo("area").enchantment, it)
    }.max() ?: 0
    if (areaLevel <= 0) {
        return
    }
    val trace = interactionManager.player.rayTrace(5.0, 1.0f, false)
    if (trace != null && trace is BlockHitResult && trace.blockPos == pos) {
        areaBreak(interactionManager, pos, trace.side, areaLevel)
    }
}

private fun areaBreak(interactionManager: IServerPlayerInteractionManager, pos: BlockPos, face: Direction, area: Int) {
    val dirs = Direction.Axis.values().filter { it != face.axis }.map {
        face.rotateClockwise(it).vector!!
    }
    assert(dirs.size == 2)

    val state = interactionManager.world.getBlockState(pos)

    for (i in area downTo -area) {
        for (j in area downTo -area) {
            if (i == 0 && j == 0) {
                continue
            }
            val breakPos = pos.add(dirs[0].x * i, dirs[0].y * i, dirs[0].z * i)
                    .add(dirs[1].x * j, dirs[1].y * j, dirs[1].z * j)
            breakBlock(interactionManager, breakPos, state)
        }
    }
}

private fun breakBlock(interactionManager: IServerPlayerInteractionManager, pos: BlockPos,
        originState: BlockState): Boolean {
    val state = interactionManager.world.getBlockState(pos)
    if (state.block != originState.block) {
        return false
    }
    val blockEntity = interactionManager.world.getBlockEntity(pos)
    val destroyed = interactionManager.doDestroyBlock(pos)
    if (!interactionManager.isCreative()) {
        val stack = interactionManager.player.mainHandStack
        stack.onBlockBroken(interactionManager.world, state, pos, interactionManager.player)
        if (destroyed && interactionManager.player.isUsingEffectiveTool(state)) {
            state.block.afterBreak(interactionManager.world, interactionManager.player, pos, state, blockEntity,
                                   if (stack.isEmpty) ItemStack.EMPTY else stack.copy())
        }
    }
    return true
}
