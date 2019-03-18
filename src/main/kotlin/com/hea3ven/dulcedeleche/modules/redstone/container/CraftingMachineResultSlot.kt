package com.hea3ven.dulcedeleche.modules.redstone.container

/*
class CraftingMachineResultSlot(private val craftingMachineCraftingInv: CraftingMachineCraftingInventory,
        inventory: Inventory, x: Int, y: Int) : Slot(inventory, 0, x, y) {

    override fun canInsert(itemStack_1: ItemStack) = false

    override fun takeStack(amount: Int): ItemStack {
        return super.getStack().copy().apply { this.amount = amount }
    }

    override fun canTakeItems(player: PlayerEntity): Boolean {
        return craftingMachineCraftingInv.canCraft(player)
    }

    override fun onTakeItem(player: PlayerEntity?, craftedStack: ItemStack): ItemStack {
        onCrafted(craftedStack)
        return craftingMachineCraftingInv.craftItem(player, craftedStack)
    }

    override fun doDrawHoveringEffect(): Boolean {
        return true
    }
}
        */