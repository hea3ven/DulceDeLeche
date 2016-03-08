package com.hea3ven.dulcedeleche.enchantments.enchantment

import net.minecraft.block.state.IBlockState
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.EnumEnchantmentType
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class EnchantmentArea(id: Int) :
		Enchantment(id, ResourceLocation("dulcedeleche:area"), 1, EnumEnchantmentType.DIGGER) {
	init {
		name = "area"
	}

	override fun getMaxLevel(): Int {
		return 2
	}

	override fun getMinEnchantability(enchantmentLevel: Int): Int {
		return if (enchantmentLevel == 1) 15 else 100
	}

	override fun getMaxEnchantability(enchantmentLevel: Int): Int {
		return super.getMaxEnchantability(enchantmentLevel) + 50
	}

	@SubscribeEvent
	fun onBlockBreakEvent(event: BlockEvent.BreakEvent) {
		if (event is AreaBlockBreakEvent)
			return

		val area = getAreaModifier(event.player)
		if ( area > 0) {
			val trace = event.player.rayTrace(0.5, 1.0f)
			if (trace.blockPos == event.pos) {
				val exp = areaBreak(event.world, event.player, event.pos, trace.sideHit, area)
				event.expToDrop = event.expToDrop + exp
			}
		}
	}

	private fun getAreaModifier(player: EntityPlayer) = EnchantmentHelper.getEnchantmentLevel(effectId,
			player.heldItem)

	private fun areaBreak(world: World, player: EntityPlayer, pos: BlockPos, face: EnumFacing,
			area: Int): Int {
		val dirs = EnumFacing.Axis.values().filter { it != face.axis }.map {
			face.rotateAround(it).directionVec!!
		}
		assert(dirs.size == 2)

		var exp = 0
		for (i in area..-area) {
			for (j in area..-area) {
				val breakPos = pos.add(dirs[0].x * i, dirs[0].y * i, dirs[0].z * i)
						.add(dirs[1].x * j, dirs[1].y * j, dirs[1].z * j)
				val breakExp = breakBlock(world, player, breakPos)
				if (breakExp != -1)
					exp += breakExp
			}
		}
		return exp
	}

	private fun breakBlock(world: World, player: EntityPlayer, pos: BlockPos): Int {
		val state = world.getBlockState(pos)

		val canHarvest = state.block.canHarvestBlock(world, pos, player)
		if (!canHarvest)
			return -1

		val stack = player.currentEquippedItem
		if (stack != null) {
			stack.onBlockDestroyed(world, state.block, pos, player)
			if (stack.stackSize == 0)
				player.destroyCurrentEquippedItem()
		}

		val subEvent = AreaBlockBreakEvent(world, pos, state, player)
		MinecraftForge.EVENT_BUS.post(subEvent)
		if (!subEvent.isCanceled) {
			val removed = removeBlock(world, player, pos, canHarvest)
			if (removed)
				state.block.harvestBlock(world, player, pos, state, world.getTileEntity(pos))
			return subEvent.expToDrop
		}
		return -1
	}

	private fun removeBlock(world: World, player: EntityPlayer, pos: BlockPos, canHarvest: Boolean): Boolean {
		val state = world.getBlockState(pos)
		state.block.onBlockHarvested(world, pos, state, player)
		if (state.block.removedByPlayer(world, pos, player, canHarvest)) {
			state.block.onBlockDestroyedByPlayer(world, pos, state)
			return true
		}
		return false
	}

	class AreaBlockBreakEvent(world: World?, pos: BlockPos?, state: IBlockState?, player: EntityPlayer?) :
			BlockEvent.BreakEvent(world, pos, state, player) {

	}

	companion object {
		lateinit var instance : EnchantmentArea
	}
}
