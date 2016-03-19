package com.hea3ven.dulcedeleche.enchantments

import com.hea3ven.dulcedeleche.enchantments.enchantment.EnchantmentArea
import com.hea3ven.tools.commonutils.mod.ProxyModModule
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent

class ProxyModDulceDeLecheEnchantments : ProxyModModule() {

	val area = EnchantmentArea()

	override fun onPostInitEvent(event: FMLPostInitializationEvent?) {
		super.onPostInitEvent(event)

		MinecraftForge.EVENT_BUS.register(area);
	}

	override fun registerEnchantments() {
		addEnchantment(area, "area")
	}
}