package com.hea3ven.dulcedeleche

object DulceDeLecheModClient : DulceDeLecheMod() {

    init {
        addModule("redstone", "com.hea3ven.dulcedeleche.modules.redstone.RedstoneModuleClient.INSTANCE")
    }
}

