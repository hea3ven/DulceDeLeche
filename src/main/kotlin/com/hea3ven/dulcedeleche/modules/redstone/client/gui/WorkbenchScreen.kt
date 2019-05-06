package com.hea3ven.dulcedeleche.modules.redstone.client.gui

import com.hea3ven.tools.commonutils.container.GenericContainer
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.util.Identifier

class WorkbenchScreen(workbenchContainer: GenericContainer, playerInv: PlayerInventory, name: Component) :
        CraftingMachineScreen<GenericContainer>(workbenchContainer, playerInv, name,
                Identifier("dulcedeleche:textures/gui/container/workbench.png"))