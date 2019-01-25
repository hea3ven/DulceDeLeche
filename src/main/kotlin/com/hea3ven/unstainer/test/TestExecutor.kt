package com.hea3ven.unstainer.test

import com.hea3ven.dulcedeleche.tests.redstone.getRedstoneModuleAcceptanceTests
import net.minecraft.world.World
import net.minecraft.world.dimension.DimensionType

class TestExecutor() {

    private val tests = getRedstoneModuleAcceptanceTests().toMutableList()

    private val testState = TestState().apply {
        start(tests.removeAt(0))
    }

    fun onTick(world: World?) {
        if (world != null && world.dimension.type == DimensionType.OVERWORLD) {
            if (!world.isClient) {
                if(testState.finished) {
                    if(tests.size <= 0){
                        return
                    }
                    testState.start(tests.removeAt(0))
                }
                testState.tick(world)
            }
        }

    }

}
