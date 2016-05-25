package com.hea3ven.dulcedeleche

import com.hea3ven.tools.commonutils.mod.ProxyModComposite
import com.mojang.authlib.GameProfile
import java.util.*

class ProxyModDulceDeLeche : ProxyModComposite(ModDulceDeLeche.MODID) {

	init {
		addModule("enchantments", "com.hea3ven.dulcedeleche.enchantments.ProxyModDulceDeLecheEnchantments")
		addModule("food", "com.hea3ven.dulcedeleche.food.ProxyModDulceDeLecheFood")
		addModule("redstone", "com.hea3ven.dulcedeleche.redstone.ProxyModDulceDeLecheRedstone")
		setFakePlayerProfile(GameProfile(UUID.randomUUID(), "[DulceDeLeche]"))
	}
}