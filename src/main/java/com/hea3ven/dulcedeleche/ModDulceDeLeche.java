package com.hea3ven.dulcedeleche;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.hea3ven.tools.bootstrap.Bootstrap;

@Mod(modid = ModDulceDeLeche.MODID, version = ModDulceDeLeche.VERSION,
		dependencies = "required-after:Forge@[11.14.3.1513,)")
public class ModDulceDeLeche {

	public static final String MODID = "dulcedeleche";
	public static final String VERSION = "1.0.0";

	private final ProxyCommonDulceDeLeche proxy = new ProxyCommonDulceDeLeche();

	static {
		Bootstrap.require(MODID, "1.0.x");
		Bootstrap.initLib(MODID, "h3nt-commonutils", "1.0.1", "1.0.1");
	}

	@EventHandler
	public void onPreInitEvent(FMLPreInitializationEvent event) {
		proxy.onPreInitEvent();
	}

	@EventHandler
	public void onInitEvent(FMLInitializationEvent event) {
		proxy.onInitEvent();
	}

	@EventHandler
	public void onPostInitEvent(FMLPostInitializationEvent event) {
		proxy.onPostInitEvent();
	}

}
