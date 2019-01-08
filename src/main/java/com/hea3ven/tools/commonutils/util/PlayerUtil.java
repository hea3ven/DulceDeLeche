package com.hea3ven.tools.commonutils.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.EquipmentSlot.Type;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PlayerUtil {
	public static HeldEquipment getHeldEquipment(PlayerEntity player, Item item) {
		for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
			if(equipmentSlot.getType() != Type.HAND)
				continue;
			ItemStack stack = player.getEquippedStack(equipmentSlot);
			if (stack.getItem() == item)
				return new HeldEquipment(player, equipmentSlot);
		}
		return null;
	}

	public static class HeldEquipment {
		public PlayerEntity player;
		public EquipmentSlot hand;

		public HeldEquipment(PlayerEntity player, EquipmentSlot hand) {
			this.player = player;
			this.hand = hand;
		}

		public ItemStack getStack() {
			return player.getEquippedStack(hand);
		}
	}
}
