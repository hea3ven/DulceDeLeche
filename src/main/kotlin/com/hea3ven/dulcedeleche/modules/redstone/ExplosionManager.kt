package com.hea3ven.dulcedeleche.modules.redstone

import net.minecraft.world.loot.context.LootContext
import net.minecraft.world.loot.context.LootContextParameters
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

fun onApplySurvivesExplosionLootCondition(lootContext: LootContext, info: CallbackInfoReturnable<Boolean>) {
    val pos = lootContext.get(LootContextParameters.POSITION);
    val state = lootContext.world.getBlockState(pos)

    val chance = 0.007396f * state.block.blastResistance * state.block.blastResistance + 0.63333f

    info.returnValue = lootContext.random.nextFloat() <= chance;
}