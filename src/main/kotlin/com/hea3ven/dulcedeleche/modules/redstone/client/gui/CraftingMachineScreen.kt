package com.hea3ven.dulcedeleche.modules.redstone.client.gui

import com.hea3ven.tools.commonutils.container.GenericContainer
import com.mojang.blaze3d.platform.GlStateManager
import net.minecraft.client.gui.ContainerScreen
import net.minecraft.text.TextComponent
import net.minecraft.util.Identifier

open class CraftingMachineScreen(container: GenericContainer, name: TextComponent, private val bgTex: Identifier) :
        ContainerScreen<GenericContainer>(container, container.playerInv, name) {

    init {
        height = 215
    }

    override fun drawForeground(int_1: Int, int_2: Int) {
        val name = this.name.formattedText
        this.fontRenderer.draw(name, (this.width / 2 - this.fontRenderer.getStringWidth(name) / 2).toFloat(),
                               6.0F, 4210752)
        this.fontRenderer.draw(this.playerInventory.displayName.formattedText, 8.0F,
                               (this.height - 96 + 2).toFloat(), 4210752)
    }

    override fun drawBackground(var1: Float, var2: Int, var3: Int) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F)
        client.textureManager.bindTexture(bgTex)
        val x = left
        val y = (screenHeight - height) / 2
        this.drawTexturedRect(x, y, 0, 0, width, height)

    }

    override fun draw(int_1: Int, int_2: Int, float_1: Float) {
        super.draw(int_1, int_2, float_1)
        val x = left
        val y = (screenHeight - height) / 2
        for (i in 0..8) {
            val slot = container.getSlot(i)
            if (!slot.stack.isEmpty) {
                drawGhostItem(x + slot.xPosition, y + slot.yPosition)
            }
        }
    }

    private fun drawGhostItem(xPos: Int, yPos: Int) {
        GlStateManager.disableLighting()
        GlStateManager.disableDepthTest()
        GlStateManager.colorMask(true, true, true, false)
        drawGradientRect(xPos, yPos, xPos + 16, yPos + 16, -2130706433, -2130706433)
        GlStateManager.colorMask(true, true, true, true)
        GlStateManager.enableDepthTest()
        GlStateManager.enableLighting()
    }
}