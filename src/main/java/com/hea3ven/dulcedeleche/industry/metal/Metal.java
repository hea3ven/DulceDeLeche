package com.hea3ven.dulcedeleche.industry.metal;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

public enum Metal implements IStringSerializable {
	IRON(0),
	COPPER((200 << 16) + (75 << 8) + 40),
	TIN((185 << 16) + (210 << 8) + 230),
	GOLD(0);

	public static Metal[] ORES = new Metal[] {COPPER, TIN};
	public static final PropertyEnum METAL_ORE = PropertyEnum.create("metal", Metal.class, ORES);

	public static Metal[] INGOTS = new Metal[] {COPPER, TIN};

	static {
		int index = 0;
		for (Metal metal : ORES) {
			metal.oreIndex = index++;
		}
		index = 0;
		for (Metal metal : INGOTS) {
			metal.ingotIndex = index++;
		}
	}

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

	public static Metal getIngot(int ingotIndex) {
		return INGOTS[ingotIndex];
	}

	private int oreIndex;
	private int ingotIndex;
	private int color;

	private Metal(int color) {
		this.color = color;
	}

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	public String getUnlocalizedName() {
		return getName();
	}

	public int getOreIndex() {
		return oreIndex;
	}

	public int getIngotIndex() {
		return ingotIndex;
	}

	public int getColor() {
		return color;
	}

}
