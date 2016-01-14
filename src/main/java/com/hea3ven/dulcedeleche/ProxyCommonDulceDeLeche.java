package com.hea3ven.dulcedeleche;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.hea3ven.dulcedeleche.enchantments.enchantment.EnchantmentArea;
import com.hea3ven.dulcedeleche.industry.block.BlockBrickFurnace;
import com.hea3ven.dulcedeleche.industry.block.BlockMetalBlock;
import com.hea3ven.dulcedeleche.industry.block.BlockMetalFurnace;
import com.hea3ven.dulcedeleche.industry.block.BlockMetalOre;
import com.hea3ven.dulcedeleche.industry.block.tileentity.TileMetalFurnace;
import com.hea3ven.dulcedeleche.industry.client.gui.GuiMetalFurnace;
import com.hea3ven.dulcedeleche.industry.crafting.MetalFurnaceRecipes;
import com.hea3ven.dulcedeleche.industry.item.*;
import com.hea3ven.dulcedeleche.industry.metal.Metal;
import com.hea3ven.dulcedeleche.industry.world.WorldGeneratorOre;
import com.hea3ven.dulcedeleche.redstone.block.BlockAssembler;
import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler;
import com.hea3ven.dulcedeleche.redstone.client.gui.GuiAssembler;
import com.hea3ven.dulcedeleche.redstone.inventory.ContainerAssembler;
import com.hea3ven.tools.commonutils.inventory.ISimpleGuiHandler;
import com.hea3ven.tools.commonutils.mod.ProxyModBase;
import com.hea3ven.tools.commonutils.util.WorldHelper;

public class ProxyCommonDulceDeLeche extends ProxyModBase {

	public static Block assembler;
	public static BlockMetalOre ore;
	public static BlockMetalBlock metalBlock;
	public static ItemMetal nugget;
	public static ItemMetal ingot;
	public static BlockBrickFurnace brickFurnace;
	public static BlockMetalFurnace metalFurnace;
	public static ItemMetalPickaxe[] pickaxes;
	public static ItemMetalShovel[] shovels;
	public static ItemMetalAxe[] axes;
	public static ItemMetalHoe[] hoes;
	public static ItemMetalSword[] swords;
	public static ItemMetalArmor[] armors;

	public ProxyCommonDulceDeLeche() {
		super("dulcedeleche");

		assembler = new BlockAssembler().setUnlocalizedName("assembler")
				.setHardness(3.5F)
				.setStepSound(Block.soundTypePiston);
		ore = (BlockMetalOre) new BlockMetalOre().setUnlocalizedName("ore")
				.setHardness(3.0F)
				.setResistance(5.0F)
				.setStepSound(Block.soundTypePiston);
		metalBlock = (BlockMetalBlock) new BlockMetalBlock().setUnlocalizedName("blockMetal")
				.setHardness(5.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundTypeMetal);
		nugget = (ItemMetal) new ItemMetal(ItemMetal.NUGGETS).setUnlocalizedName("nugget");
		ingot = (ItemMetal) new ItemMetal(ItemMetal.INGOTS).setUnlocalizedName("ingot");
		brickFurnace = (BlockBrickFurnace) new BlockBrickFurnace().setUnlocalizedName("brickFurnace")
				.setHardness(2.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundTypePiston);
		metalFurnace = (BlockMetalFurnace) new BlockMetalFurnace().setUnlocalizedName("metalFurnace")
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

		addBlock(assembler, "assembler");
		addBlock(ore, "ore", ItemMetalBlock.class, new Object[] {BlockMetalOre.ORES});
		addBlock(metalBlock, "block_metal", ItemMetalBlock.class, new Object[] {BlockMetalBlock.BLOCKS});
		addBlock(brickFurnace, "brick_furnace");
		addBlock(metalFurnace, "metal_furnace", ItemMetalBlock.class,
				new Object[] {BlockMetalFurnace.METALS});

		addTileEntity(TileAssembler.class, "assembler");
		addTileEntity(TileMetalFurnace.class, "metal_furnace");

		addItem(nugget, "nugget");
		addItem(ingot, "ingot");
		for (int i = 0; i < 6; i++) {
			addItem(pickaxes[i], pickaxes[i].getMetal().getName() + "_pickaxe");
			addItem(shovels[i], shovels[i].getMetal().getName() + "_shovel");
			addItem(axes[i], axes[i].getMetal().getName() + "_axe");
			addItem(hoes[i], hoes[i].getMetal().getName() + "_hoe");
			addItem(swords[i], swords[i].getMetal().getName() + "_sword");
			addItem(armors[i * 4], armors[i * 4].getMetal().getName() + "_helmet");
			addItem(armors[i * 4 + 1], armors[i * 4 + 1].getMetal().getName() + "_chestplate");
			addItem(armors[i * 4 + 2], armors[i * 4 + 2].getMetal().getName() + "_leggings");
			addItem(armors[i * 4 + 3], armors[i * 4 + 3].getMetal().getName() + "_boots");
		}

		addGui(GuiAssembler.id, new ISimpleGuiHandler() {
			@Override
			public Container createContainer(EntityPlayer player, World world, BlockPos pos) {
				return new ContainerAssembler(player.inventory,
						WorldHelper.<TileAssembler>getTile(world, pos));
			}

			@Override
			@SideOnly(Side.CLIENT)
			public Gui createGui(EntityPlayer player, World world, BlockPos pos) {
				return new GuiAssembler(player.inventory, WorldHelper.<TileAssembler>getTile(world, pos));
			}
		});
		addGui(GuiMetalFurnace.id, new ISimpleGuiHandler() {
			@Override
			public Container createContainer(EntityPlayer player, World world, BlockPos pos) {
				return WorldHelper.<TileMetalFurnace>getTile(world, pos).getContainer(player.inventory);
			}

			@Override
			@SideOnly(Side.CLIENT)
			public Gui createGui(EntityPlayer player, World world, BlockPos pos) {
				return new GuiMetalFurnace(player.inventory,
						WorldHelper.<TileMetalFurnace>getTile(world, pos));
			}
		});
	}

