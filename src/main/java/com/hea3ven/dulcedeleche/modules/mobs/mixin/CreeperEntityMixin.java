package com.hea3ven.dulcedeleche.modules.mobs.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.SpawnType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.IWorld;

import com.hea3ven.dulcedeleche.modules.mobs.MobsModule;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin extends HostileEntity {
    protected CreeperEntityMixin() {
        super(null, null);
    }

    @Override
    public boolean canSpawn(IWorld world, SpawnType spawnType) {
        if (MobsModule.INSTANCE.cfg.getBlockCreeperSpawnInSurface()) {
            return MobsModule.INSTANCE.onCreeperEntityCanSpawn(this, world, spawnType);
        } else {
            return super.canSpawn(world, spawnType);
        }
    }
}
