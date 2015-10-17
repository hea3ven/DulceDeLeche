package com.hea3ven.dulcedeleche;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.hea3ven.dulcedeleche.enchantments.enchantment.EnchantmentArea;
import com.hea3ven.dulcedeleche.industry.block.BlockVariantOre;
import com.hea3ven.dulcedeleche.industry.item.ItemVariantIngot;
import com.hea3ven.dulcedeleche.industry.item.ItemVariantOre;
import com.hea3ven.dulcedeleche.industry.metal.Metal;
import com.hea3ven.dulcedeleche.redstone.block.BlockAssembler;
import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler;
import com.hea3ven.dulcedeleche.redstone.client.GuiHandler;
import com.hea3ven.tools.commonutils.mod.InfoBlock;
import com.hea3ven.tools.commonutils.mod.InfoBlockVariant;
import com.hea3ven.tools.commonutils.mod.InfoItem;
import com.hea3ven.tools.commonutils.mod.InfoTileEntity;
import com.hea3ven.tools.commonutils.mod.ModInitializerCommon;
import com.hea3ven.tools.commonutils.mod.ProxyModBase;

public class ProxyCommonDulceDeLeche extends ProxyModBase {

	private Block assembler;
	private Block ore;
	private Item ingot;

	public ProxyCommonDulceDeLeche(ModInitializerCommon modInitializer) {
		super(modInitializer);
		assembler = new BlockAssembler()
				.setUnlocalizedName("assembler")
				.setHardness(3.5F)
				.setStepSound(Block.soundTypePiston);
		ore = new BlockVariantOre()
				.setHardness(3.0F)
				.setResistance(5.0F)
				.setStepSound(Block.soundTypePiston)
				.setUnlocalizedName("ore");
		ingot = new ItemVariantIngot().setUnlocalizedName("ingot");
	}

	@Override
	public void registerEnchantments() {
		registerEnchantmentArea();
	}

	@Override
	public List<InfoBlock> getBlocks() {
		return Lists.newArrayList(new InfoBlock(assembler, "dulcedeleche", "assembler"));
	}

	@Override
	public List<InfoBlockVariant> getVariantBlocks() {
		Map<Object, Integer> metalOreMetas = Maps.newHashMap();
		for (Metal metal : Metal.ORES) {
			metalOreMetas.put(metal, metal.getOreIndex());
		}
		return Lists.newArrayList(new InfoBlockVariant(ore, "dulcedeleche", "ore",
				ItemVariantOre.class, Metal.METAL_ORE, "_ore", metalOreMetas));
	}

	@Override
	public List<InfoTileEntity> getTileEntities() {
		return Lists.newArrayList(new InfoTileEntity(TileAssembler.class, "assembler"));
	}

	@Override
	public List<InfoItem> getItems() {
		return Lists.newArrayList(new InfoItem(ingot, "dulcedeleche", "ingot"));
	}

	@Override
	public List<Pair<String, IGuiHandler>> getGuiHandlers() {
		return Lists.newArrayList(Pair.of(ModDulceDeLeche.MODID, (IGuiHandler) new GuiHandler()));
	}

	private void registerEnchantmentArea() {
		EnchantmentArea.instance = new EnchantmentArea(100);
		MinecraftForge.EVENT_BUS.register(EnchantmentArea.instance);
	}

}
