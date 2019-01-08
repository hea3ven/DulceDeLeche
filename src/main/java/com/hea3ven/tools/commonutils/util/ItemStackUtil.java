package com.hea3ven.tools.commonutils.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemStackUtil {
    private static final Random RANDOM = new Random();

    @Deprecated
    public static void dropFromBlock(World world, BlockPos pos, ItemStack stack) {
        Block.dropStack(world, pos, stack);
    }

    @Deprecated
    public static boolean areItemsCompletelyEqual(ItemStack stackA, ItemStack stackB) {
        return areStacksCombinable(stackA, stackB);
    }

    public static boolean areStacksCombinable(ItemStack stackA, ItemStack stackB) {
        return ItemStack.areEqualIgnoreTags(stackA, stackB) && ItemStack.areTagsEqual(stackA,
                stackB);
    }

    public static ActionResult useItem(PlayerEntity player, ItemStack stack, BlockPos pos,
            Direction facing) {
        player.setEquippedStack(EquipmentSlot.HAND_MAIN, stack);
        ActionResult result = stack.useOnBlock(new ItemUsageContext(player, stack,
                new BlockHitResult(new Vec3d(0.5D, 0.5D, 0.5D), facing, pos, true)));
        player.setEquippedStack(EquipmentSlot.HAND_MAIN, ItemStack.EMPTY);
        return result;
    }
}
