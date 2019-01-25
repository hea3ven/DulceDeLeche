package com.hea3ven.unstainer.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;

import com.hea3ven.unstainer.client.gui.UnstainerScreen;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @ModifyArg(method = "init()V",
            at = @At(value = "INVOKE", target = "openScreen(Lnet/minecraft/client/gui/Screen;)V",
                    ordinal = 1), index = 0)
    private Screen modifyGuiOnOpenGui(Screen mainMenuGui) {
        return new UnstainerScreen();
    }
}
