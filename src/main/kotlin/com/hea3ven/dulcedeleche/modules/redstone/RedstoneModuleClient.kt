package com.hea3ven.dulcedeleche.modules.redstone

import com.hea3ven.dulcedeleche.modules.redstone.client.gui.AssemblerScreen
import com.hea3ven.dulcedeleche.modules.redstone.client.gui.WorkbenchScreen
import com.hea3ven.tools.commonutils.mod.ScreenFactory

@Suppress("unused")
object RedstoneModuleClient : RedstoneModule() {

    override fun onPreInit() {
        super.onPreInit()
        addScreen("workbench", ScreenFactory(::WorkbenchScreen))
        addScreen("assembler", ScreenFactory(::AssemblerScreen))
    }


}

