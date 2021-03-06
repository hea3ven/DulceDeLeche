package com.hea3ven.dulcedeleche.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import com.hea3ven.dulcedeleche.modules.enchantments.BlockBreakHandlerKt;
import com.hea3ven.dulcedeleche.modules.enchantments.IServerPlayerInteractionManager;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin
        implements IServerPlayerInteractionManager {
    @Shadow
    public ServerPlayerEntity player;

    @Shadow
    public ServerWorld world;

    public final ServerPlayerEntity getPlayer() {
        return player;
    }

    public final void setPlayer(ServerPlayerEntity var1) {
        this.player = var1;
    }

    public final ServerWorld getWorld() {
        return world;
    }

    public final void setWorld(ServerWorld var1) {
        this.world = var1;
    }

    @Shadow
    @SuppressWarnings("SameReturnValue")
    private boolean destroyBlock(BlockPos var1) {
        return true;
    }

    @Shadow
    public abstract boolean isCreative();

    @Inject(method = {"tryBreakBlock(Lnet/minecraft/util/math/BlockPos;)Z"}, at = {@At(
            target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;destroyBlock(Lnet/minecraft/util/math/BlockPos;)Z",
            value = "INVOKE")})
    public final void onBlockBreak(BlockPos pos, CallbackInfoReturnable info) {
        BlockBreakHandlerKt.onBlockBreak(this, pos);
    }

    @Override
    public boolean doDestroyBlock(BlockPos pos) {
        return destroyBlock(pos);
    }
}