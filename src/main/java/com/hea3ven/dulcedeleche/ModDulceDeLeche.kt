package com.hea3ven.dulcedeleche

import com.hea3ven.tools.bootstrap.Bootstrap
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

@Mod(modid = ModDulceDeLeche.MODID, version = ModDulceDeLeche.VERSION,
		dependencies = ModDulceDeLeche.DEPENDENCIES)
class ModDulceDeLeche {
	@Mod.EventHandler
	public fun OnPreInit(event: FMLPreInitializationEvent?) {
		proxy.onPreInitEvent(event)
	}

	@Mod.EventHandler
	public fun OnInit(event: FMLInitializationEvent?) {
		proxy.onInitEvent(event)
	}

	@Mod.EventHandler
	public fun OnPostInit(event: FMLPostInitializationEvent?) {
		proxy.onPostInitEvent(event)
	}

	companion object {
		const val MODID = "dulcedeleche"
		const val VERSION = "1.8.9-1.0.0"
		const val DEPENDENCIES = "required-after:Forge@[11.15.1.1723,)"

		const val guiIdAssembler = 0

		init {
			Bootstrap.init()
		}

		val proxy = ProxyModDulceDeLeche()
	}
}

