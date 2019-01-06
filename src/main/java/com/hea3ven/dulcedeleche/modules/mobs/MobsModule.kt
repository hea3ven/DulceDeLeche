package com.hea3ven.dulcedeleche.modules.mobs

import com.hea3ven.dulcedeleche.Module
import net.minecraft.entity.Entity
import net.minecraft.entity.SpawnType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.LightType

object MobsModule : Module<MobsModuleConfig>() {
    override fun createDefaultConfig() = MobsModuleConfig(true)

    override fun onInitialize() {
    }

    fun onCreeperEntityCanSpawn(entity: Entity, world: IWorld, spawnType: SpawnType): Boolean {
        return world.getLightLevel(LightType.SKY, BlockPos(entity.x, entity.y, entity.z)) <= 8
    }
}

