package com.hea3ven.dulcedeleche.modules.enchantments.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot

class AreaEnchantment : Enchantment(Weight.RARE, EnchantmentTarget.DIGGER, arrayOf(EquipmentSlot.MAINHAND)) {

    override fun getMaximumLevel(): Int {
        return 2
    }

    override fun getMinimumPower(enchantmentLevel: Int): Int {
        return 20
    }


}

