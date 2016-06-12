package com.hea3ven.dulcedeleche

import com.hea3ven.tools.bootstrap.Bootstrap
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

@Mod(modid = ModDulceDeLeche.MODID, version = ModDulceDeLeche.VERSION,
		dependencies = ModDulceDeLeche.DEPENDENCIES,
		guiFactory = "com.hea3ven.dulcedeleche.DulceDeLecheModGuiFactory",
		updateJSON = "https://raw.githubusercontent.com/hea3ven/DulceDeLeche/version/media/update.json")
class ModDulceDeLeche {
	@Mod.EventHandler
	fun OnPreInit(event: FMLPreInitializationEvent?) {
		proxy.onPreInitEvent(event)
	}

	@Mod.EventHandler
	fun OnInit(event: FMLInitializationEvent?) {
		proxy.onInitEvent(event)
	}

	@Mod.EventHandler
	fun OnPostInit(event: FMLPostInitializationEvent?) {
		proxy.onPostInitEvent(event)
	}

	companion object {
		const val MODID = "dulcedeleche"
		const val VERSION = "1.9.4-1.0.1"
		const val DEPENDENCIES = "required-after:Forge@[12.17.0.1954,)"

		const val guiIdAssembler = 0

		init {
			Bootstrap.init()
		}

		val proxy = ProxyModDulceDeLeche()
	}
}

