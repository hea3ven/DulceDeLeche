package com.hea3ven.dulcedeleche.industry.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class ItemMetalIngot extends Item {

	public ItemMetalIngot() {
		setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return Metal.getIngot(stack.getMetadata()).getColor();
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
		for (Metal metal : Metal.INGOTS) {
			subItems.add(new ItemStack(itemIn, 1, metal.getIngotIndex()));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getMetadata();
		return super.getUnlocalizedName(stack) + "." + Metal.INGOTS[i];
	}

}
