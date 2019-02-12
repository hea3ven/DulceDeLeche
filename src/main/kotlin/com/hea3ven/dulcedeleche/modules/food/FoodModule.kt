package com.hea3ven.dulcedeleche.modules.food

import com.hea3ven.dulcedeleche.ModDulceDeLeche
import com.hea3ven.dulcedeleche.fabric.DulceDeLecheFabricModInitializer
import com.hea3ven.dulcedeleche.modules.food.item.ItemBucketDulceDeLeche
import com.hea3ven.tools.commonutils.mod.ModModule
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.FoodItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object FoodModule : ModModule() {

    override fun onPreInit() {
        if (ModDulceDeLeche.cfg.modules.food.dulceDeLecheEnabled) {
            addItem("dulcedeleche", ItemBucketDulceDeLeche());
            addItem("vauquita", FoodItem(1, 0.1f, false, Item.Settings().itemGroup(ItemGroup.FOOD)).apply {
                setStatusEffect(StatusEffectInstance(StatusEffects.HASTE, 15 * 20, 0), 1.0f)
            });
        }
    }
}

