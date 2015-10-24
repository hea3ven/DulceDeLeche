package com.hea3ven.dulcedeleche.industry.block;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.hea3ven.dulcedeleche.ModDulceDeLeche;
import com.hea3ven.dulcedeleche.industry.block.tileentity.TileMetalFurnace;
import com.hea3ven.dulcedeleche.industry.client.gui.GuiMetalFurnace;
import com.hea3ven.tools.commonutils.block.base.BlockMachine;

public class BlockBrickFurnace extends BlockMachine {

	public BlockBrickFurnace() {
		super(Material.rock, ModDulceDeLeche.MODID, GuiMetalFurnace.id);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileMetalFurnace();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {BlockFurnace.FACING});
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = getFacing(state).getHorizontalIndex() & 0x3;
		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = getDefaultState();
		state = setFacing(state, EnumFacing.getHorizontal(meta));
		return state;
	}

	private EnumFacing getFacing(IBlockState state) {
		return (EnumFacing) state.getValue(BlockFurnace.FACING);
	}

	private IBlockState setFacing(IBlockState state, EnumFacing facing) {
		return state.withProperty(BlockFurnace.FACING, facing);
	}
}
