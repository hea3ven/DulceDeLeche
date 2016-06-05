package com.hea3ven.dulcedeleche.redstone.dispenser

import com.hea3ven.dulcedeleche.ModDulceDeLeche
import net.minecraft.block.BlockDispenser
import net.minecraft.dispenser.IBehaviorDispenseItem
import net.minecraft.dispenser.IBlockSource
import net.minecraft.entity.passive.EntityAnimal
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import java.util.*

class DispenserBreedBehavior : IBehaviorDispenseItem {
	override fun dispense(source: IBlockSource, stack: ItemStack): ItemStack {
		val position = BlockDispenser.getDispensePosition(source)
		val pos = BlockPos(position.x, position.y, position.z)
		val box = AxisAlignedBB(pos, pos.add(1, 1, 1));
		val entities = source.world.getEntitiesWithinAABBExcludingEntity(null, box)
				.filter { it is EntityAnimal }
				.map { it as EntityAnimal }
				.toMutableList()

		if (entities.size <= 0)
			return stack

		val player = ModDulceDeLeche.proxy.getFakePlayer(source.world)
		player.setHeldItem(EnumHand.MAIN_HAND, stack)

		Collections.shuffle(entities)
		while (entities.size > 0) {
			val entity = entities[0]
			entities.remove(entity)

			if (!entity.isBreedingItem(stack))
				continue

			if (player.interact(entity, stack, EnumHand.MAIN_HAND) != EnumActionResult.SUCCESS)
				continue
		}

		player.setHeldItem(EnumHand.MAIN_HAND, null)

		return stack
	}
}
