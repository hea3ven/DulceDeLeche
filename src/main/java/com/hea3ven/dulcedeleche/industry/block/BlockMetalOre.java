package com.hea3ven.dulcedeleche.industry.block;

import java.util.List;

import net.minecraft.block.BlockOre;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class BlockMetalOre extends BlockOre {

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {Metal.METAL_ORE});
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return Metal.getMetalOre(state).getOreIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return Metal.setMetalOre(getDefaultState(), Metal.getOre(meta));
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (Metal metal : Metal.ORES) {
			list.add(new ItemStack(this, 1, metal.getOreIndex()));
		}
	}

	@SideOnly(Side.CLIENT)
	public int getRenderColor(IBlockState state) {
		return Metal.getMetalOre(state).getColor();
	}

	@Override
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
		return getRenderColor(world.getBlockState(pos));
	}
}
