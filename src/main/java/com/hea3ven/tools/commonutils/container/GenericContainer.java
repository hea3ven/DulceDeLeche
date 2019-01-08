package com.hea3ven.tools.commonutils.container;

import java.util.Collections;
import java.util.Set;

import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class GenericContainer extends ContainerBase {

    public static final int PLAYER_INV_SIZE = 27;
    public static final int PLAYER_INV_FULL_SIZE = PLAYER_INV_SIZE + 9;

    private int playerSlotsStart = -1;

    private PlayerInventory playerInv = null;

    public GenericContainer(int syncId) {
        super(syncId);
    }

    public final GenericContainer addGenericSlots(Inventory inv, int slotOff, int xOff, int yOff,
            int xSize, int ySize) {
        super.addSlots(inv, slotOff, xOff, yOff, xSize, ySize);
        return this;
    }

    public final GenericContainer addGenericSlots(int xOff, int yOff, int xSize, int ySize,
            SlotSupplier slotSupplier) {
        super.addSlots(xOff, yOff, xSize, ySize, slotSupplier);
        return this;
    }

    public final GenericContainer addOutputSlots(Inventory inv, int slotOff, int xOff, int yOff,
            int xSize, int ySize) {
        addSlots(xOff, yOff, xSize, ySize,
                (slot, x, y) -> new SlotOutput(inv, slotOff + slot, x, y));
        return this;
    }

    public final GenericContainer addPlayerSlots(PlayerInventory playerInv, int xOff, int yOff) {
        return addPlayerSlots(playerInv, xOff, yOff, Collections.emptySet());
    }

    public final GenericContainer addPlayerSlots(PlayerInventory playerInv, int xOff, int yOff,
            Set<Integer> lockedSlots) {
        playerSlotsStart = slotList.size();
        this.playerInv = playerInv;
        addSlots(playerInv, 9, xOff, yOff, 9, 3);
        addSlots(xOff, yOff + 3 * 18 + 4, 9, 1, (slot, x, y) -> {
            if (lockedSlots.contains(slot))
                return new SlotLocked(playerInv, slot, x, y);
            else
                return new Slot(playerInv, slot, x, y);
        });
        return this;
    }

    public PlayerInventory getPlayerInv() {
        return playerInv;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int int_1) {
        ItemStack resultStack = ItemStack.EMPTY;
        Slot slot = slotList.get(int_1);
        if (slot != null && slot.hasStack()) {
            ItemStack stack = slot.getStack();
            resultStack = stack.copy();
            if (playerSlotsStart <= int_1 && int_1 < playerSlotsStart + PLAYER_INV_SIZE) {
                if (!this.insertItem(stack, 0, playerSlotsStart - 1, false)) {
                    if (!this.insertItem(stack, playerSlotsStart + PLAYER_INV_SIZE,
                            playerSlotsStart + PLAYER_INV_FULL_SIZE - 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (playerSlotsStart + PLAYER_INV_SIZE <= int_1
                    && int_1 < playerSlotsStart + PLAYER_INV_FULL_SIZE) {
                if (!this.insertItem(stack, 0, playerSlotsStart + PLAYER_INV_SIZE - 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(stack, playerSlotsStart,
                    playerSlotsStart + PLAYER_INV_FULL_SIZE - 1, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (stack.getAmount() == resultStack.getAmount()) {
                return ItemStack.EMPTY;
            }

            ItemStack takeStack = slot.onTakeItem(player, stack);
            if (int_1 == 0) {
                player.dropItem(takeStack, false);
            }
        }

        return resultStack;
    }
}
