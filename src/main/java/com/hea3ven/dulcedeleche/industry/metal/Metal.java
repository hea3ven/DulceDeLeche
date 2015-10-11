package com.hea3ven.dulcedeleche.industry.metal;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

public enum Metal implements IStringSerializable {
	IRON(-1), COPPER(0), TIN(1), GOLD(-1);

	public static Metal[] ORES = new Metal[] {COPPER, TIN};
	public static final PropertyEnum METAL_ORE = PropertyEnum.create("metal", Metal.class,
			ORES);

	public static Metal get(int index) {
		return values()[index];
	}

	public static Metal getMetalOre(IBlockState state) {
		return (Metal) state.getValue(METAL_ORE);
	}

	public static IBlockState setMetalOre(IBlockState state, Metal metal) {
		return state.withProperty(METAL_ORE, metal);
	}

	public static Metal getOre(int oreIndex) {
		return ORES[oreIndex];
	}

	private int oreIndex;

	private Metal(int oreIndex) {
		this.oreIndex = oreIndex;
	}

	public int getOreIndex() {
		return oreIndex;
	}

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	public String getUnlocalizedName() {
		return getName();
	}

}
