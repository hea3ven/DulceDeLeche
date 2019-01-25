package com.hea3ven.unstainer.test

import net.minecraft.world.World

class TestState {

    private lateinit var currentTest: UnstainerTest

    private var step = 0

    private var ticks = 0

    val finished
        get() = currentTest.script.actions.size <= step

    fun start(test: UnstainerTest) {
        currentTest = test
        step = 0
    }

    fun tick(world: World) {
        if (ticks++ < 100) {
            return
        }
        if (finished) {
            return
        }
        do {
            val finishTick = currentTest.script.actions[step++].onTick(world)
        } while (!finishTick && !finished)
    }

}
