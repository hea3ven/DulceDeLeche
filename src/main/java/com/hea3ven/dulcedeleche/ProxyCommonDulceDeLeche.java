package com.hea3ven.dulcedeleche;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.hea3ven.dulcedeleche.enchantments.enchantment.EnchantmentArea;
import com.hea3ven.dulcedeleche.redstone.block.BlockAssembler;
import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler;
import com.hea3ven.dulcedeleche.redstone.client.GuiHandler;
import com.hea3ven.tools.commonutils.mod.ModInitializerCommon;
import com.hea3ven.tools.commonutils.mod.ProxyModBase;

public class ProxyCommonDulceDeLeche extends ProxyModBase {

	public ProxyCommonDulceDeLeche(ModInitializerCommon modInitializer) {
		super(modInitializer);
	}

	@Override
	public void registerEnchantments() {
		registerEnchantmentArea();
	}

	@Override
	public void registerBlocks() {
		registerBlockAssembler();
	}

	@Override
	public ArrayList<Pair<String, IGuiHandler>> getGuiHandlers() {
		return Lists.newArrayList(Pair.of(ModDulceDeLeche.MODID, (IGuiHandler) new GuiHandler()));
	}

	private void registerEnchantmentArea() {
		EnchantmentArea.instance = new EnchantmentArea(100);
		MinecraftForge.EVENT_BUS.register(EnchantmentArea.instance);
	}

	private void registerBlockAssembler() {
		Block assembler = new BlockAssembler()
				.setUnlocalizedName("assembler")
				.setHardness(3.5F)
				.setStepSound(Block.soundTypePiston);
		GameRegistry.registerBlock(assembler, "assembler");
		GameRegistry.registerTileEntity(TileAssembler.class, "assembler");
	}

}
