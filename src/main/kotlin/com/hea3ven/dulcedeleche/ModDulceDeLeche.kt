package com.hea3ven.dulcedeleche

import com.hea3ven.dulcedeleche.enchantments.DulceDeLecheEnchantments
import com.hea3ven.dulcedeleche.food.DulceDeLecheFood
import com.hea3ven.dulcedeleche.redstone.DulceDeLecheRedstone
import com.mojang.authlib.GameProfile
import net.fabricmc.api.ModInitializer
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import java.util.*

object ModDulceDeLeche : ModInitializer {

    private val fakePlayerProfile = GameProfile(UUID.randomUUID(), "[dulcedeleche]")

    override fun onInitialize() {
        DulceDeLecheFood.onInitialize()
        DulceDeLecheEnchantments.onInitialize()
        DulceDeLecheRedstone.onInitialize()
    }

    fun getFakePlayer(world: World) = object : PlayerEntity(world, ModDulceDeLeche.fakePlayerProfile) {
        override fun isSpectator() = false

        override fun isCreative() = false

    }
}

