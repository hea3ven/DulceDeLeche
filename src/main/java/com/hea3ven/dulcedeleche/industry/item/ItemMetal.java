package com.hea3ven.dulcedeleche.industry.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.hea3ven.dulcedeleche.industry.block.MetalComponent;
import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class ItemMetal extends Item {

	public static Metal[] NUGGETS = new Metal[] {Metal.COPPER, Metal.TIN, Metal.IRON, Metal.BRONZE};
	public static Metal[] INGOTS = new Metal[] {Metal.COPPER, Metal.TIN, Metal.BRONZE};

	private MetalComponent metalComponent;

	public ItemMetal(Metal[] metals) {
		setCreativeTab(CreativeTabs.tabMaterials);
		metalComponent = new MetalComponent(metals);
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return metalComponent.getMetalForMeta(stack.getMetadata()).getColor();
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for (Metal metal : metalComponent.getMetals()) {
			subItems.add(createStack(metal));
		}
	}

	public ItemStack createStack(Metal metal) {
		return new ItemStack(this, 1, metalComponent.getMetaForMetal(metal));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getMetadata();
		if (meta < metalComponent.getMetals().length)
			return super.getUnlocalizedName(stack) + "."
					+ metalComponent.getMetalForMeta(meta).getName();
		else
			return super.getUnlocalizedName(stack);
	}

}
