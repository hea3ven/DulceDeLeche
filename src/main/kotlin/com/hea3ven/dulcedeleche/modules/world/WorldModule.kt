package com.hea3ven.dulcedeleche.modules.world

import com.hea3ven.dulcedeleche.ModDulceDeLeche
import com.hea3ven.dulcedeleche.fabric.DulceDeLecheFabricModInitializer
import com.hea3ven.dulcedeleche.modules.world.event.BlockItemPlaceEvent
import com.hea3ven.tools.commonutils.mod.ModModule
import com.hea3ven.tools.commonutils.util.ReflectionUtil.reflectField
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.block.Block
import net.minecraft.block.LeavesBlock
import net.minecraft.item.ItemPlacementContext
import net.minecraft.util.ActionResult
import net.minecraft.util.registry.Registry

object WorldModule : ModModule() {

    override fun onInit() {
        if (ModDulceDeLeche.cfg.modules.world.nonSolidLeavesEnabled) {
            val leavesBlocks = Registry.BLOCK.filter { it is LeavesBlock }
            reflectField(Block::class.java, "collidable", "field_10640") { collidableField ->
                leavesBlocks.forEach {
                    collidableField.set(it, false)
                }
            }
        }
        BlockItemPlaceEvent.POST_SUCCESS.register(object : BlockItemPlaceEvent {
            override fun place(itemPlacementContext: ItemPlacementContext): ActionResult {
                return BedManagement.onPlace(itemPlacementContext)
            }
        })
        UseBlockCallback.EVENT.register(UseBlockCallback(BedManagement::onPlayerInteract))
    }

}


