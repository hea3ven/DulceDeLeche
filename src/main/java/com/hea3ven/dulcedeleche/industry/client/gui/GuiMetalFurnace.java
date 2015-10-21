package com.hea3ven.dulcedeleche.industry.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.hea3ven.dulcedeleche.industry.block.tileentity.TileMetalFurnace;

public class GuiMetalFurnace extends GuiContainer {
	public static final int id = 1;

	private static final ResourceLocation BG_RESOURCE = new ResourceLocation(
			"dulcedeleche:textures/gui/container/metal_furnace.png");

	private TileMetalFurnace te;

	public GuiMetalFurnace(InventoryPlayer playerInv, TileMetalFurnace te) {
		super(te.getContainer(playerInv));
		this.te = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(BG_RESOURCE);

		// Background
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		// Progress Bar
		int i1 = te.getProgress() * 24 / 400;
		this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);

		// Burn Progress
		i1 = te.getFuel() * 13 / te.getFuelCapacity();
		this.drawTexturedModalRect(k + 47, l + 36 + 13 - i1, 176, 13 - i1, 14, i1 + 1);
}

}
