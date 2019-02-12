package com.hea3ven.dulcedeleche.modules.redstone

import com.hea3ven.dulcedeleche.config.BaseModuleConfig

data class RedstoneModuleConfig(val dispenserPlantBehaviorEnabled: Boolean,
        val dispenserBreedingBehaviorEnabled: Boolean) : BaseModuleConfig() {
    constructor() : this(true, true)
}