package com.hea3ven.dulcedeleche.modules.world

import com.hea3ven.dulcedeleche.Module
import com.hea3ven.dulcedeleche.modules.world.event.BlockItemPlaceEvent
import com.hea3ven.tools.commonutils.util.ReflectionUtil.reflectField
import net.fabricmc.fabric.events.PlayerInteractionEvent
import net.minecraft.block.Block
import net.minecraft.block.LeavesBlock
import net.minecraft.item.ItemPlacementContext
import net.minecraft.util.ActionResult
import net.minecraft.util.registry.Registry

object WorldModule : Module<WorldModuleConfig>() {
    override fun createDefaultConfig() = WorldModuleConfig(true, 3 * 24000)

    override fun onInitialize() {
        if (cfg.nonSolidLeavesEnabled) {
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
        PlayerInteractionEvent.INTERACT_BLOCK.register(
                PlayerInteractionEvent.BlockPositioned(BedManagement::onPlayerInteract))
    }

}


