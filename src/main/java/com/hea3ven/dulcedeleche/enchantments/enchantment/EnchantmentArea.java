package com.hea3ven.dulcedeleche.enchantments.enchantment;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentArea extends Enchantment {

	public static EnchantmentArea instance = null;

	public static int getAreaModifier(EntityPlayer player) {
		return EnchantmentHelper.getEnchantmentLevel(EnchantmentArea.instance.effectId,
				player.getHeldItem());
	}

	public EnchantmentArea(int enchID) {
		super(enchID, new ResourceLocation("addedstuff:area"), 1, EnumEnchantmentType.DIGGER);
		setName("area");
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	public int getMinEnchantability(int enchantmentLevel) {
		if (enchantmentLevel == 1)
			return 15;
		else
			return 100;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	@SubscribeEvent
	public void onBlockBreakEvent(BlockEvent.BreakEvent event) {
		if (event instanceof AreaBlockBreakEvent)
			return;

		int area = getAreaModifier(event.getPlayer());
		if (area > 0) {
			MovingObjectPosition trace = event.getPlayer().rayTrace(5.0d, 1.0f);
			if (trace.getBlockPos().equals(event.pos)) {
				int exp = areaBreak(event.world, event.getPlayer(), event.pos, trace.sideHit, area);
				event.setExpToDrop(event.getExpToDrop() + exp);
			}

		}
	}

	private int areaBreak(World world, EntityPlayer player, BlockPos pos, EnumFacing face,
			int area) {
		Vec3i dir1 = null;
		Vec3i dir2 = null;
		for (Axis axis : Axis.values()) {
			if (face.getAxis() == axis)
				continue;
			if (dir1 == null)
				dir1 = face.rotateAround(axis).getDirectionVec();
			else
				dir2 = face.rotateAround(axis).getDirectionVec();
		}
		int exp = 0;
		for (int i = area; i >= -area; i--) {
			for (int j = area; j >= -area; j--) {
				BlockPos breakPos = pos.add(dir1.getX() * i, dir1.getY() * i, dir1.getZ() * i).add(
						dir2.getX() * j, dir2.getY() * j, dir2.getZ() * j);
				int breakExp = breakBlock(world, player, breakPos);
				if (breakExp != -1)
					exp += breakExp;
			}
		}
		return exp;
	}

	private int breakBlock(World world, EntityPlayer player, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);

		boolean canHarvest = state.getBlock().canHarvestBlock(world, pos, player);
		if (!canHarvest)
			return -1;

		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null) {
			stack.onBlockDestroyed(world, state.getBlock(), pos, player);
			if (stack.stackSize == 0) {
				player.destroyCurrentEquippedItem();
			}
		}

		BlockEvent.BreakEvent subEvent = new AreaBlockBreakEvent(world, pos, state, player);
		MinecraftForge.EVENT_BUS.post(subEvent);
		if (!subEvent.isCanceled()) {
			boolean removed = removeBlock(world, player, pos, canHarvest);
			if (removed) {
				state.getBlock().harvestBlock(world, player, pos, state, world.getTileEntity(pos));
			}

			return subEvent.getExpToDrop();
		}
		return -1;
	}

	private boolean removeBlock(World world, EntityPlayer player, BlockPos pos,
			boolean canHarvest) {
		IBlockState iblockstate = world.getBlockState(pos);
		iblockstate.getBlock().onBlockHarvested(world, pos, iblockstate, player);
		boolean flag = iblockstate.getBlock().removedByPlayer(world, pos, player, canHarvest);

		if (flag) {
			iblockstate.getBlock().onBlockDestroyedByPlayer(world, pos, iblockstate);
		}

		return flag;
	}

	public static class AreaBlockBreakEvent extends BreakEvent {

		public AreaBlockBreakEvent(World world, BlockPos pos, IBlockState state,
				EntityPlayer player) {
			super(world, pos, state, player);
		}
	}

}
