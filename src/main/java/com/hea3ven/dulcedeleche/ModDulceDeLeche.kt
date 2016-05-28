package com.hea3ven.dulcedeleche

import com.hea3ven.tools.bootstrap.Bootstrap
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

@Mod(modid = ModDulceDeLeche.MODID, version = ModDulceDeLeche.VERSION,
		dependencies = ModDulceDeLeche.DEPENDENCIES,
		guiFactory = "com.hea3ven.dulcedeleche.DulceDeLecheModGuiFactory")
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
		const val VERSION = "1.9-1.0.0"
		const val DEPENDENCIES = "required-after:Forge@[12.16.1.1894,)"

		const val guiIdAssembler = 0

		init {
			Bootstrap.init()
		}

		val proxy = ProxyModDulceDeLeche()
	}
}

