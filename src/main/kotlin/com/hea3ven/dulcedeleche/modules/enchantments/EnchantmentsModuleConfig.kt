package com.hea3ven.dulcedeleche.modules.enchantments

import com.hea3ven.dulcedeleche.config.BaseModuleConfig

data class EnchantmentsModuleConfig(val areaEnabled: Boolean) : BaseModuleConfig() {
    constructor() : this(true)
}