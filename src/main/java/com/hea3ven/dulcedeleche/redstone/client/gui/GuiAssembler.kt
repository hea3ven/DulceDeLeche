package com.hea3ven.dulcedeleche.redstone.client.gui

import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

class GuiAssembler(playerInv: InventoryPlayer, val te: TileAssembler) :
		GuiContainer(te.getContainer(playerInv)) {

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F)
		mc.textureManager.bindTexture(BG_RESOURCE)
		val k = (this.width - this.xSize) / 2
		val l = (this.height - this.ySize) / 2
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize)

		// Progress Bar
		val i1 = te.progress * 24 / TileAssembler.MAX_PROGRESS
		this.drawTexturedModalRect(k + 67, l + 34, 176, 0, i1 + 1, 16)

		for (i in 0..8) {
			if (te.inv.getStackInSlot(i).isEmpty) {
				if (!te.inv.invCrafting.getStackInSlot(i).isEmpty) {
					val slot = inventorySlots.inventorySlots[i]
					drawGhostItem(te.inv.invCrafting.getStackInSlot(i)!!, k + slot.xPos, l + slot.yPos)
				}
			}
		}
	}

	private fun drawGhostItem(stack: ItemStack, xPos: Int, yPos: Int) {
		itemRender.renderItemAndEffectIntoGUI(stack, xPos, yPos)
		itemRender.renderItemOverlays(mc.fontRenderer, stack, xPos, yPos)

		GlStateManager.disableLighting()
		GlStateManager.disableDepth()
		GlStateManager.colorMask(true, true, true, false)
		drawGradientRect(xPos, yPos, xPos + 16, yPos + 16, -2130706433, -2130706433)
		GlStateManager.colorMask(true, true, true, true)
		GlStateManager.enableDepth()
		GlStateManager.enableLighting()
	}

	companion object {
		val BG_RESOURCE = ResourceLocation("dulcedeleche:textures/gui/container/assembler.png")
	}
}
