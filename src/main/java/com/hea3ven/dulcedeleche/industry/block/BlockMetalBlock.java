package com.hea3ven.dulcedeleche.industry.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;

import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class BlockMetalBlock extends BlockMetal {

	public static Metal[] BLOCKS = new Metal[] {Metal.COPPER, Metal.TIN, Metal.BRONZE, Metal.STEEL,
			Metal.COBALT, Metal.FERCO_STEEL, Metal.TUNGSTEN, Metal.MUSHET_STEEL};
	public static final PropertyEnum METAL_BLOCK = PropertyEnum.create("metal", Metal.class,
			BLOCKS);

	public BlockMetalBlock() {
		super(Material.iron, BLOCKS);
	}

	@Override
	public IProperty getMetalProp() {
		return METAL_BLOCK;
	}

}
