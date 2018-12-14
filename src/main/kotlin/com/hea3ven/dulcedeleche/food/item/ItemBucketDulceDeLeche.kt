package com.hea3ven.dulcedeleche.food.item

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.*
import net.minecraft.world.World

class ItemBucketDulceDeLeche :
        FoodItem(2, 0.2f, false, Item.Settings().stackSize(1).recipeRemainder(Items.BUCKET).itemGroup(ItemGroup.FOOD)) {

    override fun onConsumed(stack: ItemStack, world: World, entity: PlayerEntity) {
        entity.addPotionEffect(StatusEffectInstance(StatusEffects.HASTE, 20 * 20, 1))
        entity.addPotionEffect(StatusEffectInstance(StatusEffects.SLOWNESS, 35 * 20, 1))
    }

    override fun onItemFinishedUsing(stack: ItemStack, world: World, entity: LivingEntity): ItemStack? {
        val newStack = super.onItemFinishedUsing(stack, world, entity)
        return if (newStack.isEmpty) ItemStack(Items.BUCKET) else stack
    }
}