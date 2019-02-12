package com.hea3ven.dulcedeleche.modules.mobs

import com.hea3ven.dulcedeleche.config.BaseModuleConfig

data class MobsModuleConfig(val blockCreeperSpawnInSurface: Boolean, val zombieKnockbackAttributeMultiplier: Double,
        val spidersApplySlowness: Boolean, val replaceCaveSpiderPoison: Boolean) : BaseModuleConfig() {
    constructor() : this(true, 14.0, true, true)
}