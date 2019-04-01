package com.hea3ven.dulcedeleche.modules.redstone.client.gui

import com.hea3ven.dulcedeleche.modules.redstone.container.AssemblerContainer
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.TextComponent
import net.minecraft.util.Identifier
import kotlin.math.roundToInt

class AssemblerScreen(private val assemblerContainer: AssemblerContainer, playerInv: PlayerInventory,
        name: TextComponent) : CraftingMachineScreen<AssemblerContainer>(assemblerContainer, playerInv, name,
                                                                         Identifier(
                                                                                 "dulcedeleche:textures/gui/container/assembler.png")) {

    override fun drawBackground(var1: Float, var2: Int, var3: Int) {
        super.drawBackground(var1, var2, var3)
        val progress = assemblerContainer.progress
        this.blit(left + 89, top + 34, 176, 0, (24 * progress).roundToInt(), 16)
    }
}