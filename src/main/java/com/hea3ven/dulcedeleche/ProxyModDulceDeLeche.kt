package com.hea3ven.dulcedeleche

import com.hea3ven.dulcedeleche.enchantments.enchantment.EnchantmentArea
import com.hea3ven.dulcedeleche.redstone.block.BlockAssembler
import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler
import com.hea3ven.dulcedeleche.redstone.client.gui.GuiAssembler
import com.hea3ven.tools.commonutils.inventory.ISimpleGuiHandler
import com.hea3ven.tools.commonutils.mod.ProxyModBase
import com.hea3ven.tools.commonutils.util.WorldHelper
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.util.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent

class ProxyModDulceDeLeche : ProxyModBase(ModDulceDeLeche.MODID) {

	private lateinit var assembler: Block

	override fun onPostInitEvent(event: FMLPostInitializationEvent?) {
		super.onPostInitEvent(event)

		MinecraftForge.EVENT_BUS.register(
				EnchantmentArea.instance);
	}

	override fun registerBlocks() {
		assembler = addBlock(BlockAssembler().apply {
			setUnlocalizedName("assembler")
			setHardness(3.5F)
			setStepSound(Block.soundTypePiston)
			setCreativeTab(CreativeTabs.tabRedstone);
		}, "assembler")
	}

	override fun registerTileEntities() {
		addTileEntity(TileAssembler::class.java, "assembler")
	}

	override fun registerGuis() {
		addGui(GuiAssembler.id, object : ISimpleGuiHandler {
			override fun createContainer(player: EntityPlayer, world: World, pos: BlockPos) =
					WorldHelper.getTile<TileAssembler>(world, pos).getContainer(player.inventory)

			override fun createGui(player: EntityPlayer, world: World, pos: BlockPos) =
					GuiAssembler(player.inventory,
							WorldHelper.getTile(world, pos))
		})
	}

	override fun registerEnchantments() {
		EnchantmentArea.instance = EnchantmentArea(100)
	}

	override fun registerRecipes() {
		addRecipe(assembler, "xxx", "xyx", "xxx", 'x', "cobblestone", 'y', Blocks.crafting_table)
	}
}