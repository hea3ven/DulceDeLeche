package com.hea3ven.dulcedeleche.enchantments

import com.hea3ven.dulcedeleche.enchantments.enchantment.EnchantmentArea
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object DulceDeLecheEnchantments {
    val AREA = EnchantmentArea()

    fun onInitialize() {
        Registry.register(Registry.ENCHANTMENT, Identifier("dulcedeleche:area"), AREA)
    }
}