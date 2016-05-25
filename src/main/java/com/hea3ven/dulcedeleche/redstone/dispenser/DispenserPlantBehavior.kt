package com.hea3ven.dulcedeleche.redstone.dispenser

import com.hea3ven.dulcedeleche.ModDulceDeLeche
import com.hea3ven.tools.commonutils.util.ItemStackUtil
import net.minecraft.block.BlockDispenser
import net.minecraft.dispenser.IBehaviorDispenseItem
import net.minecraft.dispenser.IBlockSource
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraftforge.common.IPlantable

class DispenserPlantBehavior : IBehaviorDispenseItem {
	override fun dispense(source: IBlockSource, stack: ItemStack): ItemStack {
		val position = BlockDispenser.getDispensePosition(source)
		val offset = if (source.world.isAirBlock(BlockPos(position.x, position.y, position.z))) 1 else 0
		val pos = BlockPos(position.x, position.y - offset, position.z)
		val state = source.world.getBlockState(pos)
		if (state.block.canSustainPlant(state, source.world, pos, EnumFacing.UP, stack.item as IPlantable)
				&& source.world.isAirBlock(pos.up())) {
			val player = ModDulceDeLeche.proxy.getFakePlayer(source.world)
			ItemStackUtil.useItem(source.world, player, stack, pos, EnumHand.MAIN_HAND, EnumFacing.UP)
		}
		return stack;
	}

}