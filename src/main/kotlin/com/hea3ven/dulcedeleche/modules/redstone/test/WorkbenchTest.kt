package com.hea3ven.dulcedeleche.modules.redstone.test

import com.hea3ven.dulcedeleche.DulceDeLecheMod
import com.hea3ven.dulcedeleche.modules.redstone.block.WorkbenchBlock
import com.hea3ven.dulcedeleche.modules.redstone.block.entity.CraftingMachineBlockEntity
import com.hea3ven.dulcedeleche.modules.redstone.block.entity.WorkbenchBlockEntity
import com.hea3ven.unstainer.api.TestRequirement
import com.hea3ven.unstainer.api.UnstainerTest
import com.hea3ven.unstainer.api.script.Script
import com.hea3ven.unstainer.api.script.ScriptBuilder
import net.minecraft.block.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BoundingBox
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.GameMode

class WorkbenchTest {

    @UnstainerTest(requirement = TestRequirement.CHUNK)
    fun testWorkbenchDrops(): Script {
        return ScriptBuilder() //
                .setBlockState(BlockPos(0, 4, 0),
                        DulceDeLecheMod.mod.getBlockInfo("workbench").block.defaultState.with(WorkbenchBlock.FACING,
                                Direction.NORTH)) //
                .run({ ctx ->
                    val workbenchEntity = (ctx.world.getBlockEntity(ctx.origin.up(4)) as WorkbenchBlockEntity)
                    workbenchEntity.setInvStack(CraftingMachineBlockEntity.recipeInventoryRange.first,
                            ItemStack(Items.COBBLESTONE, 1));
                    workbenchEntity.setInvStack(CraftingMachineBlockEntity.recipeInventoryRange.first + 1,
                            ItemStack(Items.COBBLESTONE, 1));
                    workbenchEntity.setInvStack(CraftingMachineBlockEntity.recipeInventoryRange.first + 2,
                            ItemStack(Items.COBBLESTONE, 1));
                    for (slot in CraftingMachineBlockEntity.inputInventoryRange) {
                        workbenchEntity.setInvStack(slot, ItemStack(Items.COBBLESTONE, 1));
                    }
                }) //
//                .playerMode(GameMode.SURVIVAL) //
//                .playerMove(Vec3d(5.0, 4.0, 0.5), 90.0f, 20.0f) //, BlockPos(0, 4, 0)) //
//                .playerInventory { inv ->
//                    inv.setInvStack(0, ItemStack(Items.DIAMOND_AXE, 1))
//                    inv.selectedSlot = 0
//                }.wait(1) //
//                .playerStartLeftClick() //
                .wait(25) //
                .assertBlockState(BlockPos(0, 4, 0), Blocks.AIR.defaultState) //
//                .assertItemEntities(BoundingBox(BlockPos(-1, 4, -1), BlockPos(1, 6, 1)),
//                        arrayOf(ItemStack(Items.COBBLESTONE,
//                                3 + CraftingMachineBlockEntity.inputInventoryRange.count()),
//                                ItemStack(DulceDeLecheMod.mod.getBlockInfo("workbench").item, 1))) //
                .build() //
    }

}