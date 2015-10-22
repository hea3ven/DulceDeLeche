package com.hea3ven.dulcedeleche;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.hea3ven.dulcedeleche.enchantments.enchantment.EnchantmentArea;
import com.hea3ven.dulcedeleche.industry.block.BlockMetalBlock;
import com.hea3ven.dulcedeleche.industry.block.BlockMetalFurnace;
import com.hea3ven.dulcedeleche.industry.block.BlockMetalOre;
import com.hea3ven.dulcedeleche.industry.block.tileentity.TileMetalFurnace;
import com.hea3ven.dulcedeleche.industry.client.GuiHandlerIndustry;
import com.hea3ven.dulcedeleche.industry.crafting.MetalFurnaceRecipes;
import com.hea3ven.dulcedeleche.industry.item.ItemMetal;
import com.hea3ven.dulcedeleche.industry.item.ItemMetalBlock;
import com.hea3ven.dulcedeleche.industry.metal.Metal;
import com.hea3ven.dulcedeleche.redstone.block.BlockAssembler;
import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler;
import com.hea3ven.dulcedeleche.redstone.client.GuiHandlerRedstone;
import com.hea3ven.tools.commonutils.mod.InfoBlock;
import com.hea3ven.tools.commonutils.mod.InfoItem;
import com.hea3ven.tools.commonutils.mod.InfoTileEntity;
import com.hea3ven.tools.commonutils.mod.ModInitializerCommon;
import com.hea3ven.tools.commonutils.mod.ProxyModBase;

public class ProxyCommonDulceDeLeche extends ProxyModBase {

	private Block assembler;
	private BlockMetalOre ore;
	private Block metalBlock;
	private ItemMetal nugget;
	private ItemMetal ingot;
	private BlockMetalFurnace metalFurnace;

	public ProxyCommonDulceDeLeche(ModInitializerCommon modInitializer) {
		super(modInitializer);
		assembler = new BlockAssembler()
				.setUnlocalizedName("assembler")
				.setHardness(3.5F)
				.setStepSound(Block.soundTypePiston);
		ore = (BlockMetalOre) new BlockMetalOre()
				.setUnlocalizedName("ore")
				.setHardness(3.0F)
				.setResistance(5.0F)
				.setStepSound(Block.soundTypePiston);
		metalBlock = new BlockMetalBlock()
				.setUnlocalizedName("blockMetal")
				.setHardness(5.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundTypeMetal);
		nugget = (ItemMetal) new ItemMetal(ItemMetal.NUGGETS).setUnlocalizedName("nugget");
		ingot = (ItemMetal) new ItemMetal(ItemMetal.INGOTS).setUnlocalizedName("ingot");
		metalFurnace = (BlockMetalFurnace) new BlockMetalFurnace()
				.setUnlocalizedName("metalFurnace")
				.setHardness(3.5F)
				.setStepSound(Block.soundTypeMetal);
	}

	@Override
	public void registerEnchantments() {
		registerEnchantmentArea();
	}

	@Override
	public List<InfoBlock> getBlocks() {
		return Lists.newArrayList(new InfoBlock(assembler, "dulcedeleche", "assembler"),
				new InfoBlock(ore, "dulcedeleche", "ore", ItemMetalBlock.class,
						new Object[] {BlockMetalOre.ORES}),
				new InfoBlock(metalBlock, "dulcedeleche", "block_metal", ItemMetalBlock.class,
						new Object[] {BlockMetalBlock.BLOCKS}),
				new InfoBlock(metalFurnace, "dulcedeleche", "metal_furnace"));
	}

	@Override
	public List<InfoTileEntity> getTileEntities() {
		return Lists.newArrayList(new InfoTileEntity(TileAssembler.class, "assembler"),
				new InfoTileEntity(TileMetalFurnace.class, "metal_furnace"));
	}

	@Override
	public List<InfoItem> getItems() {
		return Lists.newArrayList(new InfoItem(nugget, "dulcedeleche", "nugget"),
				new InfoItem(ingot, "dulcedeleche", "ingot"));
	}

	@Override
	public List<Pair<String, IGuiHandler>> getGuiHandlers() {
		return Lists.newArrayList(
				Pair.of(ModDulceDeLeche.MODID, (IGuiHandler) new GuiHandlerRedstone()),
				Pair.of(ModDulceDeLeche.MODID, (IGuiHandler) new GuiHandlerIndustry()));
	}

	@Override
	public void onPostInitEvent() {
		super.onPostInitEvent();

		removeIngotSmeltingRecipes();
		MetalFurnaceRecipes.instance().addRecipe(new ItemStack(Blocks.iron_ore),
				new ItemStack(nugget, 1, nugget.getMetaForMetal(Metal.IRON)));
		MetalFurnaceRecipes.instance().addRecipe(
				new ItemStack(ore, 1, ore.getMetaForMetal(Metal.COPPER)),
				new ItemStack(nugget, 1, nugget.getMetaForMetal(Metal.COPPER)));
		MetalFurnaceRecipes.instance().addRecipe(
				new ItemStack(ore, 1, ore.getMetaForMetal(Metal.TIN)),
				new ItemStack(nugget, 1, nugget.getMetaForMetal(Metal.TIN)));
		MetalFurnaceRecipes.instance().addRecipe(
				new ItemStack(ore, 1, ore.getMetaForMetal(Metal.COPPER)),
				new ItemStack(ore, 1, ore.getMetaForMetal(Metal.TIN)),
				new ItemStack(nugget, 1, nugget.getMetaForMetal(Metal.BRONZE)));
		MetalFurnaceRecipes.instance().addRecipe(
				new ItemStack(nugget, 3, nugget.getMetaForMetal(Metal.COPPER)),
				new ItemStack(nugget, 1, nugget.getMetaForMetal(Metal.TIN)),
				new ItemStack(nugget, 1, nugget.getMetaForMetal(Metal.BRONZE)));
	}

	private void registerEnchantmentArea() {
		EnchantmentArea.instance = new EnchantmentArea(100);
		MinecraftForge.EVENT_BUS.register(EnchantmentArea.instance);
	}

	private void removeIngotSmeltingRecipes() {
		Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
		for (Entry<ItemStack, ItemStack> entry : Sets.newHashSet(recipes.entrySet())) {
			if (entry.getValue().getItem() == Items.iron_ingot) {
				recipes.remove(entry.getKey());
			} else if (entry.getValue().getItem() == Items.gold_ingot) {
				recipes.remove(entry.getKey());
			}
		}
	}

}
