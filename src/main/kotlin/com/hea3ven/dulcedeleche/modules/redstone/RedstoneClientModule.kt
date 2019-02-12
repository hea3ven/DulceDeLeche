package com.hea3ven.dulcedeleche.modules.redstone

import com.hea3ven.dulcedeleche.modules.redstone.client.gui.AssemblerScreen
import com.hea3ven.dulcedeleche.modules.redstone.client.gui.WorkbenchScreen
import com.hea3ven.tools.commonutils.mod.ModModule
import net.fabricmc.fabric.api.client.screen.ContainerScreenFactory

object RedstoneClientModule : ModModule() {

    override fun onPreInit() {
        addScreen("workbench", ContainerScreenFactory(::WorkbenchScreen))
        addScreen("assembler", ContainerScreenFactory(::AssemblerScreen))
    }


}

