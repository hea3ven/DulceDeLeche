package com.hea3ven.unstainer.test

import net.minecraft.world.World

abstract class Action {
    abstract fun onTick(world: World): Boolean
}
