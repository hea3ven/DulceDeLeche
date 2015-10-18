package com.hea3ven.dulcedeleche.industry.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.item.ItemColored;

import com.hea3ven.dulcedeleche.industry.block.BlockMetalOre;
import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class ItemMetalOre extends ItemColored {

	public ItemMetalOre(Block block) {
		super(block, true);

		List<String> names = Lists.newArrayList();
		for (Metal metal : BlockMetalOre.ORES) {
			names.add(metal.getName());
		}
		setSubtypeNames(names.toArray(new String[names.size()]));
	}
}
