package com.hea3ven.dulcedeleche.modules.redstone.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

import com.hea3ven.dulcedeleche.modules.redstone.ExplosionManagerKt;
import com.hea3ven.dulcedeleche.modules.redstone.HealthAccess;

@Mixin(ItemEntity.class)
public class ItemEntityMixin implements HealthAccess {

    @Shadow
    private int health;

    @Shadow
    public ItemStack getStack() {
        return ItemStack.EMPTY;
    }

    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD,
                    target = "Lnet/minecraft/entity/ItemEntity;health", args = {"log=true"}),
            cancellable = true)
    public final void onDamageSetHealth(DamageSource damageSource, float damage,
            CallbackInfoReturnable<Boolean> info) {
        ExplosionManagerKt.onDamageSetHealthItemEntity(this, damageSource, damage, info);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }
}
