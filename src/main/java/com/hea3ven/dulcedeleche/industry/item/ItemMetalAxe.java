package com.hea3ven.dulcedeleche.industry.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class ItemMetalAxe extends ItemAxe {

	private Metal metal;

	public ItemMetalAxe(Metal metal) {
		super(metal.getToolMaterial());
		setCreativeTab(CreativeTabs.tabTools);
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
