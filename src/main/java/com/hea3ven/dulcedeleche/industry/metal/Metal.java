package com.hea3ven.dulcedeleche.industry.metal;

import net.minecraft.util.IStringSerializable;

public enum Metal implements IStringSerializable {
	IRON((216 << 16) + (216 << 8) + 216),
	COPPER((200 << 16) + (75 << 8) + 40),
	TIN((185 << 16) + (210 << 8) + 230),
	GOLD(0);

	public static Metal get(int index) {
		return values()[index];
	}

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

	public int getColor() {
		return color;
	}

}
