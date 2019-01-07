package com.hea3ven.dulcedeleche.modules.mobs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;

import com.hea3ven.dulcedeleche.modules.mobs.MobsModule;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {

    protected MobEntityMixin() {
        super(null, null);
    }

    @Inject(method = "method_6121(Lnet/minecraft/entity/Entity;)Z", at = @At("RETURN"))
    public void onPrepareEntityAttributes(Entity entity, CallbackInfoReturnable<Boolean> info) {
        MobsModule.INSTANCE.onMobEntityDoAttack(this, entity);
    }
}
