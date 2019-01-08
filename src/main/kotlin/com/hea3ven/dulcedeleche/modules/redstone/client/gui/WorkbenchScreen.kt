package com.hea3ven.dulcedeleche.modules.redstone.client.gui

import com.hea3ven.tools.commonutils.container.GenericContainer
import net.minecraft.text.StringTextComponent
import net.minecraft.util.Identifier

class WorkbenchScreen(workbenchContainer: GenericContainer) :
        CraftingMachineScreen(workbenchContainer, StringTextComponent("Workbench"),
                              Identifier("dulcedeleche:textures/gui/container/workbench.png"))