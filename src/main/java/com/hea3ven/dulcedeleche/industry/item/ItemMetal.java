package com.hea3ven.dulcedeleche.industry.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class ItemMetal extends Item {

	public static Metal[] NUGGETS = new Metal[] {Metal.COPPER, Metal.TIN, Metal.IRON, Metal.BRONZE};
	public static Metal[] INGOTS = new Metal[] {Metal.COPPER, Metal.TIN, Metal.BRONZE};

	private Metal[] metals;
	private int[] metaByIndex;
	private int[] indexByMeta;

	public ItemMetal(Metal[] metals) {
		setCreativeTab(CreativeTabs.tabMaterials);
		this.metals = metals;
		metaByIndex = new int[Metal.values().length];
		for (int i = 0; i < Metal.values().length; i++) {
			Metal idxMetal = Metal.get(i);
			metaByIndex[i] = -1;
			for (int j = 0; j < metals.length; j++) {
				if (idxMetal == metals[j]) {
					metaByIndex[i] = j;
				}
			}
		}
		indexByMeta = new int[metals.length];
		for (int i = 0; i < metals.length; i++) {
			indexByMeta[i] = metals[i].ordinal();
		}
	}

	public int getMetaForMetal(Metal metal) {
		return metaByIndex[metal.ordinal()];
	}

	public Metal getMetalForMeta(int meta) {
		return metals[meta];
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return getMetalForMeta(stack.getMetadata()).getColor();
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
		for (Metal metal : metals) {
			subItems.add(new ItemStack(itemIn, 1, getMetaForMetal(metal)));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = stack.getMetadata();
		if (meta < metals.length)
			return super.getUnlocalizedName(stack) + "." + getMetalForMeta(meta).getName();
		else
			return super.getUnlocalizedName(stack);
	}

}
