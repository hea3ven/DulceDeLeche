package com.hea3ven.dulcedeleche.modules.food

import com.hea3ven.dulcedeleche.DulceDeLecheMod
import com.hea3ven.dulcedeleche.modules.food.item.ItemBucketDulceDeLeche
import com.hea3ven.tools.commonutils.mod.ModModule
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.FoodItemSetting
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup

@Suppress("unused")
object FoodModule : ModModule() {

    override fun onPreInit() {
        if (DulceDeLecheMod.cfg.modules.food.dulceDeLecheEnabled) {
            addItem("dulcedeleche", ItemBucketDulceDeLeche(Item.Settings().itemGroup(ItemGroup.FOOD).food(
                    FoodItemSetting.Builder().hunger(2).saturation(0.2f).build())))
            addItem("vauquita", Item(Item.Settings().itemGroup(ItemGroup.FOOD).food(
                    FoodItemSetting.Builder().hunger(1).saturation(1.0f).alwaysEdible().statusEffect(
                            StatusEffectInstance(StatusEffects.HASTE, 15 * 20, 0), 1.0f).build())))
        }
    }
}

