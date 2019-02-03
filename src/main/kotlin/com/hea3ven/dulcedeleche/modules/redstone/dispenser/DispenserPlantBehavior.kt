package com.hea3ven.dulcedeleche.modules.redstone.dispenser

import com.hea3ven.dulcedeleche.ModDulceDeLeche
import net.minecraft.block.DispenserBlock
import net.minecraft.block.dispenser.DispenserBehavior
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPointer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d

class DispenserPlantBehavior : DispenserBehavior {
    override fun dispense(source: BlockPointer, stack: ItemStack): ItemStack {
        val direction: Direction = source.blockState.get(DispenserBlock.FACING)
        val position = source.blockPos.offset(direction)

        val offset = if (source.world.isAir(position)) 1 else 0
        val pos = BlockPos(position.x, position.y - offset, position.z)
        val player = ModDulceDeLeche.getFakePlayer(source.world)
        player.setEquippedStack(EquipmentSlot.HAND_MAIN, stack)
        stack.useOnBlock(ItemUsageContext(player, stack, BlockHitResult(Vec3d(0.5, 1.0, 0.5), Direction.UP, pos, true)))
        player.setEquippedStack(EquipmentSlot.HAND_MAIN, ItemStack.EMPTY)
        return stack
    }

}