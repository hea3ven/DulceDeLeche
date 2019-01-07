package com.hea3ven.dulcedeleche.modules.mobs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;

import com.hea3ven.dulcedeleche.modules.mobs.MobsModule;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin extends HostileEntity {
    protected ZombieEntityMixin() {
        super(null, null);
    }

    @Inject(method = "method_7205(F)V", at = @At("RETURN"))
    public void onPrepareEntityAttributes(CallbackInfo info) {
        MobsModule.INSTANCE.onZombiePrepareEntityAttributes(this);
    }
}
