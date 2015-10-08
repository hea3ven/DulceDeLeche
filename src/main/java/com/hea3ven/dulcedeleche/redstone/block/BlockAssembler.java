package com.hea3ven.dulcedeleche.redstone.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.hea3ven.dulcedeleche.ModDulceDeLeche;
import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler;
import com.hea3ven.dulcedeleche.redstone.client.gui.GuiAssembler;
import com.hea3ven.tools.commonutils.block.base.BlockMachine;

public class BlockAssembler extends BlockMachine {

	public BlockAssembler() {
		super(Material.rock, ModDulceDeLeche.MODID, GuiAssembler.id);
		setDefaultState(blockState.getBaseState());
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileAssembler();
	}

}
