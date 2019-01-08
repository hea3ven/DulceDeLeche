package com.hea3ven.tools.commonutils.block;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sortme.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.hea3ven.tools.commonutils.block.entity.MachineBlockEntity;
import com.hea3ven.tools.commonutils.util.WorldHelper;

public abstract class MachineBlock extends BlockWithEntity {

    private Identifier guiId;

    protected MachineBlock(Settings settings, Identifier guiId) {
        super(settings);

        this.guiId = guiId;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity player,
            ItemStack stack) {
        if (stack.hasDisplayName()) {
            MachineBlockEntity entity = WorldHelper.getBlockEntity(world, pos);
            if (entity != null)
                entity.setCustomName(stack.getDisplayName());
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player,
            Hand hand, BlockHitResult hitResult) {
        if (!world.isClient) {
            ContainerProviderRegistry.INSTANCE.openContainer(guiId, player,
                    writer -> writer.writeBlockPos(pos));
        }
        return true;
    }

    @Override
    public void onBlockRemoved(BlockState prevState, World world_1, BlockPos blockPos_1,
            BlockState newState, boolean boolean_1) {
        if (prevState.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
            if (blockEntity_1 instanceof Inventory) {
                ItemScatterer.spawn(world_1, blockPos_1, (Inventory) blockEntity_1);
                world_1.updateHorizontalAdjacent(blockPos_1, this);
            }

            super.onBlockRemoved(prevState, world_1, blockPos_1, newState, boolean_1);
        }
    }
}
