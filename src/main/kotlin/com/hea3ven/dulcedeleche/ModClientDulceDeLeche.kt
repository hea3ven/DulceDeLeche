package com.hea3ven.dulcedeleche

import com.hea3ven.tools.commonutils.mod.ModComposite

object ModClientDulceDeLeche : ModComposite("dulcedeleche") {

    init {
        addModule("redstoneClient", "com.hea3ven.dulcedeleche.modules.redstone.RedstoneClientModule.INSTANCE")
    }
}

