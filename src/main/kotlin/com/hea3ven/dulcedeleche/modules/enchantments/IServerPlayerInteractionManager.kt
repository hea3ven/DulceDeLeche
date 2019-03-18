package com.hea3ven.dulcedeleche.modules.enchantments

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos

interface IServerPlayerInteractionManager {
    val player: PlayerEntity
    val world: ServerWorld

    fun isCreative(): Boolean

    fun doDestroyBlock(pos: BlockPos): Boolean
}