package com.hea3ven.dulcedeleche.modules.redstone.test

import com.hea3ven.unstainer.api.TestRequirement
import com.hea3ven.unstainer.api.UnstainerTest
import com.hea3ven.unstainer.api.script.Script
import com.hea3ven.unstainer.api.script.ScriptBuilder
import net.minecraft.block.Blocks
import net.minecraft.block.DispenserBlock
import net.minecraft.block.entity.DispenserBlockEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class DispenserPlantBehaviorTest {

    @UnstainerTest(requirement = TestRequirement.CHUNK)
    fun testDispenseWheatSeeds(): Script {
        return ScriptBuilder() //
                .setBlockState(BlockPos(0, 2, 0),
                        Blocks.DISPENSER.defaultState.with(DispenserBlock.FACING, Direction.UP)) //
                .run({ ctx ->
                    val dispenserEntity = (ctx.world.getBlockEntity(ctx.origin.up(2)) as DispenserBlockEntity)
                    dispenserEntity.setInvStack(0, ItemStack(Items.WATER_BUCKET, 1));
                }) //
                .setBlockState(BlockPos(0, 3, 0), Blocks.AIR.defaultState) //
                .wait(1) //
                .setBlockState(BlockPos(0, 2, 1), Blocks.REDSTONE_BLOCK.defaultState) //
                .wait(60) //
                .assertBlockState(BlockPos(0, 3, 0), Blocks.WATER.defaultState).build() //
    }

}