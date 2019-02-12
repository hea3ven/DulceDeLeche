package com.hea3ven.dulcedeleche.modules.redstone

import com.hea3ven.dulcedeleche.ModDulceDeLeche
import com.hea3ven.dulcedeleche.fabric.DulceDeLecheFabricModInitializer
import com.hea3ven.dulcedeleche.modules.food.item.ItemBucketDulceDeLeche
import com.hea3ven.dulcedeleche.modules.redstone.block.AssemblerBlock
import com.hea3ven.dulcedeleche.modules.redstone.block.WorkbenchBlock
import com.hea3ven.dulcedeleche.modules.redstone.block.entity.AssemblerBlockEntity
import com.hea3ven.dulcedeleche.modules.redstone.block.entity.WorkbenchBlockEntity
import com.hea3ven.dulcedeleche.modules.redstone.dispenser.DispenserBreedBehavior
import com.hea3ven.dulcedeleche.modules.redstone.dispenser.DispenserPlantBehavior
import com.hea3ven.tools.commonutils.mod.ModModule
import net.fabricmc.fabric.api.container.ContainerFactory
import net.minecraft.block.Block
import net.minecraft.block.DispenserBlock
import net.minecraft.block.Material
import net.minecraft.block.PlantBlock
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.container.Container
import net.minecraft.item.FoodItem
import net.minecraft.item.ItemGroup
import net.minecraft.item.Items
import net.minecraft.item.SeedsItem
import net.minecraft.item.block.BlockItem
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.Supplier

object RedstoneModule : ModModule() {

    lateinit var WorkbenchBlockEntityType: BlockEntityType<WorkbenchBlockEntity>
    lateinit var AssemblerBlockEntityType: BlockEntityType<AssemblerBlockEntity>

    override fun onPreInit() {
        Registry.register(Registry.ITEM, Identifier("dulcedeleche", "dulcedeleche"), ItemBucketDulceDeLeche());
//        val block = WorkbenchBlock(id("workbench"), Block.Settings.of(Material.STONE))
//        addBlock<WorkbenchBlockEntity>("workbench", block,
//                                       ItemGroup.REDSTONE, { blockEntityTypeSupplier ->
//                                           Supplier({ WorkbenchBlockEntity(blockEntityTypeSupplier.get()) })
//                                       })
//        addContainer("workbench", ContainerFactory<Container>(WorkbenchBlockEntity.Companion::createContainer));
//        addBlock<AssemblerBlockEntity>("assembler", AssemblerBlock(id("assembler"), Block.Settings.of(Material.STONE)),
//                                       ItemGroup.REDSTONE, { blockEntityTypeSupplier ->
//                                           Supplier({ AssemblerBlockEntity(blockEntityTypeSupplier.get()) })
//                                       })
//        addContainer("assembler", ContainerFactory<Container>(AssemblerBlockEntity.Companion::createContainer));
//
//        WorkbenchBlockEntityType = blocks["workbench"]!!.blockEntityType as BlockEntityType<WorkbenchBlockEntity>
//        AssemblerBlockEntityType = blocks["assembler"]!!.blockEntityType as BlockEntityType<AssemblerBlockEntity>

    }

    override fun onInit() {
        if (ModDulceDeLeche.cfg.modules.redstone.dispenserPlantBehaviorEnabled) {
            logger.debug("Registering the planting dispenser behavior")
            // TODO: more generic filter
            for (plant in Registry.ITEM.filter { it is SeedsItem || (it is BlockItem && it.block is PlantBlock) }) {
                DispenserBlock.registerBehavior(plant, DispenserPlantBehavior())
            }
        }
        if (ModDulceDeLeche.cfg.modules.redstone.dispenserBreedingBehaviorEnabled) {
            logger.debug("Registering the breeding dispenser behavior")
            val breedingItems = setOf(Items.WHEAT, Items.CARROT, Items.POTATO, Items.BEETROOT,
                                      Items.GOLDEN_CARROT).union(
                    Registry.ITEM.filter { it is FoodItem && it.isWolfFood })
            for (breedItem in breedingItems) DispenserBlock.registerBehavior(breedItem, DispenserBreedBehavior())
        }

    }

}

