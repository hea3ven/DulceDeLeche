package com.hea3ven.dulcedeleche.modules.redstone.client.gui

import com.hea3ven.tools.commonutils.container.GenericContainer
import com.mojang.blaze3d.platform.GlStateManager
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.util.Identifier

open class CraftingMachineScreen<T : GenericContainer>(container: T, playerInv: PlayerInventory, name: Component,
        private val bgTex: Identifier) : AbstractContainerScreen<T>(container, playerInv, name) {

    init {
        containerHeight = 215
    }

    override fun drawForeground(int_1: Int, int_2: Int) {
        val name = title.formattedText
        font.draw(name, (containerWidth / 2 - font.getStringWidth(name) / 2).toFloat(),
                               6.0F, 4210752)
        font.draw(playerInventory.displayName.formattedText, 8.0F,
                               (containerHeight - 96 + 2).toFloat(), 4210752)
    }

    override fun drawBackground(var1: Float, var2: Int, var3: Int) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F)
        minecraft!!.textureManager.bindTexture(bgTex)
        val x = left
        val y = (height - containerHeight) / 2
        blit(x, y, 0, 0, containerWidth, containerHeight)

    }

    override fun render(int_1: Int, int_2: Int, float_1: Float) {
        super.render(int_1, int_2, float_1)
        val x = left
        val y = (height - containerHeight) / 2
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
        blit(xPos, yPos, xPos + 16, yPos + 16, -2130706433, -2130706433)
        GlStateManager.colorMask(true, true, true, true)
        GlStateManager.enableDepthTest()
        GlStateManager.enableLighting()
    }
}
