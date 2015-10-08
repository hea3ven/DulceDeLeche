package com.hea3ven.dulcedeleche.redstone.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler;

public class SlotCraftingAssembler extends SlotCrafting {

	private TileAssembler te;

	public SlotCraftingAssembler(EntityPlayer player, TileAssembler te, int slot, int xPosition,
			int yPosition) {
		super(player, te.getCraftingInventory(), te, slot, xPosition,
				yPosition);

		this.te = te;
	}

	@Override
	public boolean canTakeStack(EntityPlayer playerIn) {
		return te.canCraft(1);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
		this.onCrafting(stack);
		te.craftOutput(playerIn);
	}
}
