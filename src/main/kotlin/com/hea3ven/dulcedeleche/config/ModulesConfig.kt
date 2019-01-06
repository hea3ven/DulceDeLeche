package com.hea3ven.dulcedeleche.config

import com.hea3ven.dulcedeleche.modules.enchantments.EnchantmentsModuleConfig
import com.hea3ven.dulcedeleche.modules.food.FoodModuleConfig
import com.hea3ven.dulcedeleche.modules.mobs.MobsModuleConfig
import com.hea3ven.dulcedeleche.modules.redstone.RedstoneModuleConfig
import com.hea3ven.dulcedeleche.modules.world.WorldModuleConfig

data class ModulesConfig(val food: FoodModuleConfig, val enchantments: EnchantmentsModuleConfig,
        val redstone: RedstoneModuleConfig, val world: WorldModuleConfig, val mobs: MobsModuleConfig)