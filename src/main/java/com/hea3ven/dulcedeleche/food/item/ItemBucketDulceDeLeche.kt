package com.hea3ven.dulcedeleche.food.item

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.EnumAction
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.stats.StatList
import net.minecraft.world.World

class ItemBucketDulceDeLeche : Item() {
	init {
		maxStackSize = 1
	}

	override fun onItemRightClick(stack: ItemStack, worldIn: World, player: EntityPlayer): ItemStack? {
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	}

	override fun getItemUseAction(stack: ItemStack?) = EnumAction.EAT

	override fun getMaxItemUseDuration(stack: ItemStack?) = 32

	override fun onItemUseFinish(stack: ItemStack, world: World, player: EntityPlayer): ItemStack? {
		if (!player.capabilities.isCreativeMode)
			--stack.stackSize

		player.foodStats.addStats(2, 0.2f);
		if (!world.isRemote) {
			player.addPotionEffect(PotionEffect(Potion.digSpeed.id, 20 * 20, 1));
			player.addPotionEffect(PotionEffect(Potion.moveSlowdown.id, 35 * 20, 1));
		}

		player.triggerAchievement(StatList.objectUseStats[getIdFromItem(this)]);
		return if (stack.stackSize <= 0 ) ItemStack(Items.bucket) else stack;
	}
}