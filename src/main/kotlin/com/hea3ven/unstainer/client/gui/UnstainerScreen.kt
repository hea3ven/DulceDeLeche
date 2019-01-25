package com.hea3ven.unstainer.client.gui

import com.hea3ven.unstainer.test.TestExecutor
import com.mojang.datafixers.Dynamic
import com.mojang.datafixers.types.JsonOps
import net.fabricmc.fabric.api.event.world.WorldTickCallback
import net.minecraft.client.gui.Screen
import net.minecraft.datafixers.NbtOps
import net.minecraft.text.StringTextComponent
import net.minecraft.world.GameMode
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig
import net.minecraft.world.level.LevelGeneratorType
import net.minecraft.world.level.LevelInfo
import java.util.*

class UnstainerScreen : Screen(StringTextComponent("Unstainer")) {
    private lateinit var testExecutor: TestExecutor

    private var initializations = 0

    override fun onInitialized() {
        super.onInitialized()
        testExecutor = TestExecutor()
        initializations++
    }

    override fun update() {
        if (initializations == 2) {
            startNewWorld()
            WorldTickCallback.EVENT.register(WorldTickCallback(testExecutor::onTick))
        }
    }

    private fun startNewWorld() {
        client!!.openScreen(null)

        val seed = Random().nextLong()
        val levelInfo = LevelInfo(seed, GameMode.CREATIVE, true, false, LevelGeneratorType.FLAT)
        val cfg = FlatChunkGeneratorConfig.getDefaultConfig().toDynamic(NbtOps.INSTANCE).value
        levelInfo.generatorOptions = Dynamic.convert(NbtOps.INSTANCE, JsonOps.INSTANCE, cfg)
        levelInfo.setBonusChest();
        levelInfo.enableCommands();

        client!!.startIntegratedServer("TestWorld", "Test World", levelInfo);
    }
}