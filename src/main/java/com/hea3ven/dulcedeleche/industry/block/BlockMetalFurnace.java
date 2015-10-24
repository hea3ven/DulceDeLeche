package com.hea3ven.dulcedeleche.industry.block;

import java.util.List;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.hea3ven.dulcedeleche.ModDulceDeLeche;
import com.hea3ven.dulcedeleche.industry.block.tileentity.TileMetalFurnace;
import com.hea3ven.dulcedeleche.industry.client.gui.GuiMetalFurnace;
import com.hea3ven.dulcedeleche.industry.metal.Metal;
import com.hea3ven.tools.commonutils.block.base.BlockMachine;

public class BlockMetalFurnace extends BlockMachine {

	public static final Metal[] METALS = new Metal[] {Metal.BRONZE, Metal.STEEL, Metal.COBALT};
	public static final PropertyEnum METAL = PropertyEnum.create("metal", Metal.class, METALS);

	private MetalComponent metalComponent;

	public BlockMetalFurnace() {
		super(Material.iron, ModDulceDeLeche.MODID, GuiMetalFurnace.id);
		setCreativeTab(CreativeTabs.tabDecorations);

		metalComponent = new MetalComponent(METALS);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileMetalFurnace();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {BlockFurnace.FACING, METAL});
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = getFacing(state).getHorizontalIndex() & 0x3;
		meta |= (metalComponent.getMetaForMetal(getMetal(state)) & 0x3) << 2;
		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = getDefaultState();
		state = setFacing(state, EnumFacing.getHorizontal(meta));
		state = setMetal(state, metalComponent.getMetalForMeta((meta >> 2) & 0x3));
		return state;
	}

	private EnumFacing getFacing(IBlockState state) {
		return (EnumFacing) state.getValue(BlockFurnace.FACING);
	}

	private IBlockState setFacing(IBlockState state, EnumFacing facing) {
		return state.withProperty(BlockFurnace.FACING, facing);
	}

	private Metal getMetal(IBlockState state) {
		return (Metal) state.getValue(METAL);
	}

	private IBlockState setMetal(IBlockState state, Metal metal) {
		return state.withProperty(METAL, metal);
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (Metal metal : metalComponent.getMetals()) {
			list.add(createStack(metal));
		}
	}

	public ItemStack createStack(Metal metal) {
		return new ItemStack(this, 1, metalComponent.getMetaForMetal(metal) << 2);
	}

	@Override
	public int getRenderColor(IBlockState state) {
		return getMetal(state).getColor();
	}

	@Override
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
		return getRenderColor(world.getBlockState(pos));
	}

	public int getTier(IBlockState state) {
		switch (getMetal(state)) {
			case BRONZE:
				return 1;
			case STEEL:
				return 2;
			case COBALT:
				return 3;
			default:
				return -1;
		}
	}
}
