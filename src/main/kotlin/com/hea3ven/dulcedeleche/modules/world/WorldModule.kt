package com.hea3ven.dulcedeleche.modules.world

import com.hea3ven.dulcedeleche.Module
import com.hea3ven.util.reflectField
import net.minecraft.block.Block
import net.minecraft.block.LeavesBlock
import net.minecraft.util.registry.Registry

object WorldModule : Module<WorldModuleConfig>() {
    override fun createDefaultConfig() = WorldModuleConfig(true)

    override fun onInitialize() {
        if (cfg.nonSolidLeavesEnabled) {
            val leavesBlocks = Registry.BLOCK.filter { it is LeavesBlock }
            reflectField(Block::class.java, "collidable", "field_10640") { collidableField ->
                leavesBlocks.forEach {
                    collidableField.set(it, false)
                }
            }
        }
    }

}

