package com.hea3ven.dulcedeleche.industry.crafting;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.item.ItemStack;

public class MetalFurnaceRecipes {
	private static MetalFurnaceRecipes instance = new MetalFurnaceRecipes();

	private Map<Pair<ItemStack, ItemStack>, MetalFurnaceRecipe> recipes = Maps.newHashMap();

	private MetalFurnaceRecipes() {
	}

	public static MetalFurnaceRecipes instance() {
		return instance;
	}

	public void addRecipe(ItemStack input, ItemStack output) {
		recipes.put(Pair.of(input, (ItemStack) null), new MetalFurnaceRecipe(input, null, output));
	}

	public void addRecipe(ItemStack input1, ItemStack input2, ItemStack output) {
		recipes.put(Pair.of(input1, input2), new MetalFurnaceRecipe(input1, input2, output));
	}

	public ItemStack getSmeltingResult(ItemStack stack1, ItemStack stack2) {
		MetalFurnaceRecipe recipe = getRecipe(stack1, stack2);
		return (recipe != null) ? recipe.getOutput() : null;
	}

	public ItemStack smelt(ItemStack stack1, ItemStack stack2) {
		MetalFurnaceRecipe recipe = getRecipe(stack1, stack2);
		return recipe.smelt(stack1, stack2);
	}

	private MetalFurnaceRecipe getRecipe(ItemStack stack1, ItemStack stack2) {
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

		private ItemStack input1;
		private ItemStack input2;
		private ItemStack output;

		public MetalFurnaceRecipe(ItemStack input1, ItemStack input2, ItemStack output) {
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
	}
}
