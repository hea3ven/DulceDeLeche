package com.hea3ven.dulcedeleche

import com.hea3ven.tools.commonutils.mod.config.GuiConfigAutomatic
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.fml.client.IModGuiFactory

@Suppress("unused")
class DulceDeLecheModGuiFactory : IModGuiFactory {
	override fun runtimeGuiCategories() = null

	override fun mainConfigGuiClass() = GuiConfigDulceDeLeche::class.java

	override fun getHandlerFor(element: IModGuiFactory.RuntimeOptionCategoryElement?) = null

	override fun initialize(minecraftInstance: Minecraft) {
	}
}

class GuiConfigDulceDeLeche(parentScreen: GuiScreen) :
		GuiConfigAutomatic(parentScreen, ModDulceDeLeche.proxy) {
}
