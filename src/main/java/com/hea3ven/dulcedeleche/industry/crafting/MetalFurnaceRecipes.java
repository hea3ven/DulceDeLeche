package com.hea3ven.dulcedeleche.industry.crafting;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import com.hea3ven.dulcedeleche.ProxyCommonDulceDeLeche;
import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class MetalFurnaceRecipes {
	private static MetalFurnaceRecipes instance = new MetalFurnaceRecipes();

	private Map<Pair<ItemStack, ItemStack>, MetalFurnaceRecipe> recipes = Maps.newHashMap();

	private MetalFurnaceRecipes() {
	}

	public static MetalFurnaceRecipes instance() {
		return instance;
	}

	public void addRecipe(int tier, String oreInput, int sizeInput, ItemStack output) {
		for (ItemStack input : OreDictionary.getOres(oreInput)) {
			input = input.copy();
			input.stackSize = sizeInput;
			addRecipe(tier, input, output);
		}
	}

	public void addRecipe(int tier, ItemStack input, ItemStack output) {
		recipes.put(Pair.of(input, (ItemStack) null),
				new MetalFurnaceRecipe(tier, input, null, output));
	}

	public void addRecipe(int tier, String oreInput1, int sizeInput1, String oreInput2,
			int sizeInput2, ItemStack output) {
		for (ItemStack input1 : OreDictionary.getOres(oreInput1)) {
			input1 = input1.copy();
			input1.stackSize = sizeInput1;
			for (ItemStack input2 : OreDictionary.getOres(oreInput2)) {
				input2 = input2.copy();
				input2.stackSize = sizeInput2;
				addRecipe(tier, input1, input2, output);
			}
		}
	}

	public void addRecipe(int tier, ItemStack input1, ItemStack input2, ItemStack output) {
		recipes.put(Pair.of(input1, input2), new MetalFurnaceRecipe(tier, input1, input2, output));
	}

	public void addMetalRecipe(int tier, Metal metal) {
		for (ItemStack input1 : OreDictionary.getOres(metal.getOreName())) {
			input1 = input1.copy();
			if (metal == Metal.GOLD)
				MetalFurnaceRecipes.instance().addRecipe(tier, input1,
						new ItemStack(Items.gold_nugget));
			else
				MetalFurnaceRecipes.instance().addRecipe(tier, input1,
						ProxyCommonDulceDeLeche.nugget.createStack(metal, 3));
		}
		for (ItemStack input1 : OreDictionary.getOres(metal.getIngotName())) {
			input1 = input1.copy();
			input1.stackSize = 9;
			if (metal == Metal.IRON)
				MetalFurnaceRecipes.instance().addRecipe(tier, input1,
						new ItemStack(Blocks.iron_block));
			else if (metal == Metal.GOLD)
				MetalFurnaceRecipes.instance().addRecipe(tier, input1,
						new ItemStack(Blocks.gold_block));
			else
				MetalFurnaceRecipes.instance().addRecipe(tier, input1,
						ProxyCommonDulceDeLeche.metalBlock.createStack(metal));
		}
		for (ItemStack input1 : OreDictionary.getOres(metal.getNuggetName())) {
			input1 = input1.copy();
			input1.stackSize = 9;
			if (metal == Metal.IRON)
				MetalFurnaceRecipes.instance().addRecipe(tier, input1,
						new ItemStack(Items.iron_ingot));
			else if (metal == Metal.GOLD)
				MetalFurnaceRecipes.instance().addRecipe(tier, input1,
						new ItemStack(Items.gold_ingot));
			else
				MetalFurnaceRecipes.instance().addRecipe(tier, input1,
						ProxyCommonDulceDeLeche.ingot.createStack(metal));
		}
	}

	public void addAlloyRecipe(int tier, Metal inputMetal1, int sizeInput1, Metal inputMetal2,
			int sizeInput2, Metal outputMetal, int sizeOutput) {
		for (ItemStack input1 : OreDictionary.getOres(inputMetal1.getOreName())) {
			input1 = input1.copy();
			input1.stackSize = sizeInput1;
			for (ItemStack input2 : OreDictionary.getOres(inputMetal2.getOreName())) {
				input2 = input2.copy();
				input2.stackSize = sizeInput2;
				MetalFurnaceRecipes.instance().addRecipe(tier, input1, input2,
						ProxyCommonDulceDeLeche.nugget.createStack(outputMetal, sizeOutput));
			}
		}
		for (ItemStack input1 : OreDictionary.getOres(inputMetal1.getBlockName())) {
			input1 = input1.copy();
			input1.stackSize = sizeInput1;
			for (ItemStack input2 : OreDictionary.getOres(inputMetal2.getBlockName())) {
				input2 = input2.copy();
				input2.stackSize = sizeInput2;
				MetalFurnaceRecipes.instance().addRecipe(tier, input1, input2,
						ProxyCommonDulceDeLeche.metalBlock.createStack(outputMetal, sizeOutput));
			}
		}
		for (ItemStack input1 : OreDictionary.getOres(inputMetal1.getIngotName())) {
			input1 = input1.copy();
			input1.stackSize = sizeInput1;
			for (ItemStack input2 : OreDictionary.getOres(inputMetal2.getIngotName())) {
				input2 = input2.copy();
				input2.stackSize = sizeInput2;
				MetalFurnaceRecipes.instance().addRecipe(tier, input1, input2,
						ProxyCommonDulceDeLeche.ingot.createStack(outputMetal, sizeOutput));
			}
		}
		for (ItemStack input1 : OreDictionary.getOres(inputMetal1.getNuggetName())) {
			input1 = input1.copy();
			input1.stackSize = sizeInput1;
			for (ItemStack input2 : OreDictionary.getOres(inputMetal2.getNuggetName())) {
				input2 = input2.copy();
				input2.stackSize = sizeInput2;
				MetalFurnaceRecipes.instance().addRecipe(tier, input1, input2,
						ProxyCommonDulceDeLeche.nugget.createStack(outputMetal, sizeOutput));
			}
		}
		for (ItemStack input1 : OreDictionary.getOres(outputMetal.getIngotName())) {
			input1 = input1.copy();
			input1.stackSize = 9;
			MetalFurnaceRecipes.instance().addRecipe(tier, input1,
					ProxyCommonDulceDeLeche.metalBlock.createStack(outputMetal));
		}
		for (ItemStack input1 : OreDictionary.getOres(outputMetal.getNuggetName())) {
			input1 = input1.copy();
			input1.stackSize = 9;
			MetalFurnaceRecipes.instance().addRecipe(tier, input1,
					ProxyCommonDulceDeLeche.ingot.createStack(outputMetal));
		}
	}

	public boolean isInput(ItemStack stack) {
		for (Entry<Pair<ItemStack, ItemStack>, MetalFurnaceRecipe> entry : recipes.entrySet()) {
			if ((ItemStack.areItemsEqual(stack, entry.getKey().getLeft())
					&& ItemStack.areItemStackTagsEqual(stack, entry.getKey().getLeft()))
					|| (ItemStack.areItemsEqual(stack, entry.getKey().getRight())
							&& ItemStack.areItemStackTagsEqual(stack, entry.getKey().getRight())))
				return true;
		}
		return false;
	}

	public ItemStack getSmeltingResult(ItemStack stack1, ItemStack stack2) {
		MetalFurnaceRecipe recipe = getRecipe(stack1, stack2);
		return (recipe != null) ? recipe.getOutput() : null;
	}

	public ItemStack smelt(ItemStack stack1, ItemStack stack2) {
		MetalFurnaceRecipe recipe = getRecipe(stack1, stack2);
		return recipe.smelt(stack1, stack2);
	}

	public MetalFurnaceRecipe getRecipe(ItemStack stack1, ItemStack stack2) {
		if (stack1 == null && stack2 == null)
			return null;
		if (stack1 == null) {
			stack1 = stack2;
			stack2 = null;
		}
		for (Entry<Pair<ItemStack, ItemStack>, MetalFurnaceRecipe> entry : recipes.entrySet()) {
			Pair<ItemStack, ItemStack> inputs = entry.getKey();
			ItemStack input2 = inputs.getRight();
			ItemStack input1 = inputs.getLeft();
			if ((itemsMatch(input1, stack1) && itemsMatch(input2, stack2))
					|| (itemsMatch(input1, stack2) && itemsMatch(input2, stack1)))
				return entry.getValue();
		}
		return null;
	}

	private boolean itemsMatch(ItemStack stack1, ItemStack stack2) {
		return stack1 == null && stack2 == null || ItemStack.areItemsEqual(stack1, stack2)
				&& stack1.getMetadata() == stack2.getMetadata();
	}

	public static class MetalFurnaceRecipe {

		private int tier;
		private ItemStack input1;
		private ItemStack input2;
		private ItemStack output;

		public MetalFurnaceRecipe(int tier, ItemStack input1, ItemStack input2, ItemStack output) {
			this.tier = tier;
			this.input1 = input1;
			this.input2 = input2;
			this.output = output;
		}

		public ItemStack getOutput() {
			return output;
		}

		public ItemStack smelt(ItemStack stack1, ItemStack stack2) {
			stack1.stackSize -= input1.stackSize;
			if (input2 != null)
				stack2.stackSize -= input2.stackSize;
			return output.copy();
		}

		public int getTier() {
			return tier;
		}
	}

}
