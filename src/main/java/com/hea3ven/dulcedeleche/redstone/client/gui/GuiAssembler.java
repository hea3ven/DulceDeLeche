package com.hea3ven.dulcedeleche.redstone.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler;
import com.hea3ven.dulcedeleche.redstone.inventory.ContainerAssembler;

public class GuiAssembler extends GuiContainer {

	private static final ResourceLocation BG_RESOURCE = new ResourceLocation(
			"dulcedeleche:textures/gui/container/assembler.png");

	public GuiAssembler(InventoryPlayer playerInv, TileAssembler te) {
		super(new ContainerAssembler(playerInv, te));
	}

	public static final int id = 0;

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(BG_RESOURCE);
		//	        this.mc.getTextureManager().bindTexture(craftingTableGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

}
