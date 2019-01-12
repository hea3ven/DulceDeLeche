package com.hea3ven.dulcedeleche

import com.hea3ven.dulcedeleche.config.GeneralConfig
import com.hea3ven.dulcedeleche.config.ModulesConfig
import com.hea3ven.dulcedeleche.modules.enchantments.EnchantmentsModule
import com.hea3ven.dulcedeleche.modules.enchantments.EnchantmentsModuleConfig
import com.hea3ven.dulcedeleche.modules.food.FoodModule
import com.hea3ven.dulcedeleche.modules.food.FoodModuleConfig
import com.hea3ven.dulcedeleche.modules.mobs.MobsModule
import com.hea3ven.dulcedeleche.modules.mobs.MobsModuleConfig
import com.hea3ven.dulcedeleche.modules.redstone.RedstoneModule
import com.hea3ven.dulcedeleche.modules.redstone.RedstoneModuleConfig
import com.hea3ven.dulcedeleche.modules.world.WorldModule
import com.hea3ven.dulcedeleche.modules.world.WorldModuleConfig
import com.hea3ven.util.readConfig
import com.mojang.authlib.GameProfile
import net.fabricmc.api.ModInitializer
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import org.apache.logging.log4j.LogManager

object ModDulceDeLeche : ModInitializer {

    private val logger = LogManager.getFormatterLogger("ModDulceDeLeche")

    private val fakePlayerProfile = GameProfile(null, "[dulcedeleche]")

    val modules = listOf(FoodModule, EnchantmentsModule, RedstoneModule, WorldModule, MobsModule)

    override fun onInitialize() {
        initializeConfig()
        initializeModules()
    }

    private fun initializeConfig() {
        logger.info("Reading configuration file")
        val cfg = readConfig("dulcedeleche", GeneralConfig::class.java, this::createDefaultConfig,
                             Pair(ModulesConfig::class.java, createDefaultConfig()::modules),
                             Pair(FoodModuleConfig::class.java, FoodModule::createDefaultConfig),
                             Pair(EnchantmentsModuleConfig::class.java, EnchantmentsModule::createDefaultConfig),
                             Pair(RedstoneModuleConfig::class.java, RedstoneModule::createDefaultConfig),
                             Pair(WorldModuleConfig::class.java, WorldModule::createDefaultConfig),
                             Pair(MobsModuleConfig::class.java, MobsModule::createDefaultConfig))
        FoodModule.cfg = cfg.modules.food
        EnchantmentsModule.cfg = cfg.modules.enchantments
        RedstoneModule.cfg = cfg.modules.redstone
        WorldModule.cfg = cfg.modules.world
        MobsModule.cfg = cfg.modules.mobs
    }

    private fun initializeModules() {
        logger.info("Initializing the modules")
        modules.filter { mod -> mod.cfg.enabled }
                .onEach { logger.info("Initializing module %s", it.javaClass.simpleName) }
                .forEach(Module<*>::onInitialize)
    }

    fun getFakePlayer(world: World) = object : PlayerEntity(world, ModDulceDeLeche.fakePlayerProfile) {
        override fun isSpectator() = false
        override fun isCreative() = false
    }

    private fun createDefaultConfig() = GeneralConfig(
            ModulesConfig(FoodModule.createDefaultConfig(), EnchantmentsModule.createDefaultConfig(),
                          RedstoneModule.createDefaultConfig(), WorldModule.createDefaultConfig(),
                          MobsModule.createDefaultConfig()))

}

