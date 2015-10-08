package com.hea3ven.dulcedeleche.redstone.block.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;

import net.minecraftforge.common.util.Constants.NBT;

import com.hea3ven.tools.commonutils.tileentity.TileMachine;

public class TileAssembler extends TileMachine implements ISidedInventory {

	private InventoryCrafting inv = new InventoryCrafting(new Container() {

		@Override
		public boolean canInteractWith(EntityPlayer playerIn) {
			return true;
		}

		public void onCraftMatrixChanged(net.minecraft.inventory.IInventory inventoryIn) {
			result = CraftingManager.getInstance().findMatchingRecipe(inv, getWorld());

		};
	}, 3, 3);

	private ItemStack result = null;
	private InventoryBasic extraOutput = new InventoryBasic("extraOutput", false, 4);

	public InventoryCrafting getCraftingInventory() {
		return inv;
	}

	public IInventory getExtraOutputInventory() {
		return extraOutput;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagList craftingNbt = new NBTTagList();
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			if (inv.getStackInSlot(i) != null) {
				NBTTagCompound slotNbt = new NBTTagCompound();
				slotNbt.setByte("Slot", (byte) i);
				inv.getStackInSlot(i).writeToNBT(slotNbt);
				craftingNbt.appendTag(slotNbt);
			}
		}
		compound.setTag("Crafting", craftingNbt);

		NBTTagList extraOutputNbt = new NBTTagList();
		for (int i = 0; i < extraOutput.getSizeInventory(); ++i) {
			if (extraOutput.getStackInSlot(i) != null) {
				NBTTagCompound slotNbt = new NBTTagCompound();
				slotNbt.setByte("Slot", (byte) i);
				extraOutput.getStackInSlot(i).writeToNBT(slotNbt);
				extraOutputNbt.appendTag(slotNbt);
			}
		}
		compound.setTag("ExtraOutput", extraOutputNbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		NBTTagList craftingNbt = compound.getTagList("Crafting", NBT.TAG_COMPOUND);
		for (int i = 0; i < craftingNbt.tagCount(); ++i) {
			NBTTagCompound slotNbt = craftingNbt.getCompoundTagAt(i);
			int j = slotNbt.getByte("Slot") & 255;

			if (j >= 0 && j < inv.getSizeInventory()) {
				inv.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(slotNbt));
			}
		}

		NBTTagList extraOutputNbt = compound.getTagList("ExtraOutput", NBT.TAG_COMPOUND);
		for (int i = 0; i < extraOutputNbt.tagCount(); ++i) {
			NBTTagCompound slotNbt = extraOutputNbt.getCompoundTagAt(i);
			int j = slotNbt.getByte("Slot") & 255;

			if (j >= 0 && j < extraOutput.getSizeInventory()) {
				extraOutput.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(slotNbt));
			}
		}
	}

	// Inventory implementation

	@Override
	public String getName() {
		return "container.assembler";
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentTranslation(getBlockType().getUnlocalizedName() + ".name",
				new Object[0]);
	}

	@Override
	public int getSizeInventory() {
		return 14;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < 9)
			return inv.getStackInSlot(index);
		else if (index < 10)
			return result;
		else
			return extraOutput.getStackInSlot(index - 10);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (index < 9)
			if (inv.getStackInSlot(index) != null
					&& inv.getStackInSlot(index).stackSize - count > 0)
				return inv.decrStackSize(index, count);
			else
				return null;
		else if (index < 10) {
			if (canCraft(count))
				return craftOutput(null);
			else
				return null;
		} else {
			markDirty();
			return extraOutput.decrStackSize(index - 10, count);
		}
	}

	public boolean canCraft(int amount) {
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			if (inv.getStackInSlot(i) != null && inv.getStackInSlot(i).stackSize < 1 + amount)
				return false;
		}
		return true;
	}

	public ItemStack craftOutput(EntityPlayer player) {
		ItemStack output = result;

		net.minecraftforge.common.ForgeHooks.setCraftingPlayer(player);
		ItemStack[] aitemstack = CraftingManager.getInstance().func_180303_b(inv, getWorld());
		net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);

		for (int i = 0; i < aitemstack.length; ++i) {
			ItemStack itemstack1 = inv.getStackInSlot(i);
			ItemStack itemstack2 = aitemstack[i];

			if (itemstack1 != null) {
				inv.decrStackSize(i, 1);
			}

			if (itemstack2 != null) {
				int size = extraOutput.getSizeInventory();
				for (int j = 0; j < size; j++) {
					if (extraOutput.getStackInSlot(j) == null) {
						extraOutput.setInventorySlotContents(j, itemstack2);
						break;
					}
				}
			}
		}

		markDirty();
		return output;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 9) {
			inv.setInventorySlotContents(index, stack);
			markDirty();
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
		if (index < 9)
			return inv.getStackInSlot(index) != null && inv.isItemValidForSlot(index, stack);
		else
			return false;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if (side != EnumFacing.DOWN)
			return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
		else
			return new int[] {9, 10, 11, 12, 13};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		if (direction != EnumFacing.DOWN)
			return true;
		else
			return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if (direction != EnumFacing.DOWN)
			return false;
		else
			return true;
	}

}
