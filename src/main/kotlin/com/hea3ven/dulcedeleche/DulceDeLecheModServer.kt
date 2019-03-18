package com.hea3ven.dulcedeleche

object DulceDeLecheModServer : DulceDeLecheMod() {

    init {
        addModule("redstone", "com.hea3ven.dulcedeleche.modules.redstone.RedstoneModuleServer.INSTANCE")
    }
}

