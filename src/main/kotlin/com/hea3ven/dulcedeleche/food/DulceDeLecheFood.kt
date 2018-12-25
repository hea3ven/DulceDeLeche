package com.hea3ven.dulcedeleche.food

import com.hea3ven.dulcedeleche.food.item.ItemBucketDulceDeLeche
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.FoodItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object DulceDeLecheFood {

    val BUCKET_DULCEDELECHE = ItemBucketDulceDeLeche()

    val VAUQUITA = FoodItem(1, 0.1f, false, Item.Settings().itemGroup(ItemGroup.FOOD)).apply {
        setStatusEffect(StatusEffectInstance(StatusEffects.HASTE, 15 * 20, 0), 1.0f)
    }

    fun onInitialize() {
        Registry.register(Registry.ITEM, Identifier("dulcedeleche:dulcedeleche"), BUCKET_DULCEDELECHE)
        Registry.register(Registry.ITEM, Identifier("dulcedeleche:vauquita"), VAUQUITA)
    }
}

