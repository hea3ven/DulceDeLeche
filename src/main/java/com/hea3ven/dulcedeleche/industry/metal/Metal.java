package com.hea3ven.dulcedeleche.industry.metal;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.IStringSerializable;

import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public enum Metal implements IStringSerializable {
	IRON(ToolMaterial.IRON, ArmorMaterial.IRON, (216 << 16) + (216 << 8) + 216, "oreIron", "blockIron",
			"ingotIron", "nuggetIron", 0, 64, 20, 9),
	COPPER(null, null, (210 << 16) + (80 << 8) + 50, "oreCopper", "blockCopper", "ingotCopper",
			"nuggetCopper", 32, 64, 15, 12),
	TIN(null, null, (185 << 16) + (210 << 8) + 230, "oreTin", "blockTin", "ingotTin", "nuggetTin", 50, 90, 24,
			7),
	GOLD(ToolMaterial.GOLD, ArmorMaterial.GOLD, 0, "oreGold", "blockGold", "ingotGold", "nuggetGold", 0, 32,
			2, 9),
	BRONZE(EnumHelper.addToolMaterial("bronze", 1, 1024, 6.0F, 2.0F, 22),
			EnumHelper.addArmorMaterial("bronze", "armor", 15, new int[] {1, 5, 3, 2}, 10),
			(225 << 16) + (170 << 8) + 80, null, "blockBronze", "ingotBronze", "nuggetBronze", 0, 0, 0, 0),
	STEEL(EnumHelper.addToolMaterial("steel", 3, 3072, 7.0F, 2.5F, 14),
			EnumHelper.addArmorMaterial("steel", "armor", 28, new int[] {2, 6, 5, 2}, 10),
			(100 << 16) + (100 << 8) + 110, null, "blockSteel", "ingotSteel", "nuggetSteel", 0, 0, 0, 0),
	COBALT(EnumHelper.addToolMaterial("cobalt", 3, 2048, 9.0F, 4.0F, 16),
			EnumHelper.addArmorMaterial("cobalt", "armor", 20, new int[] {2, 7, 5, 3}, 10),
			(65 << 16) + (90 << 8) + 205, "oreCobalt", "blockCobalt", "ingotCobalt", "nuggetCobalt", 16, 70,
			10, 15),
	FERCO_STEEL(EnumHelper.addToolMaterial("fercoSteel", 3, 4096, 10.0F, 4.5F, 12),
			EnumHelper.addArmorMaterial("fercoSteel", "armor", 38, new int[] {2, 7, 5, 3}, 10),
			(50 << 16) + (60 << 8) + 105, null, "blockFercoSteel", "ingotFercoSteel", "nuggetFercoSteel", 0,
			0, 0, 0),
	TUNGSTEN(EnumHelper.addToolMaterial("tungsten", 3, 5120, 7.0F, 2.5F, 20),
			EnumHelper.addArmorMaterial("tungsten", "armor", 40, new int[] {3, 8, 6, 3}, 10),
			(60 << 16) + (50 << 8) + 50, "oreTungsten", "blockTungsten", "ingotTungsten", "nuggetTungsten", 0,
			0, 0, 0),
	MUSHET_STEEL(EnumHelper.addToolMaterial("mushetSteel", 3, 8192, 8.0F, 3.0F, 12),
			EnumHelper.addArmorMaterial("mushetSteel", "armor", 56, new int[] {3, 8, 6, 3}, 10),
			(25 << 16) + (35 << 8) + 25, null, "blockMushetSteel", "ingotMushetSteel", "nuggetMushetSteel", 0,
			0, 0, 0);

	public static Metal get(int index) {
		return values()[index];
	}

	public static void initVanillaMaterials(){
		// IRON.maxUses = 2048
		ReflectionHelper.setPrivateValue(ToolMaterial.class, ToolMaterial.IRON, 2048, 6);
		Items.iron_pickaxe.setMaxDamage(2048);
	}

	private Item.ToolMaterial toolMaterial;
	private ArmorMaterial armorMaterial;
	private int color;
	private String oreName;
	private String blockName;
	private String ingotName;
	private String nuggetName;
	private int worldMinLevel;
	private int worldMaxLevel;
	private int worldCount;
	private int worldSize;

	private Metal(ToolMaterial toolMaterial, ArmorMaterial armorMaterial, int color, String oreName,
			String blockName, String ingotName, String nuggetName, int worldMinLevel, int worldMaxLevel,
			int worldCount, int worldSize) {
		this.toolMaterial = toolMaterial;
		this.armorMaterial = armorMaterial;
		this.color = color;
		this.oreName = oreName;
		this.blockName = blockName;
		this.ingotName = ingotName;
		this.nuggetName = nuggetName;
		this.worldMinLevel = worldMinLevel;
		this.worldMaxLevel = worldMaxLevel;
		this.worldCount = worldCount;
		this.worldSize = worldSize;
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

	public int getWorldCount() {
		return worldCount;
	}

	public int getWorldSize() {
		return worldSize;
	}

	public ToolMaterial getToolMaterial() {
		return toolMaterial;
	}

	public ArmorMaterial getArmorMaterial() {
		return armorMaterial;
	}

	public String getOreName() {
		return oreName;
	}

	public String getBlockName() {
		return blockName;
	}

	public String getIngotName() {
		return ingotName;
	}

	public String getNuggetName() {
		return nuggetName;
	}

}
