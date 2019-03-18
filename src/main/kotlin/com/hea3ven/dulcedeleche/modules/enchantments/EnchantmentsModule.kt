package com.hea3ven.dulcedeleche.modules.enchantments

import com.hea3ven.dulcedeleche.DulceDeLecheMod
import com.hea3ven.dulcedeleche.modules.enchantments.enchantment.AreaEnchantment
import com.hea3ven.tools.commonutils.mod.ModModule

@Suppress("unused")
object EnchantmentsModule : ModModule() {

    override fun onPreInit() {
        if (DulceDeLecheMod.cfg.modules.enchantments.areaEnabled) {
            addEnchantment("area", AreaEnchantment())
        }
    }

}

