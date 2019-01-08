package com.hea3ven.tools.commonutils.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.DefaultedList;

public class InventoryExtraUtil {

    public static void serialize(CompoundTag tag, DefaultedList<ItemStack> inventory, String tagName) {
        ListTag list = new ListTag();
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.get(i);
            if (!stack.isEmpty()) {
                CompoundTag slotTag = new CompoundTag();
                slotTag.putByte("Slot", (byte) i);
                stack.toTag(slotTag);
                list.add(slotTag);
            }
        }
        tag.put(tagName, list);
    }

    public static void deserialize(CompoundTag tag, DefaultedList<ItemStack> inventory, String tagName) {
        ListTag list = tag.getList(tagName, 10);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag slotTag = list.getCompoundTag(i);
            int slot = slotTag.getByte("Slot");
            if (slot >= 0 && slot < inventory.size()) {
                inventory.set(slot, ItemStack.fromTag(slotTag));
            }
        }
    }
}
