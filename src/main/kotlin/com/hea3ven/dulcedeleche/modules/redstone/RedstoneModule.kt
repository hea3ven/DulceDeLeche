package com.hea3ven.dulcedeleche.modules.redstone

import com.hea3ven.dulcedeleche.Module
import com.hea3ven.dulcedeleche.modules.redstone.block.AssemblerBlock
import com.hea3ven.dulcedeleche.modules.redstone.block.WorkbenchBlock
import com.hea3ven.dulcedeleche.modules.redstone.block.entity.AssemblerBlockEntity
import com.hea3ven.dulcedeleche.modules.redstone.block.entity.WorkbenchBlockEntity
import com.hea3ven.dulcedeleche.modules.redstone.client.gui.AssemblerScreen
import com.hea3ven.dulcedeleche.modules.redstone.client.gui.WorkbenchScreen
import com.hea3ven.dulcedeleche.modules.redstone.dispenser.DispenserBreedBehavior
import com.hea3ven.dulcedeleche.modules.redstone.dispenser.DispenserPlantBehavior
import net.fabricmc.fabric.api.client.screen.ContainerScreenFactory
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry
import net.fabricmc.fabric.api.container.ContainerFactory
import net.fabricmc.fabric.api.container.ContainerProviderRegistry
import net.minecraft.block.Block
import net.minecraft.block.DispenserBlock
import net.minecraft.block.Material
import net.minecraft.block.PlantBlock
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.container.Container
import net.minecraft.item.*
import net.minecraft.item.block.BlockItem
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object RedstoneModule : Module<RedstoneModuleConfig>() {
    override fun createDefaultConfig() = RedstoneModuleConfig(true, true)

    lateinit var workbenchBlockEntityType: BlockEntityType<WorkbenchBlockEntity>
        private set

    lateinit var assemblerBlockEntityType: BlockEntityType<AssemblerBlockEntity>
        private set

    var workbenchId: Identifier = Identifier("dulcedeleche", "workbench")
    var assemblerId: Identifier = Identifier("dulcedeleche", "assembler")

    override fun onInitialize() {
        if (cfg.dispenserPlantBehaviorEnabled) {
            logger.debug("Registering the planting dispenser behavior")
            // TODO: more generic filter
            for (plant in Registry.ITEM.filter { it is SeedsItem || (it is BlockItem && it.block is PlantBlock) }) {
                DispenserBlock.registerBehavior(plant, DispenserPlantBehavior())
            }
        }
        if (cfg.dispenserBreedingBehaviorEnabled) {
            logger.debug("Registering the breeding dispenser behavior")
            val breedingItems = setOf(Items.WHEAT, Items.CARROT, Items.POTATO, Items.BEETROOT,
                                      Items.GOLDEN_CARROT).union(
                    Registry.ITEM.filter { it is FoodItem && it.isWolfFood })
            for (breedItem in breedingItems) DispenserBlock.registerBehavior(breedItem, DispenserBreedBehavior())
        }
        val workbenchItem = Registry.register(Registry.ITEM, workbenchId, BlockItem(
                Registry.register(Registry.BLOCK, workbenchId, WorkbenchBlock(Block.Settings.of(Material.STONE))),
                Item.Settings().itemGroup(ItemGroup.REDSTONE)))
        workbenchItem.registerBlockItemMap(Item.BLOCK_ITEM_MAP, workbenchItem)
        ContainerProviderRegistry.INSTANCE.registerFactory(workbenchId, ContainerFactory<Container>(
                WorkbenchBlockEntity.Companion::createContainer))
        ScreenProviderRegistry.INSTANCE.registerFactory(workbenchId, ContainerScreenFactory(::WorkbenchScreen))
        workbenchBlockEntityType = Registry.register(Registry.BLOCK_ENTITY, workbenchId, BlockEntityType(
                { WorkbenchBlockEntity(RedstoneModule.workbenchBlockEntityType) }, null))

        val assembler = Registry.register(Registry.BLOCK, assemblerId,
                                          AssemblerBlock(Block.Settings.of(Material.STONE)))
        val assemblerItem = Registry.register(Registry.ITEM, assemblerId,
                                              BlockItem(assembler, Item.Settings().itemGroup(ItemGroup.REDSTONE)))
        assemblerItem.registerBlockItemMap(Item.BLOCK_ITEM_MAP, assemblerItem)
        ContainerProviderRegistry.INSTANCE.registerFactory(assemblerId, ContainerFactory<Container>(
                AssemblerBlockEntity.Companion::createContainer))
        ScreenProviderRegistry.INSTANCE.registerFactory(assemblerId, ContainerScreenFactory(::AssemblerScreen))
        assemblerBlockEntityType = Registry.register(Registry.BLOCK_ENTITY, assemblerId, BlockEntityType(
                { AssemblerBlockEntity(RedstoneModule.assemblerBlockEntityType) }, null))
    }

}