	private void createArmorsAndTools(Metal metal, int index) {
		pickaxes[index] = (ItemMetalPickaxe) new ItemMetalPickaxe(metal).setUnlocalizedName(
				"pickaxe" + metal.getName());
		shovels[index] =
				(ItemMetalShovel) new ItemMetalShovel(metal).setUnlocalizedName("shovel" + metal.getName());
		axes[index] = (ItemMetalAxe) new ItemMetalAxe(metal).setUnlocalizedName("axe" + metal.getName());
		hoes[index] = (ItemMetalHoe) new ItemMetalHoe(metal).setUnlocalizedName("hoe" + metal.getName());
		swords[index] =
				(ItemMetalSword) new ItemMetalSword(metal).setUnlocalizedName("sword" + metal.getName());
		armors[index * 4] =
				(ItemMetalArmor) new ItemMetalArmor(metal, 0).setUnlocalizedName("helmet" + metal.getName());
		armors[index * 4 + 1] = (ItemMetalArmor) new ItemMetalArmor(metal, 1).setUnlocalizedName(
				"chestplate" + metal.getName());
		armors[index * 4 + 2] = (ItemMetalArmor) new ItemMetalArmor(metal, 2).setUnlocalizedName(
				"leggings" + metal.getName());
		armors[index * 4 + 3] =
				(ItemMetalArmor) new ItemMetalArmor(metal, 3).setUnlocalizedName("boots" + metal.getName());
	}

	@Override
	public void onInitEvent(FMLInitializationEvent event) {
		for (Metal metal : ore.getMetalComponent().getMetals()) {
			OreDictionary.registerOre(metal.getOreName(), ore.createStack(metal));
		}
		for (Metal metal : metalBlock.getMetalComponent().getMetals()) {
			OreDictionary.registerOre(metal.getBlockName(), metalBlock.createStack(metal));
		}
		for (Metal metal : ingot.getMetalComponent().getMetals()) {
			OreDictionary.registerOre(metal.getIngotName(), ingot.createStack(metal));
		}
		for (Metal metal : nugget.getMetalComponent().getMetals()) {
			OreDictionary.registerOre(metal.getNuggetName(), nugget.createStack(metal));
		}

		super.onInitEvent(event);
	}

	@Override
	public void onPostInitEvent(FMLPostInitializationEvent event) {
		super.onPostInitEvent(event);

		MinecraftForge.EVENT_BUS.register(EnchantmentArea.instance);

		GameRegistry.registerWorldGenerator(new WorldGeneratorOre(), 1);
	}

