package com.hea3ven.dulcedeleche.tests.redstone

import com.hea3ven.tools.commonutils.util.WorldHelper
import com.hea3ven.unstainer.test.ScriptBuilder
import com.hea3ven.unstainer.test.UnstainerTest
import net.minecraft.block.Blocks
import net.minecraft.block.FarmlandBlock
import net.minecraft.block.entity.DispenserBlockEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.math.BlockPos

val x = 40
val y = 3
val z = 0

fun getRedstoneModuleAcceptanceTests(): List<UnstainerTest> {
    return listOf(UnstainerTest("Dispenser plant seeds", createDispenserTestScript(1)),
                  UnstainerTest("Dispenser plant seeds one block higher", createDispenserTestScript(0)))
}

fun createDispenserTestScript(level: Int) = ScriptBuilder().run { world ->
    world.setBlockState(BlockPos(x + level * 2, y, z), Blocks.FARMLAND.defaultState.with(FarmlandBlock.MOISTURE, 7))
    val dispenserPos = BlockPos(x + level * 2, y + level, z + 1)
    world.setBlockState(dispenserPos, Blocks.DISPENSER.defaultState)
    WorldHelper.getBlockEntity<DispenserBlockEntity>(world, dispenserPos)
            .setInvStack(0, ItemStack(Items.WHEAT_SEEDS, 5))
}.wait(1).run { world ->
    world.setBlockState(BlockPos(x + level * 2, y + level, z + 2), Blocks.REDSTONE_BLOCK.defaultState)
}.wait(4).run { world ->
    val state = world.getBlockState(BlockPos(x + level * 2, y + 1, z))
    assert(state.block == Blocks.WHEAT, { "Wheat was not planted" })
}.build()