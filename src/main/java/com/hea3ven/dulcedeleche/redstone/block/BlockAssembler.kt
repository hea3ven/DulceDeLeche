package com.hea3ven.dulcedeleche.redstone.block

import com.hea3ven.dulcedeleche.ModDulceDeLeche
import com.hea3ven.dulcedeleche.redstone.block.tileentity.TileAssembler
import com.hea3ven.dulcedeleche.redstone.client.gui.GuiAssembler
import com.hea3ven.tools.commonutils.block.base.BlockMachine
import net.minecraft.block.material.Material
import net.minecraft.world.World

class BlockAssembler : BlockMachine(Material.rock, ModDulceDeLeche.MODID, GuiAssembler.id) {

	override fun createNewTileEntity(worldIn: World?, meta: Int) = TileAssembler()

}
