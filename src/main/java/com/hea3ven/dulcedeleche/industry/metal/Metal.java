package com.hea3ven.dulcedeleche.industry.metal;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.IStringSerializable;

import net.minecraftforge.common.util.EnumHelper;

public enum Metal implements IStringSerializable {
	IRON(ToolMaterial.IRON, ArmorMaterial.IRON, (216 << 16) + (216 << 8) + 216, 0, 64),
	COPPER(null, null, (210 << 16) + (80 << 8) + 50, 32, 64),
	TIN(null, null, (185 << 16) + (210 << 8) + 230, 64, 128),
	GOLD(ToolMaterial.GOLD, ArmorMaterial.GOLD, 0, 0, 32),
	BRONZE(EnumHelper.addToolMaterial("bronze", 0, 300, 6.0F, 2.0F, 14),
			EnumHelper.addArmorMaterial("bronze", "armor", 15, new int[] {2, 6, 5, 2}, 10),
			(225 << 16) + (170 << 8) + 80, 0, 0),
	STEEL(EnumHelper.addToolMaterial("steel", 0, 300, 6.0F, 2.0F, 14),
			EnumHelper.addArmorMaterial("steel", "armor", 15, new int[] {2, 6, 5, 2}, 10),
			(100 << 16) + (100 << 8) + 110, 0, 0),
	COBALT(EnumHelper.addToolMaterial("cobalt", 0, 300, 6.0F, 2.0F, 14),
			EnumHelper.addArmorMaterial("cobalt", "armor", 15, new int[] {2, 6, 5, 2}, 10),
			(65 << 16) + (90 << 8) + 205, 0, 0),
	FERCO_STEEL(EnumHelper.addToolMaterial("fercoSteel", 0, 300, 6.0F, 2.0F, 14),
			EnumHelper.addArmorMaterial("fercoSteel", "armor", 15, new int[] {2, 6, 5, 2}, 10),
			(50 << 16) + (60 << 8) + 105, 0, 0),
	TUNGSTEN(EnumHelper.addToolMaterial("tungsten", 0, 300, 6.0F, 2.0F, 14),
			EnumHelper.addArmorMaterial("tungsten", "armor", 15, new int[] {2, 6, 5, 2}, 10),
			(60 << 16) + (50 << 8) + 50, 0, 0),
	MUSHET_STEEL(EnumHelper.addToolMaterial("mushetSteel", 0, 300, 6.0F, 2.0F, 14),
			EnumHelper.addArmorMaterial("mushetSteel", "armor", 15, new int[] {2, 6, 5, 2}, 10),
			(25 << 16) + (35 << 8) + 25, 0, 0);

	public static Metal get(int index) {
		return values()[index];
	}

	private Item.ToolMaterial toolMaterial;
	private ArmorMaterial armorMaterial;
	private int color;
	private int worldMinLevel;
	private int worldMaxLevel;

	private Metal(ToolMaterial toolMaterial, ArmorMaterial armorMaterial, int color,
			int worldMinLevel, int worldMaxLevel) {
		this.toolMaterial = toolMaterial;
		this.armorMaterial = armorMaterial;
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

	public ToolMaterial getToolMaterial() {
		return toolMaterial;
	}

	public ArmorMaterial getArmorMaterial() {
		return armorMaterial;
	}

}
