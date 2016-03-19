package com.hea3ven.dulcedeleche.food

import com.hea3ven.dulcedeleche.food.item.ItemBucketDulceDeLeche
import com.hea3ven.tools.commonutils.mod.ProxyModModule
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Items
import net.minecraft.init.MobEffects
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.potion.PotionEffect

class ProxyModDulceDeLecheFood : ProxyModModule() {

	val dulcedeleche = ItemBucketDulceDeLeche().apply {
		unlocalizedName = "dulcedeleche.dulcedeleche"
		creativeTab = CreativeTabs.tabFood
		containerItem = Items.bucket;
	}

	val vauquita = ItemFood(1, 0.1f, false).apply {
		unlocalizedName = "dulcedeleche.vauquita"
		creativeTab = CreativeTabs.tabFood
		setPotionEffect(PotionEffect(MobEffects.digSpeed, 40, 0), 1.0f)
	}

	override fun registerItems() {
		addItem(dulcedeleche, "dulcedeleche")
		addItem(vauquita, "vauquita")
	}

	override fun registerRecipes() {
		FurnaceRecipes.instance().addSmelting(Items.milk_bucket, ItemStack(dulcedeleche), 0.35f)
		addRecipe(true, ItemStack(vauquita), dulcedeleche, Items.sugar)
	}
}

