package com.hea3ven.dulcedeleche.modules.mobs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.SpiderEntity;

import com.hea3ven.dulcedeleche.DulceDeLecheMod;
import com.hea3ven.dulcedeleche.modules.mobs.MobsModule;

@Mixin(CaveSpiderEntity.class)
public abstract class CaveSpiderEntityMixin extends SpiderEntity {

    @SuppressWarnings("ConstantConditions")
    protected CaveSpiderEntityMixin() {
        super(null, null);
    }

    @Inject(method = "attack(Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    public void onPrepareEntityAttributes(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (DulceDeLecheMod.Companion.getCfg()
                .getModules()
                .getMobs()
                .getReplaceCaveSpiderPoison()) {
            if (super.attack(entity)) {
                MobsModule.INSTANCE.onCaveSpiderEntityDoAttack(this, entity);
                info.setReturnValue(true);
            } else {
                info.setReturnValue(false);
            }
        }
    }
}
