package com.hea3ven.dulcedeleche.redstone

import com.hea3ven.dulcedeleche.redstone.dispenser.DispenserBreedBehavior
import com.hea3ven.dulcedeleche.redstone.dispenser.DispenserPlantBehavior
import net.minecraft.block.Blocks
import net.minecraft.block.DispenserBlock
import net.minecraft.block.PlantBlock
import net.minecraft.item.FoodItem
import net.minecraft.item.Items
import net.minecraft.item.SeedsItem
import net.minecraft.item.block.BlockItem
import net.minecraft.util.registry.Registry

object DulceDeLecheRedstone {

    fun onInitialize() {
        // TODO: more generic filter
        for (plant in Registry.ITEM.filter { it is SeedsItem || (it is BlockItem && it.block is PlantBlock) }) {
            DispenserBlock.registerBehavior(plant, DispenserPlantBehavior())
        }
        val breedingItems = setOf(Items.WHEAT, Items.CARROT, Items.POTATO, Items.BEETROOT, Items.GOLDEN_CARROT).union(
                Registry.ITEM.filter { it is FoodItem && it.isWolfFood })
        for (breedItem in breedingItems) DispenserBlock.registerBehavior(breedItem, DispenserBreedBehavior())
    }

}
