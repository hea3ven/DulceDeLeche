package com.hea3ven.tools.commonutils.util;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class WorldHelper {

    @Deprecated
    public static <T extends BlockEntity> T getTile(BlockView world, BlockPos pos) {
        return getBlockEntity(world, pos);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockEntity> T getBlockEntity(BlockView world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity == null)
            return null;
        try {
            return (T) entity;
        } catch (ClassCastException e) {
            return null;
        }
    }
}
