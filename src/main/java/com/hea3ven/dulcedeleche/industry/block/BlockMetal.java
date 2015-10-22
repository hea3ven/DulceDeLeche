package com.hea3ven.dulcedeleche.industry.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.hea3ven.dulcedeleche.industry.metal.Metal;

public abstract class BlockMetal extends Block {

	private Metal[] metals;
	private int[] metaByIndex;
	private int[] indexByMeta;

	public BlockMetal(Material material, Metal[] metals) {
		super(material);
		setCreativeTab(CreativeTabs.tabBlock);
		this.metals = metals;
		metaByIndex = new int[Metal.values().length];
		for (int i = 0; i < Metal.values().length; i++) {
			Metal idxMetal = Metal.get(i);
			metaByIndex[i] = -1;
			for (int j = 0; j < metals.length; j++) {
				if (idxMetal == metals[j]) {
					metaByIndex[i] = j;
				}
			}
		}
		indexByMeta = new int[metals.length];
		for (int i = 0; i < metals.length; i++) {
			indexByMeta[i] = metals[i].ordinal();
		}
	}

	public abstract IProperty getMetalProp();

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {getMetalProp()});
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return metaByIndex[getMetal(state).ordinal()];
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return setMetal(getDefaultState(), Metal.get(indexByMeta[meta]));
	}

	public Metal[] getMetals() {
		return metals;
	}

	public int getMetaForMetal(Metal metal) {
		return metaByIndex[metal.ordinal()];
	}

	public Metal getMetalForMeta(int meta) {
		return metals[meta];
	}

	public Metal getMetal(IBlockState state) {
		return (Metal) state.getValue(getMetalProp());
	}

	public IBlockState setMetal(IBlockState state, Metal metal) {
		return state.withProperty(getMetalProp(), metal);
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (Metal metal : metals) {
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
