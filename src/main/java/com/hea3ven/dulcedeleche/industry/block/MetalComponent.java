package com.hea3ven.dulcedeleche.industry.block;

import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class MetalComponent {

	private Metal[] metals;
	private int[] metaByIndex;
	private int[] indexByMeta;

	public MetalComponent(Metal[] metals) {
		this.metals = metals;
		metaByIndex = new int[Metal.values().length];
		for (int i = 0; i < Metal.values().length; i++) {
			Metal idxMetal = Metal.get(i);
			metaByIndex[i] = -1;
			for (int j = 0; j < metals.length; j++) {
				if (idxMetal == metals[j]) {
					metaByIndex[i] = j;
				}
			}
		}
		indexByMeta = new int[metals.length];
		for (int i = 0; i < metals.length; i++) {
			indexByMeta[i] = metals[i].ordinal();
		}
	}

	public Metal[] getMetals() {
		return metals;
	}

	public int getMetaForMetal(Metal metal) {
		return metaByIndex[metal.ordinal()];
	}

	public Metal getMetalForMeta(int meta) {
		return metals[meta];
	}

}
