package com.hea3ven.dulcedeleche.modules.redstone.inventory

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

interface CraftingMachineInventory : Inventory {
    fun craftItem(player: PlayerEntity?): ItemStack
}
