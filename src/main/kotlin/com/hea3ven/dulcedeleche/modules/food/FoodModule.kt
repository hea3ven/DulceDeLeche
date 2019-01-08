package com.hea3ven.dulcedeleche.modules.food

import com.hea3ven.dulcedeleche.Module
import com.hea3ven.dulcedeleche.modules.food.item.ItemBucketDulceDeLeche
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.FoodItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object FoodModule : Module<FoodModuleConfig>() {

    override fun createDefaultConfig() = FoodModuleConfig(true)

    override fun onInitialize() {
        if (cfg.dulceDeLecheEnabled) {
            logger.debug("Registering the Dulce de Leche item")
            Registry.register(Registry.ITEM, Identifier("dulcedeleche:dulcedeleche"), ItemBucketDulceDeLeche())
            logger.debug("Registering the Vauquita item")
            Registry.register(Registry.ITEM, Identifier("dulcedeleche:vauquita"),
                              FoodItem(1, 0.1f, false, Item.Settings().itemGroup(ItemGroup.FOOD)).apply {
                                  setStatusEffect(StatusEffectInstance(StatusEffects.HASTE, 15 * 20, 0), 1.0f)
                              })
        }
    }
}

