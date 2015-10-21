package com.hea3ven.dulcedeleche.industry.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.hea3ven.dulcedeleche.ModDulceDeLeche;
import com.hea3ven.dulcedeleche.industry.block.tileentity.TileMetalFurnace;
import com.hea3ven.dulcedeleche.industry.client.gui.GuiMetalFurnace;
import com.hea3ven.tools.commonutils.block.base.BlockMachine;

public class BlockMetalFurnace extends BlockMachine {

	public BlockMetalFurnace() {
		super(Material.iron, ModDulceDeLeche.MODID, GuiMetalFurnace.id);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileMetalFurnace();
	}

}
