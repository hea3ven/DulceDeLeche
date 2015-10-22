package com.hea3ven.dulcedeleche.industry.block.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

import net.minecraftforge.common.util.Constants.NBT;

import com.hea3ven.dulcedeleche.industry.crafting.MetalFurnaceRecipes;
import com.hea3ven.tools.commonutils.inventory.GenericContainer;
import com.hea3ven.tools.commonutils.tileentity.TileMachine;

public class TileMetalFurnace extends TileMachine implements IInventory, IUpdatePlayerListBox {

	public static final int MAX_PROGRESS = 400;

	public static final int FIELD_PROGRESS = 0;
	public static final int FIELD_FUEL = 1;
	public static final int FIELD_FUELCAPACITY = 2;

	private ItemStack[] slots = new ItemStack[4];

	private int progress = 0;
	private int fuel = 0;
	private int fuelCapacity = 1;

	public int getProgress() {
		return progress;
	}

	public int getFuel() {
		return fuel;
	}

	public int getFuelCapacity() {
		return fuelCapacity;
	}

	@Override
	public void update() {
		if (!getWorld().isRemote) {

			if (isBurning() && canSmelt()) {
				progress++;
				if (progress >= MAX_PROGRESS) {
					progress = 0;
					smeltItems();
				}
			}

			if (isBurning())
				fuel--;

			if (!isBurning() && canSmelt()) {
				burnFuel();
			}
		}
	}

	private boolean canSmelt() {
		if (slots[0] == null && slots[1] == null)
			return false;

		ItemStack result = MetalFurnaceRecipes.instance().getSmeltingResult(slots[0], slots[1]);
		if (result == null)
			return false;
		if (slots[3] == null)
			return true;
		if (!slots[3].isItemEqual(result))
			return false;
		int resultSize = slots[3].stackSize + result.stackSize;
		return resultSize <= getInventoryStackLimit() && resultSize <= slots[3].getMaxStackSize();
	}

	private void smeltItems() {
		ItemStack result = MetalFurnaceRecipes.instance().smelt(slots[0], slots[1]);
		if (slots[0] != null && slots[0].stackSize <= 0)
			slots[0] = null;
		if (slots[1] != null && slots[1].stackSize <= 0)
			slots[1] = null;

		if (slots[3] == null)
			slots[3] = result;
		else {
			slots[3].stackSize += result.stackSize;
		}
	}

	private boolean isBurning() {
		return fuel > 0;
	}

	private void burnFuel() {
		ItemStack stack = slots[2];
		if (stack != null && stack.getItem() == Items.coal && stack.getMetadata() == 0) {
			stack.stackSize--;
			if (stack.stackSize <= 0)
				slots[2] = null;
			fuel = 800;
			fuelCapacity = 800;
		}
	}

	@Override
	public String getName() {
		return hasCustomName() ? getCustomName() : "container.metalFurnace";
	}

	@Override
	public IChatComponent getDisplayName() {
		return this.hasCustomName() ? new ChatComponentText(this.getName())
				: new ChatComponentTranslation(this.getName(), new Object[0]);
	}

	@Override
	public int getSizeInventory() {
		return 4;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return slots[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (slots[index] == null)
			return null;

		if (slots[index].stackSize <= count) {
			ItemStack stack = slots[index];
			slots[index] = null;
			return stack;
		} else {
			return slots[index].splitStack(count);
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		if (slots[index] != null) {
			ItemStack stack = slots[index];
			slots[index] = null;
			return stack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		slots[index] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index < 2)
			return stack.getItem() == Item.getItemFromBlock(Blocks.iron_ore)
					|| stack.getItem() == Item.getItemFromBlock(Blocks.gold_ore);
		else if (index < 3)
			return stack.getItem() == Items.coal && stack.getMetadata() == 0;
		else
			return false;
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case FIELD_PROGRESS:
				return progress;
			case FIELD_FUEL:
				return fuel;
			case FIELD_FUELCAPACITY:
				return fuelCapacity;
			default:
				return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
			case FIELD_PROGRESS:
				progress = value;
				break;
			case FIELD_FUEL:
				fuel = value;
				break;
			case FIELD_FUELCAPACITY:
				fuelCapacity = value;
				break;
		}
	}

	@Override
	public int getFieldCount() {
		return 3;
	}

	@Override
	public void clear() {
	}

	public Container getContainer(InventoryPlayer playerInv) {
		return new GenericContainer()
				.addInputSlots(this, 0, 38, 17, 2, 1)
				.addInputSlots(this, 2, 47, 53, 1, 1)
				.addOutputSlots(this, 3, 116, 35, 1, 1)
				.addPlayerSlots(playerInv)
				.setUpdateHandler(this);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagList slotsNbt = new NBTTagList();
		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null) {
				NBTTagCompound slotNbt = new NBTTagCompound();
				slotNbt.setByte("Slot", (byte) i);
				slots[i].writeToNBT(slotNbt);
				slotsNbt.appendTag(slotNbt);
			}
		}
		compound.setTag("Slots", slotsNbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		NBTTagList slotsNbt = compound.getTagList("Slots", NBT.TAG_COMPOUND);
		for (int i = 0; i < slotsNbt.tagCount(); ++i) {
			NBTTagCompound slotNbt = slotsNbt.getCompoundTagAt(i);
			byte slot = slotNbt.getByte("Slot");
			if (0 <= slot && slot < slots.length) {
				slots[slot] = ItemStack.loadItemStackFromNBT(slotNbt);
			}
		}
	}

}
