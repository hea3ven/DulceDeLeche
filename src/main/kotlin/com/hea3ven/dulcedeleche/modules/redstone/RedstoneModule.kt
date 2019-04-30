package com.hea3ven.dulcedeleche.modules.redstone

import com.hea3ven.dulcedeleche.DulceDeLecheMod
import com.hea3ven.dulcedeleche.modules.redstone.block.AssemblerBlock
import com.hea3ven.dulcedeleche.modules.redstone.block.WorkbenchBlock
import com.hea3ven.dulcedeleche.modules.redstone.block.entity.AssemblerBlockEntity
import com.hea3ven.dulcedeleche.modules.redstone.block.entity.WorkbenchBlockEntity
import com.hea3ven.dulcedeleche.modules.redstone.container.AssemblerContainer
import com.hea3ven.dulcedeleche.modules.redstone.container.WorkbenchContainer
import com.hea3ven.dulcedeleche.modules.redstone.dispenser.DispenserBreedBehavior
import com.hea3ven.dulcedeleche.modules.redstone.dispenser.DispenserPlantBehavior
import com.hea3ven.tools.commonutils.mod.ModModule
import net.minecraft.block.Block
import net.minecraft.block.DispenserBlock
import net.minecraft.block.Material
import net.minecraft.block.PlantBlock
import net.minecraft.item.ItemGroup
import net.minecraft.item.Items
import net.minecraft.item.BlockItem
import net.minecraft.util.registry.Registry

abstract class RedstoneModule : ModModule() {

    override fun onPreInit() {
        addBlock<WorkbenchBlockEntity>("workbench", WorkbenchBlock(id("workbench"),
                                                                   Block.Settings.of(Material.WOOD).strength(2.5f,
                                                                                                             2.5f)),
                                       ItemGroup.REDSTONE, ::WorkbenchBlockEntity)
        addContainer("workbench", ::WorkbenchContainer)
        addBlock<AssemblerBlockEntity>("assembler", AssemblerBlock(id("assembler"),
                                                                   Block.Settings.of(Material.STONE).strength(2.5f,
                                                                                                              2.5f)),
                                       ItemGroup.REDSTONE, ::AssemblerBlockEntity)
        addContainer("assembler", ::AssemblerContainer)
    }

    override fun onInit() {
        if (DulceDeLecheMod.cfg.modules.redstone.dispenserPlantBehaviorEnabled) {
            logger.debug("Registering the planting dispenser behavior")
            // TODO: more generic filter
            for (plant in Registry.ITEM.filter { it is BlockItem && it.block is PlantBlock }) {
                DispenserBlock.registerBehavior(plant, DispenserPlantBehavior())
            }
        }
        if (DulceDeLecheMod.cfg.modules.redstone.dispenserBreedingBehaviorEnabled) {
            logger.debug("Registering the breeding dispenser behavior")
            val breedingItems = setOf(Items.WHEAT, Items.CARROT, Items.POTATO, Items.BEETROOT,
                                      Items.GOLDEN_CARROT).union(
                    Registry.ITEM.filter { it.foodSetting?.isWolfFood ?: false })
            for (breedItem in breedingItems) DispenserBlock.registerBehavior(breedItem, DispenserBreedBehavior())
        }
    }

}

