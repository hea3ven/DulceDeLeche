package com.hea3ven.dulcedeleche;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.tuple.Pair;

import com.hea3ven.tools.commonutils.mod.ModInitializerClient;

public class ProxyClientDulceDeLeche extends ProxyCommonDulceDeLeche {
	
	public ProxyClientDulceDeLeche() {
		super(new ModInitializerClient());
	}

	@Override
	public ArrayList<Pair<String, Integer>> getBlockItems() {
		return Lists.newArrayList(Pair.of("dulcedeleche:assembler", 0));
	}
}
