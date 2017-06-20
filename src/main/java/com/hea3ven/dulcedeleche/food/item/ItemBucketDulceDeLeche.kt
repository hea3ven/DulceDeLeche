package com.hea3ven.dulcedeleche.food.item

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.init.MobEffects
import net.minecraft.item.EnumAction
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.stats.StatList
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World

class ItemBucketDulceDeLeche : Item() {
	init {
		maxStackSize = 1
	}

	override fun onItemRightClick(worldIn: World, player: EntityPlayer, hand: EnumHand)
			: ActionResult<ItemStack> {
		player.activeHand = hand
		return ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand))
	}

	override fun getItemUseAction(stack: ItemStack?) = EnumAction.EAT

	override fun getMaxItemUseDuration(stack: ItemStack?) = 32

	override fun onItemUseFinish(stack: ItemStack, world: World, entity: EntityLivingBase): ItemStack? {
		if (entity is EntityPlayer) {
			if (!entity.capabilities.isCreativeMode)
				stack.shrink(1)

			entity.foodStats.addStats(2, 0.2f)
			if (!world.isRemote) {
				entity.addPotionEffect(PotionEffect(MobEffects.HASTE, 20 * 20, 1))
				entity.addPotionEffect(PotionEffect(MobEffects.SLOWNESS, 35 * 20, 1))
			}

			entity.addStat(StatList.getObjectUseStats(this))
		}
		return if (stack.isEmpty) ItemStack(Items.BUCKET) else stack
	}
}