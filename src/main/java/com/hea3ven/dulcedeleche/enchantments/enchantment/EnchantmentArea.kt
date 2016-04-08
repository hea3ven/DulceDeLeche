package com.hea3ven.dulcedeleche.enchantments.enchantment

import net.minecraft.block.state.IBlockState
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.EnumEnchantmentType
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class EnchantmentArea : Enchantment(Rarity.VERY_RARE, EnumEnchantmentType.DIGGER,
		arrayOf(EntityEquipmentSlot.MAINHAND)) {
	init {
		name = "area"
	}

	override fun getMaxLevel(): Int {
		return 2
	}

	override fun getMinEnchantability(enchantmentLevel: Int): Int {
		return 20
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
			val trace = event.player.rayTrace(5.0, 1.0f)
			if (trace.blockPos == event.pos) {
				val exp = areaBreak(event.world, event.player, event.pos, trace.sideHit, area)
				event.expToDrop = event.expToDrop + exp
			}
		}
	}

	private fun getAreaModifier(player: EntityPlayer): Int {
		return player.heldEquipment.map { EnchantmentHelper.getEnchantmentLevel(this, it) }.max() ?: 0
	}

	private fun areaBreak(world: World, player: EntityPlayer, pos: BlockPos, face: EnumFacing,
			area: Int): Int {
		val dirs = EnumFacing.Axis.values().filter { it != face.axis }.map {
			face.rotateAround(it).directionVec!!
		}
		assert(dirs.size == 2)

		var exp = 0
		for (i in area downTo -area) {
			for (j in area downTo -area) {
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

		val stack = player.activeItemStack
		if (stack != null) {
			stack.onBlockDestroyed(world, state, pos, player)
			if (stack.stackSize == 0)
				player.setHeldItem(player.activeHand, null)
		}

		val subEvent = AreaBlockBreakEvent(world, pos, state, player)
		MinecraftForge.EVENT_BUS.post(subEvent)
		if (!subEvent.isCanceled) {
			val removed = removeBlock(world, player, pos, canHarvest)
			if (removed)
				state.block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), stack)
			return subEvent.expToDrop
		}
		return -1
	}

	private fun removeBlock(world: World, player: EntityPlayer, pos: BlockPos, canHarvest: Boolean): Boolean {
		val state = world.getBlockState(pos)
		state.block.onBlockHarvested(world, pos, state, player)
		if (state.block.removedByPlayer(state, world, pos, player, canHarvest)) {
			state.block.onBlockDestroyedByPlayer(world, pos, state)
			return true
		}
		return false
	}

	class AreaBlockBreakEvent(world: World?, pos: BlockPos?, state: IBlockState?, player: EntityPlayer?) :
			BlockEvent.BreakEvent(world, pos, state, player) {

	}
}
