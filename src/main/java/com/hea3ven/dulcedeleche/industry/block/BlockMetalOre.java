package com.hea3ven.dulcedeleche.industry.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumWorldBlockLayer;

import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class BlockMetalOre extends BlockMetal {

	public static Metal[] ORES = new Metal[] {Metal.COPPER, Metal.TIN, Metal.COBALT,
			Metal.TUNGSTEN};
	public static final PropertyEnum METAL_ORE = PropertyEnum.create("metal", Metal.class, ORES);

	public BlockMetalOre() {
		super(Material.rock, ORES);
	}

	@Override
	public IProperty getMetalProp() {
		return METAL_ORE;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

}
