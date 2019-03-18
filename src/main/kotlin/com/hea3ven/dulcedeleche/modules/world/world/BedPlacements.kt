package com.hea3ven.dulcedeleche.modules.world.world

import com.hea3ven.dulcedeleche.DulceDeLecheMod
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.util.math.BlockPos
import net.minecraft.world.PersistentState
import net.minecraft.world.World

class BedPlacements : PersistentState("beds_data") {
    private val placements = mutableMapOf<BlockPos, Long>()

    fun onBedPlaced(world: World, pos1: BlockPos, pos2: BlockPos) {
        placements[pos1] = world.time
        placements[pos2] = world.time
        markDirty()
    }


    fun canSleep(world: World, pos: BlockPos): Boolean {
        val timePlaced = placements[pos] ?: return true
        if (world.time - timePlaced > DulceDeLecheMod.cfg.modules.world.bedSleepingRestriction) {
            placements.remove(pos)
            return true
        }
        return false
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        tag.put("bedPlacements", ListTag().apply {
            placements.forEach {
                add(CompoundTag().apply {
                    putLong("pos", it.key.asLong())
                    putLong("time", it.value)
                })
            }
        })
        return tag
    }

    override fun fromTag(tag: CompoundTag) {
        tag.getList("bedPlacements", 10).forEach {
            val placementTag = it as CompoundTag
            placements[BlockPos.fromLong(placementTag.getLong("pos"))] = placementTag.getLong("time")
        }
    }

}