package com.hea3ven.dulcedeleche.modules.world.event

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.item.ItemPlacementContext
import net.minecraft.util.ActionResult

@FunctionalInterface
interface BlockItemPlaceEvent {
    companion object {
        val POST_SUCCESS: Event<BlockItemPlaceEvent> = EventFactory.createArrayBacked(
                BlockItemPlaceEvent::class.java) { listeners ->
            object : BlockItemPlaceEvent {
                override fun place(itemPlacementContext: ItemPlacementContext): ActionResult {
                    for (handler in listeners) {
                        val result = handler.place(itemPlacementContext)
                        if (result != ActionResult.PASS) return result
                    }
                    return ActionResult.PASS
                }
            }
        }
    }

    fun place(itemPlacementContext: ItemPlacementContext): ActionResult
}