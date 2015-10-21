package com.hea3ven.dulcedeleche.redstone.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.IGuiHandler;

import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler;
import com.hea3ven.dulcedeleche.redstone.client.gui.GuiAssembler;
import com.hea3ven.dulcedeleche.redstone.inventory.ContainerAssembler;
import com.hea3ven.tools.commonutils.util.WorldHelper;

public class GuiHandlerRedstone implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y,
			int z) {
		if (ID == GuiAssembler.id)
			return new ContainerAssembler(player.inventory,
					WorldHelper.<TileAssembler> getTile(world, new BlockPos(x, y, z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y,
			int z) {
		if (ID == GuiAssembler.id)
			return new GuiAssembler(player.inventory,
					WorldHelper.<TileAssembler> getTile(world, new BlockPos(x, y, z)));
		return null;
	}

}
