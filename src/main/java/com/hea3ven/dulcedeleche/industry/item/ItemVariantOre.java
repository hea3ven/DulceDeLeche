package com.hea3ven.dulcedeleche.industry.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class ItemVariantOre extends ItemBlock {

	public ItemVariantOre(Block block) {
		super(block);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "."
				+ Metal.getOre(stack.getMetadata()).getUnlocalizedName();
	}
}
