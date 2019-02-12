package com.hea3ven.dulcedeleche.modules.world

import com.hea3ven.dulcedeleche.config.BaseModuleConfig

data class WorldModuleConfig(val nonSolidLeavesEnabled: Boolean, val bedSleepingRestriction: Int) : BaseModuleConfig(){
    constructor() : this(true, 3 * 24000)
}