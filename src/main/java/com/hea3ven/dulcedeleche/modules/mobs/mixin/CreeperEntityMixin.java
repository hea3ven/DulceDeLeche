package com.hea3ven.dulcedeleche.modules.mobs.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.SpawnType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.IWorld;

import com.hea3ven.dulcedeleche.ModDulceDeLeche;
import com.hea3ven.dulcedeleche.modules.mobs.MobsModule;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin extends HostileEntity {

    @SuppressWarnings("ConstantConditions")
    protected CreeperEntityMixin() {
        super(null, null);
    }

    @Override
    public boolean canSpawn(IWorld world, SpawnType spawnType) {
        if (ModDulceDeLeche.INSTANCE.getCfg()
                .getModules()
                .getMobs()
                .getBlockCreeperSpawnInSurface()) {
            return MobsModule.INSTANCE.onCreeperEntityCanSpawn(this, world, spawnType);
        } else {
            return super.canSpawn(world, spawnType);
        }
    }
}
