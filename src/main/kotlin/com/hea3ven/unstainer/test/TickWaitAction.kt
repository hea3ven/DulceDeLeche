package com.hea3ven.unstainer.test

import net.minecraft.world.World

class TickWaitAction(private var count: Int) : Action() {
    override fun onTick(world: World): Boolean {
        count--
        return count < 0
    }
}