package com.hea3ven.dulcedeleche.modules.enchantments

import com.hea3ven.dulcedeleche.Module
import com.hea3ven.dulcedeleche.modules.enchantments.enchantment.EnchantmentArea
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object EnchantmentsModule : Module<EnchantmentsModuleConfig>() {
    val AREA = EnchantmentArea()

    override fun createDefaultConfig() = EnchantmentsModuleConfig(true)

    override fun onInitialize() {
        if (cfg.areaEnabled) {
            logger.debug("Registering the Area enchantment")
            Registry.register(Registry.ENCHANTMENT, Identifier("dulcedeleche:area"), AREA)
        }
    }

}

