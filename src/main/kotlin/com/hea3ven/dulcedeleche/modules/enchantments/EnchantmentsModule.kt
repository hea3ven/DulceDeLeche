package com.hea3ven.dulcedeleche.modules.enchantments

import com.hea3ven.dulcedeleche.ModDulceDeLeche
import com.hea3ven.dulcedeleche.fabric.DulceDeLecheFabricModInitializer
import com.hea3ven.dulcedeleche.modules.enchantments.enchantment.EnchantmentArea
import com.hea3ven.tools.commonutils.mod.ModModule

object EnchantmentsModule : ModModule() {

    override fun onPreInit() {
        if (ModDulceDeLeche.cfg.modules.enchantments.areaEnabled) {
            addEnchantment("area", EnchantmentArea());
        }
    }

}

