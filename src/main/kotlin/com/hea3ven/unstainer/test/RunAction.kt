package com.hea3ven.unstainer.test

import net.minecraft.world.World

class RunAction(private val closure: (World) -> Any) :Action() {
    override fun onTick(world: World): Boolean {
        closure.invoke(world)
        return false
    }
}