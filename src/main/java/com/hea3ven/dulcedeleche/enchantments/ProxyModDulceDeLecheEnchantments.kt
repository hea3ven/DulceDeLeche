package com.hea3ven.dulcedeleche.enchantments

import com.hea3ven.dulcedeleche.enchantments.enchantment.EnchantmentArea
import com.hea3ven.tools.commonutils.mod.ProxyModModule
import com.hea3ven.tools.commonutils.mod.config.FileConfigManagerBuilder.CategoryConfigManagerBuilder
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Property
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent

@Suppress("unused")
class ProxyModDulceDeLecheEnchantments : ProxyModModule() {

	private var enableArea = true

	val area = EnchantmentArea()

	override fun onPostInitEvent(event: FMLPostInitializationEvent?) {
		super.onPostInitEvent(event)

		if (enableArea)
			MinecraftForge.EVENT_BUS.register(area)
	}

	override fun getConfig() = CategoryConfigManagerBuilder("enchantment")
			.addValue("enableArea", "true", Property.Type.BOOLEAN, "Enable the Area enchantment",
					{ enableArea = it.boolean }, true, true)

	override fun registerEnchantments() {
		if (enableArea)
			addEnchantment(area, "area")
	}
}