package com.hea3ven.dulcedeleche.modules.world

import com.hea3ven.dulcedeleche.modules.world.world.BedPlacements
import net.minecraft.block.BedBlock
import net.minecraft.block.enums.BedPart
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.TranslatableTextComponent
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

object BedManagement {
    private var placements: BedPlacements? = null

    private fun initPlacements(world: ServerWorld) {
        if (placements == null) {
            placements = world.persistentStateManager!!.get(::BedPlacements, "beds_data")
            if (placements == null) {
                placements = BedPlacements()
                world.persistentStateManager!!.set(placements)
            }
        }
    }

    private fun onBedPlaced(world: World, pos1: BlockPos, pos2: BlockPos) {
        if (!world.isClient && world is ServerWorld) {
            initPlacements(world)
            placements!!.onBedPlaced(world, pos1, pos2)
        }
    }

    private fun canSleep(world: World, pos: BlockPos): Boolean {
        if (!world.isClient && world is ServerWorld) {
            initPlacements(world)
            return placements!!.canSleep(world, pos)
        } else {
            return true
        }
    }

    fun onPlace(itemPlacementContext: ItemPlacementContext): ActionResult {
        if (itemPlacementContext.world.isClient) {
            return ActionResult.SUCCESS
        }
        val state = itemPlacementContext.world.getBlockState(itemPlacementContext.blockPos)
        if (state.block is BedBlock) {
            var dir = state.get(BedBlock.field_11177)
            if (state.get(BedBlock.PART) == BedPart.FOOT) {
                dir = dir.opposite
            }
            val pos2 = itemPlacementContext.blockPos.offset(dir)
            onBedPlaced(itemPlacementContext.world, itemPlacementContext.blockPos, pos2)
        }
        return ActionResult.SUCCESS
    }

    fun onPlayerInteract(player: PlayerEntity, world: World, hand: Hand, hit: BlockHitResult): ActionResult {
        if (world.isClient) {
            return ActionResult.PASS
        }
        val state = world.getBlockState(hit.blockPos)
        if (state.block is BedBlock) {
            if (!BedManagement.canSleep(world, hit.blockPos)) {
                player.addChatMessage(TranslatableTextComponent("block.minecraft.bed.recently_placed"), true)
                return ActionResult.FAILURE
            }
        }
        return ActionResult.PASS
    }
}