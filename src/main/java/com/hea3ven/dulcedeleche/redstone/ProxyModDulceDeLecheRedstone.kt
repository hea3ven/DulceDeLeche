package com.hea3ven.dulcedeleche.redstone

import com.hea3ven.dulcedeleche.ModDulceDeLeche
import com.hea3ven.dulcedeleche.redstone.block.BlockAssembler
import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler
import com.hea3ven.dulcedeleche.redstone.client.gui.GuiAssembler
import com.hea3ven.tools.commonutils.inventory.ISimpleGuiHandler
import com.hea3ven.tools.commonutils.mod.ProxyModModule
import com.hea3ven.tools.commonutils.util.WorldHelper
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ProxyModDulceDeLecheRedstone : ProxyModModule() {

	private var assembler = BlockAssembler().apply {
		unlocalizedName = "assembler"
		setHardness(3.5F)
		//			soundType SoundType.STONE
		setCreativeTab(CreativeTabs.REDSTONE);
	}

	override fun registerBlocks() {
		addBlock(assembler, "assembler")
	}

	override fun registerTileEntities() {
		addTileEntity(TileAssembler::class.java, "assembler")
	}

	override fun registerGuis() {
		addGui(ModDulceDeLeche.guiIdAssembler, object : ISimpleGuiHandler {
			override fun createContainer(player: EntityPlayer, world: World, pos: BlockPos) =
					WorldHelper.getTile<TileAssembler>(world, pos).getContainer(player.inventory)

			override fun createGui(player: EntityPlayer, world: World, pos: BlockPos) =
					GuiAssembler(player.inventory,
							WorldHelper.getTile(world, pos))
		})
	}

	override fun registerRecipes() {
		addRecipe(assembler, "xxx", "xyx", "xxx", 'x', "cobblestone", 'y', Blocks.CRAFTING_TABLE)
	}
}
