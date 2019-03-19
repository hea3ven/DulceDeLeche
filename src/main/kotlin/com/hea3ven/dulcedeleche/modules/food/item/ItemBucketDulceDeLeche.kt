package com.hea3ven.dulcedeleche.modules.food.item

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.world.World

class ItemBucketDulceDeLeche(settings: Settings) : Item(settings.stackSize(1).recipeRemainder(Items.BUCKET)) {

    override fun onItemFinishedUsing(stack: ItemStack, world: World, entity: LivingEntity): ItemStack? {
        val newStack = super.onItemFinishedUsing(stack, world, entity)

        if (!world.isClient) {
            entity.addPotionEffect(StatusEffectInstance(StatusEffects.HASTE, 20 * 20, 1))
            entity.addPotionEffect(StatusEffectInstance(StatusEffects.SLOWNESS, 35 * 20, 1))
        }
        return if (newStack.isEmpty) ItemStack(Items.BUCKET) else stack
    }
}