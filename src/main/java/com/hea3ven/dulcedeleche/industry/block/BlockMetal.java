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
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.hea3ven.dulcedeleche.industry.metal.Metal;

public abstract class BlockMetal extends Block {

	private MetalComponent metalComponent;

	public BlockMetal(Material material, Metal[] metals) {
		super(material);
		setCreativeTab(CreativeTabs.tabBlock);
		metalComponent = new MetalComponent(metals);
	}

	public abstract IProperty getMetalProp();

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {getMetalProp()});
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return metalComponent.getMetaForMetal(getMetal(state));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return setMetal(getDefaultState(), metalComponent.getMetalForMeta(meta));
	}

	public MetalComponent getMetalComponent() {
		return metalComponent;
	}

	public Metal getMetal(IBlockState state) {
		return (Metal) state.getValue(getMetalProp());
	}

	public IBlockState setMetal(IBlockState state, Metal metal) {
		return state.withProperty(getMetalProp(), metal);
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (Metal metal : metalComponent.getMetals()) {
			list.add(createStack(metal));
		}
	}

	public ItemStack createStack(Metal metal) {
		return createStack(metal, 1);
	}

	public ItemStack createStack(Metal metal, int size) {
		return new ItemStack(this, size, metalComponent.getMetaForMetal(metal));
	}

	@SideOnly(Side.CLIENT)
	public int getRenderColor(IBlockState state) {
		return getMetal(state).getColor();
	}

	@Override
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
		return getRenderColor(world.getBlockState(pos));
	}

	@Override
	public int getDamageValue(World world, BlockPos pos) {
		return getMetaFromState(world.getBlockState(pos));
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

}
