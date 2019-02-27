package com.hea3ven.dulcedeleche.modules.redstone.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.world.loot.context.LootContext;
import net.minecraft.world.loot.context.LootContextParameters;
import net.minecraft.world.loot.function.ConditionalLootFunction;
import net.minecraft.world.loot.function.ExplosionDecayLootFunction;

import com.hea3ven.dulcedeleche.modules.redstone.ExplosionManagerKt;
import com.hea3ven.dulcedeleche.modules.world.event.BlockItemPlaceEvent;

@Mixin(SurvivesExplosionLootCondition.class)
public class SurvivesExplosionLootConditionMixin {

    @Inject(method = "method_869(Lnet/minecraft/world/loot/context/LootContext;)Z",
            at = @At(value = "HEAD"), cancellable = true)
    public final void onProcess(LootContext lootContext, CallbackInfoReturnable<Boolean> info) {
        ExplosionManagerKt.onApplySurvivesExplosionLootCondition(lootContext, info);
    }
}