	@Override
	public void registerEnchantments() {
		EnchantmentArea.instance = new EnchantmentArea(100);
	}

	@Override
	public void registerRecipes() {
		removeIngotSmeltingRecipes();

		addMetalFurnaceRecipes();

		addMetalSmeltingRecipes();

		addMetalRecipes();

		addToolsRecipes();

		GameRegistry.addRecipe(
				new ShapedOreRecipe(Block.getBlockFromName("dulcedeleche:assembler"), "xxx", "xyx", "xxx",
						'x', "cobblestone", 'y', new ItemStack(Blocks.crafting_table)));
	}

	private void addMetalRecipe(ItemStack itemStack, Object... recipe) {
		for (String recipeMetal : recipeMetals) {
			Object[] newRecipe = new Object[recipe.length];
			for (int i = 0; i < recipe.length; i++) {
				if (recipe[i] == null)
					newRecipe[i] = recipeMetal;
				else
					newRecipe[i] = recipe[i];
			}
			GameRegistry.addRecipe(new ShapedOreRecipe(itemStack, newRecipe));
		}
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

	private void addMetalFurnaceRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(brickFurnace), "xxx", "x x", "xxx", 'x',
				new ItemStack(Blocks.brick_block));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(metalFurnace.createStack(Metal.BRONZE), "xxx", "x x", "xxx", 'x',
						"blockBronze"));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(metalFurnace.createStack(Metal.STEEL), "xxx", "x x", "xxx", 'x',
						"blockSteel"));
		GameRegistry.addRecipe(
				new ShapedOreRecipe(metalFurnace.createStack(Metal.COBALT), "xxx", "x x", "xxx", 'x',
						"blockCobalt"));
	}

	private void addMetalSmeltingRecipes() {
		MetalFurnaceRecipes.instance().addMetalRecipe(0, Metal.GOLD);
		MetalFurnaceRecipes.instance().addMetalRecipe(0, Metal.TIN);
		MetalFurnaceRecipes.instance().addMetalRecipe(0, Metal.COPPER);
		MetalFurnaceRecipes.instance().addAlloyRecipe(0, Metal.COPPER, 3, Metal.TIN, 1, Metal.BRONZE, 2);
		MetalFurnaceRecipes.instance().addMetalRecipe(1, Metal.IRON);
		for (ItemStack stack : OreDictionary.getOres("oreIron")) {
			MetalFurnaceRecipes.instance()
					.addRecipe(1, stack, new ItemStack(Items.coal), nugget.createStack(Metal.IRON));
		}
		for (ItemStack stack : OreDictionary.getOres("blockIron")) {
			MetalFurnaceRecipes.instance()
					.addRecipe(1, stack, new ItemStack(Blocks.coal_block),
							metalBlock.createStack(Metal.STEEL));
			MetalFurnaceRecipes.instance()
					.addRecipe(1, stack, new ItemStack(Items.coal, 9), metalBlock.createStack(Metal.STEEL));
		}
		for (ItemStack stack : OreDictionary.getOres("ingotIron")) {
			MetalFurnaceRecipes.instance()
					.addRecipe(1, stack, new ItemStack(Items.coal), ingot.createStack(Metal.STEEL));
		}
		for (ItemStack stack : OreDictionary.getOres("nuggetIron")) {
			stack = stack.copy();
			stack.stackSize = 9;
			MetalFurnaceRecipes.instance()
					.addRecipe(1, stack, new ItemStack(Items.coal), ingot.createStack(Metal.STEEL));
		}
		MetalFurnaceRecipes.instance().addMetalRecipe(2, Metal.COBALT);
		MetalFurnaceRecipes.instance().addMetalRecipe(2, Metal.TUNGSTEN);
		MetalFurnaceRecipes.instance()
				.addAlloyRecipe(3, Metal.STEEL, 3, Metal.COBALT, 1, Metal.FERCO_STEEL, 2);
		MetalFurnaceRecipes.instance()
				.addAlloyRecipe(3, Metal.STEEL, 2, Metal.TUNGSTEN, 1, Metal.MUSHET_STEEL, 1);
	}

	private static final String[] recipeMetals =
			new String[] {Metal.BRONZE.getIngotName(), Metal.COBALT.getNuggetName()};

	private void addMetalRecipes() {
		for (Metal metal : metalBlock.getMetalComponent().getMetals()) {
			GameRegistry.addRecipe(
					new ShapedOreRecipe(metalBlock.createStack(metal), "xxx", "xxx", "xxx", 'x',
							metal.getIngotName()));
		}
		for (Metal metal : ingot.getMetalComponent().getMetals()) {
			GameRegistry.addRecipe(new ShapedOreRecipe(ingot.createStack(metal), "xxx", "xxx", "xxx", 'x',
					metal.getNuggetName()));
			GameRegistry.addRecipe(new ShapelessOreRecipe(ingot.createStack(metal, 9), metal.getBlockName()));
		}
		GameRegistry.addRecipe(
				new ShapedOreRecipe(Items.iron_ingot, "xxx", "xxx", "xxx", 'x', Metal.IRON.getNuggetName()));
		for (Metal metal : nugget.getMetalComponent().getMetals()) {
			GameRegistry.addRecipe(
					new ShapelessOreRecipe(nugget.createStack(metal, 9), metal.getIngotName()));
		}

		addMetalRecipe(new ItemStack(Blocks.rail, 16), "X X", "X#X", "X X", 'X', null, '#', "stickWood");
		addMetalRecipe(new ItemStack(Blocks.activator_rail, 6), "XSX", "X#X", "XSX", 'X', null, '#',
				Blocks.redstone_torch, 'S', "stickWood");
		addMetalRecipe(new ItemStack(Blocks.detector_rail, 6), "X X", "X#X", "XRX", 'X', null, 'R',
				"dustRedstone", '#', Blocks.stone_pressure_plate);
		addMetalRecipe(new ItemStack(Items.minecart, 1), "# #", "###", '#', null);
		addMetalRecipe(new ItemStack(Items.cauldron, 1), "# #", "# #", "###", '#', null);
		addMetalRecipe(new ItemStack(Items.bucket, 1), "# #", " # ", '#', null);
		addMetalRecipe(new ItemStack(Blocks.tripwire_hook, 2), "I", "S", "#", '#', "plankWood", 'S',
				"stickWood", 'I', null);
		addMetalRecipe(new ItemStack(Items.compass, 1), " # ", "#X#", " # ", '#', null, 'X', "dustRedstone");
		addMetalRecipe(new ItemStack(Blocks.piston, 1), "TTT", "#X#", "#R#", '#', "cobblestone", 'X', null,
				'R', "dustRedstone", 'T', "planksWood");
		addMetalRecipe(new ItemStack(Blocks.hopper), "I I", "ICI", " I ", 'I', null, 'C', "chest");
	}

	private void addToolsRecipes() {
		for (int i = 0; i < 6; i++) {
			GameRegistry.addRecipe(new ShapedOreRecipe(pickaxes[i], "xxx", " y ", " y ", 'x',
					pickaxes[i].getMetal().getIngotName(), 'y', "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(shovels[i], " x ", " y ", " y ", 'x',
					shovels[i].getMetal().getIngotName(), 'y', "stickWood"));
			GameRegistry.addRecipe(
					new ShapedOreRecipe(axes[i], "xx ", "xy ", " y ", 'x', axes[i].getMetal().getIngotName(),
							'y', "stickWood"));
			GameRegistry.addRecipe(
					new ShapedOreRecipe(hoes[i], "xx ", " y ", " y ", 'x', hoes[i].getMetal().getIngotName(),
							'y', "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(swords[i], " x ", " x ", " y ", 'x',
					swords[i].getMetal().getIngotName(), 'y', "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(armors[i * 4], "xxx", "x x", 'x',
					armors[i * 4].getMetal().getIngotName()));
			GameRegistry.addRecipe(new ShapedOreRecipe(armors[i * 4 + 1], "x x", "xxx", "xxx", 'x',
					armors[i * 4 + 1].getMetal().getIngotName()));
			GameRegistry.addRecipe(new ShapedOreRecipe(armors[i * 4 + 2], "xxx", "x x", "x x", 'x',
					armors[i * 4 + 2].getMetal().getIngotName()));
			GameRegistry.addRecipe(new ShapedOreRecipe(armors[i * 4 + 3], "x x", "x x", 'x',
					armors[i * 4 + 3].getMetal().getIngotName()));
		}
	}
}
