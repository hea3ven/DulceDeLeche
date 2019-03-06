package com.hea3ven.dulcedeleche.fabric

import com.hea3ven.dulcedeleche.DulceDeLecheMod
import com.hea3ven.dulcedeleche.DulceDeLecheModClient
import com.hea3ven.dulcedeleche.DulceDeLecheModServer
import com.hea3ven.tools.commonutils.mod.fabric.FabricClientModHandler
import com.hea3ven.tools.commonutils.mod.fabric.FabricServerModHandler
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.DedicatedServerModInitializer

@Suppress("unused")
object DulceDeLecheFabricModInitializer : DedicatedServerModInitializer {
    override fun onInitializeServer() {
        FabricServerModHandler.onInitializeServer(DulceDeLecheModServer)
        DulceDeLecheMod.mod = DulceDeLecheModServer
    }

}

@Suppress("unused")
object DulceDeLecheFabricClientModInitializer : ClientModInitializer {

    override fun onInitializeClient() {
        FabricClientModHandler.onInitializeClient(DulceDeLecheModClient)
        DulceDeLecheMod.mod = DulceDeLecheModClient
    }

}
