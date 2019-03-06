package com.hea3ven.dulcedeleche.modules.redstone.dispenser

import com.hea3ven.dulcedeleche.DulceDeLecheMod
import com.hea3ven.tools.commonutils.util.ItemStackUtil
import net.minecraft.block.DispenserBlock
import net.minecraft.block.dispenser.DispenserBehavior
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPointer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class DispenserPlantBehavior : DispenserBehavior {
    override fun dispense(source: BlockPointer, stack: ItemStack): ItemStack {
        val direction: Direction = source.blockState.get(DispenserBlock.FACING)
        val position = source.blockPos.offset(direction)

        val offset = if (source.world.isAir(position)) 1 else 0
        val pos = BlockPos(position.x, position.y - offset, position.z)
        val player = DulceDeLecheMod.mod.getFakePlayer(source.world)
        player.setEquippedStack(EquipmentSlot.HAND_MAIN, stack)
        ItemStackUtil.useItem(player, stack, pos, Direction.UP)
        player.setEquippedStack(EquipmentSlot.HAND_MAIN, ItemStack.EMPTY)
        return stack
    }

}