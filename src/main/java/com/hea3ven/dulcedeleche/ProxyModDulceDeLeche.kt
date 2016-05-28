package com.hea3ven.dulcedeleche

import com.hea3ven.tools.commonutils.mod.ProxyModComposite
import com.hea3ven.tools.commonutils.mod.config.FileConfigManagerBuilder
import com.mojang.authlib.GameProfile
import java.util.*

class ProxyModDulceDeLeche : ProxyModComposite(ModDulceDeLeche.MODID) {

	init {
		addModule("enchantments", "com.hea3ven.dulcedeleche.enchantments.ProxyModDulceDeLecheEnchantments")
		addModule("food", "com.hea3ven.dulcedeleche.food.ProxyModDulceDeLecheFood")
		addModule("redstone", "com.hea3ven.dulcedeleche.redstone.ProxyModDulceDeLecheRedstone")
		setFakePlayerProfile(GameProfile(UUID.randomUUID(), "[DulceDeLeche]"))
	}

	override fun registerConfig() {
		addConfigManager(FileConfigManagerBuilder()
				.setFileName("DulceDeLeche.cfg")
				.setDesc("Dulce De Leche Config")
				.addCategory("modules")
				.add(getModule("enchantments").config)
				.add(getModule("food").config)
				.add(getModule("redstone").config)
				.endCategory())
	}
}