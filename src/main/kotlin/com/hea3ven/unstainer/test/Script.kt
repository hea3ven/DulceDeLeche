package com.hea3ven.unstainer.test

import net.minecraft.world.World

class Script(val actions: List<Action>) {}

class ScriptBuilder {
    private val actions = mutableListOf<Action>()

    fun run(closure: (World) -> Any): ScriptBuilder {
        actions.add(RunAction(closure))
        return this
    }

    fun wait(count: Int): ScriptBuilder {
        actions.add(TickWaitAction(count))
        return this
    }

    fun build() = Script(actions)
}