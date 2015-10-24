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
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.hea3ven.dulcedeleche.enchantments.enchantment.EnchantmentArea;
import com.hea3ven.dulcedeleche.industry.block.BlockMetalBlock;
import com.hea3ven.dulcedeleche.industry.block.BlockMetalFurnace;
import com.hea3ven.dulcedeleche.industry.block.BlockMetalOre;
import com.hea3ven.dulcedeleche.industry.block.tileentity.TileMetalFurnace;
import com.hea3ven.dulcedeleche.industry.client.GuiHandlerIndustry;
import com.hea3ven.dulcedeleche.industry.crafting.MetalFurnaceRecipes;
import com.hea3ven.dulcedeleche.industry.item.ItemMetal;
import com.hea3ven.dulcedeleche.industry.item.ItemMetalArmor;
import com.hea3ven.dulcedeleche.industry.item.ItemMetalAxe;
import com.hea3ven.dulcedeleche.industry.item.ItemMetalBlock;
import com.hea3ven.dulcedeleche.industry.item.ItemMetalHoe;
import com.hea3ven.dulcedeleche.industry.item.ItemMetalPickaxe;
import com.hea3ven.dulcedeleche.industry.item.ItemMetalShovel;
import com.hea3ven.dulcedeleche.industry.item.ItemMetalSword;
import com.hea3ven.dulcedeleche.industry.metal.Metal;
import com.hea3ven.dulcedeleche.industry.world.WorldGeneratorOre;
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
	private ItemMetalPickaxe[] pickaxes;
	private ItemMetalShovel[] shovels;
	private ItemMetalAxe[] axes;
	private ItemMetalHoe[] hoes;
	private ItemMetalSword[] swords;
	private ItemMetalArmor[] armors;

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

		pickaxes = new ItemMetalPickaxe[6];
		shovels = new ItemMetalShovel[6];
		axes = new ItemMetalAxe[6];
		hoes = new ItemMetalHoe[6];
		swords = new ItemMetalSword[6];
		armors = new ItemMetalArmor[6 * 4];
		createArmorsAndTools(Metal.BRONZE, 0);
		createArmorsAndTools(Metal.STEEL, 1);
		createArmorsAndTools(Metal.COBALT, 2);
		createArmorsAndTools(Metal.FERCO_STEEL, 3);
		createArmorsAndTools(Metal.TUNGSTEN, 4);
		createArmorsAndTools(Metal.MUSHET_STEEL, 5);
	}

	private void createArmorsAndTools(Metal metal, int index) {
		pickaxes[index] = (ItemMetalPickaxe) new ItemMetalPickaxe(metal)
				.setUnlocalizedName("pickaxe" + metal.getName());
		shovels[index] = (ItemMetalShovel) new ItemMetalShovel(metal)
				.setUnlocalizedName("shovel" + metal.getName());
		axes[index] = (ItemMetalAxe) new ItemMetalAxe(metal)
				.setUnlocalizedName("axe" + metal.getName());
		hoes[index] = (ItemMetalHoe) new ItemMetalHoe(metal)
				.setUnlocalizedName("hoe" + metal.getName());
		swords[index] = (ItemMetalSword) new ItemMetalSword(metal)
				.setUnlocalizedName("sword" + metal.getName());
		armors[index * 4] = (ItemMetalArmor) new ItemMetalArmor(metal, 0)
				.setUnlocalizedName("helmet" + metal.getName());
		armors[index * 4 + 1] = (ItemMetalArmor) new ItemMetalArmor(metal, 1)
				.setUnlocalizedName("chestplate" + metal.getName());
		armors[index * 4 + 2] = (ItemMetalArmor) new ItemMetalArmor(metal, 2)
				.setUnlocalizedName("leggings" + metal.getName());
		armors[index * 4 + 3] = (ItemMetalArmor) new ItemMetalArmor(metal, 3)
				.setUnlocalizedName("boots" + metal.getName());
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
				new InfoBlock(metalFurnace, "dulcedeleche", "metal_furnace", ItemMetalBlock.class,
						new Object[] {BlockMetalFurnace.METALS}));
	}

	@Override
	public List<InfoTileEntity> getTileEntities() {
		return Lists.newArrayList(new InfoTileEntity(TileAssembler.class, "assembler"),
				new InfoTileEntity(TileMetalFurnace.class, "metal_furnace"));
	}

	@Override
	public List<InfoItem> getItems() {
		List<InfoItem> items = Lists.newArrayList(new InfoItem(nugget, "dulcedeleche", "nugget"),
				new InfoItem(ingot, "dulcedeleche", "ingot"));
		for (int i = 0; i < 6; i++) {
			items.add(new InfoItem(pickaxes[i], "dulcedeleche",
					pickaxes[i].getMetal().getName() + "_pickaxe"));
			items.add(new InfoItem(shovels[i], "dulcedeleche",
					shovels[i].getMetal().getName() + "_shovel"));
			items.add(new InfoItem(axes[i], "dulcedeleche", axes[i].getMetal().getName() + "_axe"));
			items.add(new InfoItem(hoes[i], "dulcedeleche", hoes[i].getMetal().getName() + "_hoe"));
			items.add(new InfoItem(swords[i], "dulcedeleche",
					swords[i].getMetal().getName() + "_sword"));
			items.add(new InfoItem(armors[i * 4], "dulcedeleche",
					armors[i * 4].getMetal().getName() + "_helmet"));
			items.add(new InfoItem(armors[i * 4 + 1], "dulcedeleche",
					armors[i * 4 + 1].getMetal().getName() + "_chestplate"));
			items.add(new InfoItem(armors[i * 4 + 2], "dulcedeleche",
					armors[i * 4 + 2].getMetal().getName() + "_leggings"));
			items.add(new InfoItem(armors[i * 4 + 3], "dulcedeleche",
					armors[i * 4 + 3].getMetal().getName() + "_boots"));
		}
		return items;
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
				nugget.createStack(Metal.IRON));
		MetalFurnaceRecipes.instance().addRecipe(ore.createStack(Metal.COPPER),
				nugget.createStack(Metal.COPPER));
		MetalFurnaceRecipes.instance().addRecipe(ore.createStack(Metal.TIN),
				nugget.createStack(Metal.TIN));
		MetalFurnaceRecipes.instance().addRecipe(ore.createStack(Metal.COPPER),
				ore.createStack(Metal.TIN), nugget.createStack(Metal.BRONZE));
		MetalFurnaceRecipes.instance().addRecipe(nugget.createStack(Metal.COPPER),
				nugget.createStack(Metal.TIN), nugget.createStack(Metal.BRONZE));

		GameRegistry.registerWorldGenerator(new WorldGeneratorOre(), 1);
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
