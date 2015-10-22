package com.hea3ven.dulcedeleche.industry.metal;

import net.minecraft.util.IStringSerializable;

public enum Metal implements IStringSerializable {
	IRON((216 << 16) + (216 << 8) + 216, 0, 64),
	COPPER((210 << 16) + (80 << 8) + 50, 32, 64),
	TIN((185 << 16) + (210 << 8) + 230, 64, 128),
	GOLD(0, 0, 32),
	BRONZE((225 << 16) + (170 << 8) + 80, 0, 0);

	public static Metal get(int index) {
		return values()[index];
	}

	private int color;
	private int worldMinLevel;
	private int worldMaxLevel;

	private Metal(int color, int worldMinLevel, int worldMaxLevel) {
		this.color = color;
		this.worldMinLevel = worldMinLevel;
		this.worldMaxLevel = worldMaxLevel;
	}

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	public String getUnlocalizedName() {
		return getName();
	}

	public int getColor() {
		return color;
	}

	public int getWorldMaxLevel() {
		return worldMaxLevel;
	}

	public int getWorldMinLevel() {
		return worldMinLevel;
	}

}
