package com.hea3ven.dulcedeleche

import com.hea3ven.dulcedeleche.config.GeneralConfig
import com.hea3ven.dulcedeleche.config.ModulesConfig
import com.hea3ven.dulcedeleche.modules.enchantments.EnchantmentsModuleConfig
import com.hea3ven.dulcedeleche.modules.food.FoodModuleConfig
import com.hea3ven.dulcedeleche.modules.mobs.MobsModuleConfig
import com.hea3ven.dulcedeleche.modules.redstone.RedstoneModuleConfig
import com.hea3ven.dulcedeleche.modules.world.WorldModuleConfig
import com.hea3ven.tools.commonutils.mod.ModComposite
import com.hea3ven.util.readConfig

object ModDulceDeLeche : ModComposite("dulcedeleche") {

    val cfg: GeneralConfig = readConfig("dulcedeleche", GeneralConfig::class.java, ::GeneralConfig,
                                    ModulesConfig::class.java to ::ModulesConfig,
                                    FoodModuleConfig::class.java to ::FoodModuleConfig,
                                    EnchantmentsModuleConfig::class.java to ::EnchantmentsModuleConfig,
                                    RedstoneModuleConfig::class.java to ::RedstoneModuleConfig,
                                    WorldModuleConfig::class.java to ::WorldModuleConfig,
                                    MobsModuleConfig::class.java to ::MobsModuleConfig)

    init {

        addModule("food", "com.hea3ven.dulcedeleche.modules.food.FoodModule.INSTANCE")
        addModule("enchantments", "com.hea3ven.dulcedeleche.modules.enchantments.EnchantmentsModule.INSTANCE")
        addModule("redstone", "com.hea3ven.dulcedeleche.modules.redstone.RedstoneModule.INSTANCE")
        addModule("world", "com.hea3ven.dulcedeleche.modules.world.WorldModule.INSTANCE")
        addModule("mobs", "com.hea3ven.dulcedeleche.modules.mobs.MobsModule.INSTANCE")
    }

}

