package com.hea3ven.dulcedeleche.industry.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class ItemMetalArmor extends ItemArmor {

	private Metal metal;

	public ItemMetalArmor(Metal metal, int armorType) {
		super(metal.getArmorMaterial(), -1, armorType);
		setCreativeTab(CreativeTabs.tabCombat);
		setHasSubtypes(true);

		this.metal = metal;
	}

	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		if (renderPass == 0)
			return metal.getColor();
		else
			return super.getColorFromItemStack(stack, renderPass);
	}

	public Metal getMetal() {
		return metal;
	}

}
