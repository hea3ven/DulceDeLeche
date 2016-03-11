package com.hea3ven.dulcedeleche.food

import com.hea3ven.dulcedeleche.food.item.ItemBucketDulceDeLeche
import com.hea3ven.tools.commonutils.mod.ProxyModBase
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.*
import net.minecraft.item.crafting.FurnaceRecipes
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.stats.StatList
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.GameRegistry

class ProxyModDulceDeLecheFood(modId: String) : ProxyModBase(modId) {

	val dulcedeleche = ItemBucketDulceDeLeche().apply {
		unlocalizedName = "dulcedeleche.dulcedeleche"
		creativeTab = CreativeTabs.tabFood
	}

	val vauquita = ItemFood(1, 0.1f, false).apply {
		unlocalizedName = "dulcedeleche.vauquita"
		creativeTab = CreativeTabs.tabFood
		setPotionEffect(Potion.digSpeed.id, 40, 0, 1.0f)
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

