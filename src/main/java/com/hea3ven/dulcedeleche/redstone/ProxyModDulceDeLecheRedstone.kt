package com.hea3ven.dulcedeleche.redstone

import com.hea3ven.dulcedeleche.ModDulceDeLeche
import com.hea3ven.dulcedeleche.redstone.block.BlockAssembler
import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler
import com.hea3ven.dulcedeleche.redstone.client.gui.GuiAssembler
import com.hea3ven.dulcedeleche.redstone.dispenser.DispenserBreedBehavior
import com.hea3ven.dulcedeleche.redstone.dispenser.DispenserPlantBehavior
import com.hea3ven.tools.commonutils.inventory.ISimpleGuiHandler
import com.hea3ven.tools.commonutils.mod.ProxyModModule
import com.hea3ven.tools.commonutils.mod.config.FileConfigManagerBuilder
import com.hea3ven.tools.commonutils.util.WorldHelper
import net.minecraft.block.BlockDispenser
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemFood
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.config.Property
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Suppress("unused")
class ProxyModDulceDeLecheRedstone : ProxyModModule() {

	private var enableDispenserPlantBehaviour = true
	private var enableDispenserBreedBehaviour = true
	private var enableAssembler = true

	private var assembler = BlockAssembler().apply {
		unlocalizedName = "assembler"
		setHardness(3.5F)
		//			soundType SoundType.STONE
		setCreativeTab(CreativeTabs.REDSTONE);
	}

	override fun onInitEvent(event: FMLInitializationEvent?) {
		super.onInitEvent(event)

		if (enableDispenserPlantBehaviour) {
			for (plant in Item.REGISTRY.filter { it is IPlantable }) {
				BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(plant, DispenserPlantBehavior())
			}
		}
		if (enableDispenserBreedBehaviour) {
			val breedingItems = setOf(Items.WHEAT, Items.CARROT, Items.POTATO, Items.BEETROOT,
					Items.GOLDEN_CARROT, Item.getItemFromBlock(Blocks.YELLOW_FLOWER), Items.WHEAT_SEEDS,
					Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS)
					.union(Item.REGISTRY.filter { it is ItemFood && it.isWolfsFavoriteMeat })
			for (breedItem in breedingItems)
				BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(breedItem, DispenserBreedBehavior())
		}
	}

	override fun getConfig(): FileConfigManagerBuilder.CategoryConfigManagerBuilder {
		return FileConfigManagerBuilder.CategoryConfigManagerBuilder("redstone")
				.addSubCategory("dispencerBehaviours")
				.addValue("plantSeeds", "true", Property.Type.BOOLEAN,
						"Enable to make dispensers plant seeds",
						{ enableDispenserPlantBehaviour = it.boolean }, true, true)
				.addValue("breedAnimals", "true", Property.Type.BOOLEAN,
						"Enable to make dispensers be able to feed animals",
						{ enableDispenserBreedBehaviour = it.boolean }, true, true)
				.endSubCategory()
				.addValue("enableAssembler", "true", Property.Type.BOOLEAN, "Enable the assembler block",
						{ enableAssembler = it.boolean }, true, true)
	}

	override fun registerBlocks() {
		if (enableAssembler)
			addBlock(assembler, "assembler")
	}

	override fun registerTileEntities() {
		if (enableAssembler)
			addTileEntity(TileAssembler::class.java, "assembler")
	}

	override fun registerGuis() {
		if (enableAssembler) {
			addGui(ModDulceDeLeche.guiIdAssembler, object : ISimpleGuiHandler {
				override fun createContainer(player: EntityPlayer, world: World, pos: BlockPos) =
						WorldHelper.getTile<TileAssembler>(world, pos).getContainer(player.inventory)

				override fun createGui(player: EntityPlayer, world: World, pos: BlockPos) =
						GuiAssembler(player.inventory,
								WorldHelper.getTile(world, pos))
			})
		}
	}

	override fun registerRecipes() {
		if (enableAssembler)
			addRecipe(assembler, "xxx", "xyx", "xxx", 'x', "cobblestone", 'y', Blocks.CRAFTING_TABLE)
	}
}
