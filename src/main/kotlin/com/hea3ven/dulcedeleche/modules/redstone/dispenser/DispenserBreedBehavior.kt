package com.hea3ven.dulcedeleche.modules.redstone.dispenser

import com.hea3ven.dulcedeleche.DulceDeLecheMod
import net.minecraft.block.DispenserBlock
import net.minecraft.block.dispenser.DispenserBehavior
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPointer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BoundingBox

class DispenserBreedBehavior : DispenserBehavior {
    override fun dispense(source: BlockPointer, stack: ItemStack): ItemStack {
        val var3 = source.blockState.get(DispenserBlock.FACING)
        val position = source.blockPos.offset(var3)
        val pos = BlockPos(position.x, position.y, position.z)
        val box = BoundingBox(pos, pos.add(1, 1, 1))
        val entities = source.world.getEntities(null, box, { it is AnimalEntity }).map { it as AnimalEntity }
                .toMutableList()

        if (entities.size <= 0) return stack

        val player = DulceDeLecheMod.mod.getFakePlayer(source.world)

        player.setEquippedStack(EquipmentSlot.HAND_MAIN, stack)

        entities.shuffle()
        while (entities.size > 0) {
            val entity = entities[0]
            entities.remove(entity)

            if (!entity.isBreedingItem(stack)) {
                continue
            }

            if (player.interact(entity, Hand.MAIN) == ActionResult.SUCCESS) {
                break
            }
        }

        player.setEquippedStack(EquipmentSlot.HAND_MAIN, ItemStack.EMPTY)

        return stack
    }
}
