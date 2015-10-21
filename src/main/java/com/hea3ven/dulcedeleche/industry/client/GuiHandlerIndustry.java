package com.hea3ven.dulcedeleche.industry.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.IGuiHandler;

import com.hea3ven.dulcedeleche.industry.block.tileentity.TileMetalFurnace;
import com.hea3ven.dulcedeleche.industry.client.gui.GuiMetalFurnace;
import com.hea3ven.tools.commonutils.util.WorldHelper;

public class GuiHandlerIndustry implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y,
			int z) {
		if (ID == GuiMetalFurnace.id)
			return WorldHelper
					.<TileMetalFurnace> getTile(world, new BlockPos(x, y, z))
					.getContainer(player.inventory);
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y,
			int z) {
		if (ID == GuiMetalFurnace.id)
			return new GuiMetalFurnace(player.inventory,
					WorldHelper.<TileMetalFurnace> getTile(world, new BlockPos(x, y, z)));
		return null;
	}

}
