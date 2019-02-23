package com.hea3ven.dulcedeleche.modules.redstone.block.entity

import com.hea3ven.dulcedeleche.modules.redstone.container.AssemblerContainer
import com.hea3ven.tools.commonutils.container.GenericContainer
import com.hea3ven.tools.commonutils.util.ItemStackUtil
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.container.PropertyDelegate
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.text.TranslatableTextComponent
import net.minecraft.util.Identifier
import net.minecraft.util.PacketByteBuf
import net.minecraft.util.Tickable
import net.minecraft.util.math.Direction

class AssemblerBlockEntity(blockEntityType: BlockEntityType<AssemblerBlockEntity>) :
        CraftingMachineBlockEntity(4, blockEntityType), SidedInventory, Tickable {

    var craftingTime = -1
        private set

    val propertyDelegate = object : PropertyDelegate {
        override fun size() = 1

        override fun get(key: Int) = when (key) {
            PROGRESS_PROPERTY -> craftingTime
            else -> throw IndexOutOfBoundsException("key $key")
        }

        override fun set(key: Int, value: Int) = when (key) {
            PROGRESS_PROPERTY -> craftingTime = value
            else -> throw IndexOutOfBoundsException("key $key")
        }
    }

    override fun getContainerName() = TranslatableTextComponent("container.assembler")

    override fun getInvAvailableSlots(direction: Direction): IntArray {
        if (direction == Direction.DOWN) {
            return OUTPUT_SLOTS
        } else {
            return COMMON_SLOTS
        }
    }

    override fun canInsertInvStack(index: Int, stack: ItemStack, direction: Direction?): Boolean {
        return this.isValidInvStack(index, stack)
    }


    override fun canExtractInvStack(index: Int, stack: ItemStack, direction: Direction): Boolean {
        return true
    }

    override fun tick() {
        if (craftingTime > 0) {
            craftingTime--
        }
        if (!world.isClient) {
            if (craftingTime == 0) {
                val recipe = getRecipe()
                if (recipe != null) {
                    val result = craftItem(null, recipe.output.copy())
                    if (!result.isEmpty) {
                        for (i in 18 until 22) {
                            val stack = getInvStack(i)
                            if (stack.isEmpty) {
                                setInvStack(i, result)
                                break
                            } else if (ItemStackUtil.areStacksCombinable(result, stack)) {
                                // TODO: check max stack size
                                stack.amount += result.amount
                                break
                            }
                        }
                        craftingTime = CRAFTING_WORK
                        markDirty()
                    }
                }
            }
            if (!canCraft()) {
                if (craftingTime != -1) {
                    craftingTime = -1
                    markDirty()
                }
            } else {
                if (craftingTime == -1) {
                    craftingTime = CRAFTING_WORK
                    markDirty()
                }
            }
        }
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        super.toTag(tag)
        tag.putInt("CraftingTime", craftingTime)
        return tag
    }

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)
        craftingTime = tag.getInt("CraftingTime")
    }

    companion object {
        fun createContainer(syncId: Int, identifier: Identifier, playerEntity: PlayerEntity,
                reader: PacketByteBuf): GenericContainer {
            val pos = reader.readBlockPos()
            val assemblerEntity = playerEntity.world.getBlockEntity(pos) as AssemblerBlockEntity
            return AssemblerContainer(syncId, assemblerEntity, playerEntity, assemblerEntity.propertyDelegate)
        }

        fun calculateProgress(craftingTime: Int) = if (craftingTime == -1) 0.0f else 1.0f - craftingTime.toFloat().div(
                CRAFTING_WORK)

        const val CRAFTING_WORK = 20 * 6

        const val PROGRESS_PROPERTY = 0

        val OUTPUT_SLOTS = intArrayOf(18, 19, 20, 21)

        val COMMON_SLOTS = IntArray(18) { it }
    }
}
