package com.hea3ven.dulcedeleche.industry.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class ItemMetalSword extends ItemSword {

	private Metal metal;

	public ItemMetalSword(Metal metal) {
		super(metal.getToolMaterial());
		setCreativeTab(CreativeTabs.tabCombat);
		setHasSubtypes(true);

		this.metal = metal;
	}

	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		if (renderPass == 1)
			return metal.getColor();
		else
			return super.getColorFromItemStack(stack, renderPass);
	}

	public Metal getMetal() {
		return metal;
	}

}
