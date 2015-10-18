package com.hea3ven.dulcedeleche.industry.block;

import java.util.List;

import net.minecraft.block.BlockOre;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
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

	public static Metal[] ORES = new Metal[] {Metal.COPPER, Metal.TIN};
	public static final PropertyEnum METAL_ORE = PropertyEnum.create("metal", Metal.class, ORES);

	// TODO: fix this later
	private int[] metaByIndex = new int[] {-1, 0, 1, -1};
	private int[] indexByMeta = new int[] {1, 2};

	public static Metal getMetal(IBlockState state) {
		return (Metal) state.getValue(METAL_ORE);
	}

	public static IBlockState setMetal(IBlockState state, Metal metal) {
		return state.withProperty(METAL_ORE, metal);
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {METAL_ORE});
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return metaByIndex[getMetal(state).ordinal()];
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return setMetal(getDefaultState(), Metal.get(indexByMeta[meta]));
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (Metal metal : ORES) {
			list.add(new ItemStack(this, 1, metaByIndex[metal.ordinal()]));
		}
	}

	@SideOnly(Side.CLIENT)
	public int getRenderColor(IBlockState state) {
		return getMetal(state).getColor();
	}

	@Override
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
		return getRenderColor(world.getBlockState(pos));
	}
}
