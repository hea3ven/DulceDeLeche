package com.hea3ven.dulcedeleche

import com.hea3ven.dulcedeleche.modules.redstone.RedstoneClientModule
import net.fabricmc.api.ClientModInitializer

@Suppress("unused")
object ModClientDulceDeLeche : ClientModInitializer {

    override fun onInitializeClient() {
        RedstoneClientModule.onInitialize()
    }
}

