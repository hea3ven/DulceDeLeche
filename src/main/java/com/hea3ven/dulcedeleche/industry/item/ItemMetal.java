package com.hea3ven.dulcedeleche.industry.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.hea3ven.dulcedeleche.industry.block.MetalComponent;
import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class ItemMetal extends Item {

	public static Metal[] NUGGETS = new Metal[] {Metal.COPPER, Metal.TIN, Metal.BRONZE, Metal.IRON,
			Metal.STEEL, Metal.COBALT, Metal.FERCO_STEEL, Metal.TUNGSTEN, Metal.MUSHET_STEEL};
	public static Metal[] INGOTS = new Metal[] {Metal.COPPER, Metal.TIN, Metal.BRONZE, Metal.STEEL,
			Metal.COBALT, Metal.FERCO_STEEL, Metal.TUNGSTEN, Metal.MUSHET_STEEL};

	private MetalComponent metalComponent;

	public ItemMetal(Metal[] metals) {
		setCreativeTab(CreativeTabs.tabMaterials);
		setHasSubtypes(true);
		metalComponent = new MetalComponent(metals);
	}

	public MetalComponent getMetalComponent() {
		return metalComponent;
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
		return createStack(metal, 1);
	}

	public ItemStack createStack(Metal metal, int size) {
		return new ItemStack(this, size, metalComponent.getMetaForMetal(metal));
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
