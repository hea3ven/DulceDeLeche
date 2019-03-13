package com.hea3ven.dulcedeleche.modules.redstone

import net.minecraft.entity.damage.DamageSource
import net.minecraft.item.ItemStack
import net.minecraft.item.block.BlockItem
import net.minecraft.world.loot.context.LootContext
import net.minecraft.world.loot.context.LootContextParameters
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import kotlin.math.exp
import kotlin.math.min
import kotlin.math.roundToInt

fun onApplySurvivesExplosionLootCondition(lootContext: LootContext, info: CallbackInfoReturnable<Boolean>) {
    val pos = lootContext.get(LootContextParameters.POSITION);
    val state = lootContext.world.getBlockState(pos)

    val chance = 0.007396f * state.block.blastResistance * state.block.blastResistance + 0.63333f

    info.returnValue = lootContext.random.nextFloat() <= chance;
}

fun onDamageSetHealthItemEntity(itemEntity: HealthAccess, damageSource: DamageSource, damage: Float,
        info: CallbackInfoReturnable<Boolean>) {
    if (damageSource.isExplosive) {
        val item = itemEntity.stack.item
        if(item is BlockItem) {
            val resistance = item.block.blastResistance
            itemEntity.health = min(3, (3.0 * exp((damage* 2.5) / (40.0 / resistance))).roundToInt())
            info.returnValue = false
        }
    }
}

interface HealthAccess {
    var health: Int

    val stack: ItemStack
}
