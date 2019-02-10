package com.hea3ven.dulcedeleche.modules.redstone

import com.hea3ven.dulcedeleche.modules.redstone.RedstoneModule.assemblerId
import com.hea3ven.dulcedeleche.modules.redstone.RedstoneModule.workbenchId
import com.hea3ven.dulcedeleche.modules.redstone.client.gui.AssemblerScreen
import com.hea3ven.dulcedeleche.modules.redstone.client.gui.WorkbenchScreen
import net.fabricmc.fabric.api.client.screen.ContainerScreenFactory
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry

object RedstoneClientModule {

    fun onInitialize() {
        ScreenProviderRegistry.INSTANCE.registerFactory(workbenchId, ContainerScreenFactory(::WorkbenchScreen))
        ScreenProviderRegistry.INSTANCE.registerFactory(assemblerId, ContainerScreenFactory(::AssemblerScreen))
    }

}

