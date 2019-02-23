package com.hea3ven.dulcedeleche.modules.mobs

import com.hea3ven.dulcedeleche.ModDulceDeLeche
import com.hea3ven.tools.commonutils.mod.ModModule
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.SpawnType
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.SpiderEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.LightType

object MobsModule : ModModule() {

    fun onCreeperEntityCanSpawn(entity: Entity, world: IWorld, spawnType: SpawnType): Boolean {
        return world.getLightLevel(LightType.SKY, BlockPos(entity.x, entity.y, entity.z)) <= 8
    }

    fun onZombiePrepareEntityAttributes(entity: LivingEntity) {
        entity.getAttributeInstance(EntityAttributes.KNOCKBACK_RESISTANCE).addModifier(
                EntityAttributeModifier("Dulce De Leche modifier",
                                        ModDulceDeLeche.cfg.modules.mobs.zombieKnockbackAttributeMultiplier,
                                        EntityAttributeModifier.Operation.MULTIPLY_TOTAL))
    }

    fun onMobEntityDoAttack(entity: LivingEntity, target: Entity) {
        if (target !is LivingEntity) {
            return
        }
        if (ModDulceDeLeche.cfg.modules.mobs.spidersApplySlowness) {
            if (entity is SpiderEntity) {
                target.addPotionEffect(StatusEffectInstance(StatusEffects.SLOWNESS, 200, 1))
            }
        }
    }

    fun onCaveSpiderEntityDoAttack(entity: LivingEntity, target: Entity) {
        if (target !is LivingEntity) {
            return
        }
        target.addPotionEffect(StatusEffectInstance(StatusEffects.WEAKNESS, 15 * 20))
    }

}

