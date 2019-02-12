package com.hea3ven.dulcedeleche.modules.food

import com.hea3ven.dulcedeleche.config.BaseModuleConfig

data class FoodModuleConfig(val dulceDeLecheEnabled: Boolean) : BaseModuleConfig() {
    constructor() : this(true)
}