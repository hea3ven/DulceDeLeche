package com.hea3ven.dulcedeleche.fabric

import com.hea3ven.dulcedeleche.ModClientDulceDeLeche
import com.hea3ven.dulcedeleche.ModDulceDeLeche
import com.hea3ven.tools.commonutils.mod.fabric.FabricClientModHandler
import com.hea3ven.tools.commonutils.mod.fabric.FabricModHandler
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer

object DulceDeLecheFabricModInitializer : ModInitializer {

    override fun onInitialize() {
        FabricModHandler.onInitialize(ModDulceDeLeche)
    }

}

object DulceDeLecheFabricClientModInitializer : ClientModInitializer {

    override fun onInitializeClient() {
        FabricClientModHandler.onInitializeClient(ModClientDulceDeLeche)
    }

}
