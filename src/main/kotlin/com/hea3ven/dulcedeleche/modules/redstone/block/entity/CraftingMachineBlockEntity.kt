package com.hea3ven.dulcedeleche.modules.redstone.block.entity

import com.hea3ven.tools.commonutils.block.entity.MachineBlockEntity
import com.hea3ven.tools.commonutils.util.InventoryExtraUtil
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.container.Container
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.BasicInventory
import net.minecraft.inventory.CraftingInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.crafting.CraftingRecipe
import net.minecraft.sortme.ItemScatterer
import net.minecraft.util.DefaultedList
import net.minecraft.util.InventoryUtil

abstract class CraftingMachineBlockEntity(additionalSlots: Int, blockEntityType: BlockEntityType<*>) :
        MachineBlockEntity(blockEntityType) {

    private val inventory = DefaultedList.create(18 + additionalSlots, ItemStack.EMPTY)!!

    private val recipe = DefaultedList.create(9, ItemStack.EMPTY)!!

    override fun getInvStack(index: Int) = inventory[index]
    override fun canPlayerUseInv(player: PlayerEntity) = true
    override fun removeInvStack(index: Int) = InventoryUtil.removeStack(inventory, index)!!
    override fun clear() = inventory.clear()
    override fun isInvEmpty() = inventory.all(ItemStack::isEmpty)
    override fun createContainer(syncId: Int, playerInv: PlayerInventory) = null
    override fun takeInvStack(index: Int, amount: Int) = InventoryUtil.splitStack(inventory, index, amount)!!
    override fun setInvStack(index: Int, stack: ItemStack) {
        if (stack.amount > invMaxStackAmount) {
            stack.amount = invMaxStackAmount
        }
        inventory[index] = stack
    }

    override fun getInvSize() = inventory.size

    fun getRecipeStacks() = recipe

    override fun toTag(tag: CompoundTag): CompoundTag {
        super.toTag(tag)
        InventoryUtil.serialize(tag, inventory)
        InventoryExtraUtil.serialize(tag, recipe, "Recipe")
        return tag
    }

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)
        inventory.clear()
        InventoryUtil.deserialize(tag, inventory)
        recipe.clear()
        InventoryExtraUtil.deserialize(tag, recipe, "Recipe")
    }

    fun canCraft(): Boolean {
        if (getRecipe() == null) {
            return false
        }
        val invCopy = BasicInventory(*inventory.map(ItemStack::copy).toTypedArray())
        return consumeItems(invCopy)
    }

    fun craftItem(player: PlayerEntity?, craftedStack: ItemStack): ItemStack {
        if (!world.isClient) {
            val craftingInv1 = getCraftingInventory()
            val remainingStacks = world!!.recipeManager.method_8128(RecipeType.CRAFTING, craftingInv1, world)

            if (!consumeItems(this)) {
                return ItemStack.EMPTY
            }

            storeRemainders(remainingStacks, player)

        }
        return craftedStack
    }

    private fun consumeItems(inventory: Inventory): Boolean {
        for (index in 0..8) {
            val recipeStack = recipe[index]
            if (!recipeStack.isEmpty && !consumeItem(recipeStack, inventory)) {
                // TODO: Test if this works
                // not enough ingredients to craft
                return false
            }
        }
        return true
    }

    private fun consumeItem(stack: ItemStack, inventory: Inventory): Boolean {
        for (invIndex in 0 until invSize) {
            val invStack = inventory.getInvStack(invIndex)
            if (ItemStack.areEqualIgnoreTags(stack, invStack) && ItemStack.areTagsEqual(stack, invStack)) {
                invStack.amount -= 1
                return true
            }
        }
        return false
    }

    private fun storeRemainders(remainingStacks: DefaultedList<ItemStack>, player: PlayerEntity?) {
        for (index in 0 until remainingStacks.size) {
            val originalStack = getInvStack(index)
            val remainingStack = remainingStacks[index]
            if (!remainingStack.isEmpty) {
                if (originalStack.isEmpty) {
                    setInvStack(index, remainingStack)
                } else if (ItemStack.areEqualIgnoreTags(originalStack, remainingStack) && ItemStack.areTagsEqual(
                                originalStack, remainingStack)) {
                    remainingStack.addAmount(originalStack.amount)
                    setInvStack(index, remainingStack)
                } else if (player != null) {
                    if (!player.inventory.insertStack(remainingStack)) {
                        player.dropItem(remainingStack, false)
                    }
                } else {
                    ItemScatterer.spawn(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), remainingStack)
                }
            }
        }
    }

    private fun getCraftingInventory(): CraftingInventory {
        val craftInv = CraftingInventory(object : Container(null, 0) {
            override fun canUse(var1: PlayerEntity?) = false
            override fun getType() = null
        }, 3, 3)
        (0..8).forEach { craftInv.setInvStack(it, recipe[it].copy()) }
        return craftInv
    }

    fun getRecipe(): CraftingRecipe? {
        val craftInv = getCraftingInventory()
        return world.recipeManager.get(RecipeType.CRAFTING, craftInv, world).orElse(null)

    }
}