package com.hea3ven.dulcedeleche.modules.world.mixin;

import net.fabricmc.fabric.util.HandlerArray;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.ActionResult;

import com.hea3ven.dulcedeleche.modules.world.event.BlockItemPlaceEvent;

@Mixin(BlockItem.class)
public class BlockItemMixin extends Item {
    @SuppressWarnings("ConstantConditions")
    public BlockItemMixin() {
        super(null);
    }

    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;",
            at = @At(value = "RETURN", ordinal = 4))
    public final void onPlace(ItemPlacementContext itemPlacementContext,
            CallbackInfoReturnable<ActionResult> info) {

        for (BlockItemPlaceEvent handler : ((HandlerArray<BlockItemPlaceEvent>) BlockItemPlaceEvent.Companion
                .getPOST_SUCCESS()).getBackingArray()) {
            handler.place(itemPlacementContext);
        }
    }
}
