package com.hea3ven.dulcedeleche.modules.world.event

import net.fabricmc.fabric.util.HandlerArray
import net.fabricmc.fabric.util.HandlerRegistry
import net.minecraft.item.ItemPlacementContext
import net.minecraft.util.ActionResult

@FunctionalInterface
interface BlockItemPlaceEvent {
    companion object {
        val POST_SUCCESS: HandlerRegistry<BlockItemPlaceEvent> = HandlerArray(BlockItemPlaceEvent::class.java)
    }

    fun place(itemPlacementContext: ItemPlacementContext) : ActionResult
}