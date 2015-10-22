package com.hea3ven.dulcedeleche.industry.world;

import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import com.hea3ven.dulcedeleche.industry.block.BlockMetalOre;
import com.hea3ven.dulcedeleche.industry.metal.Metal;

public class WorldGeneratorOre implements IWorldGenerator {

	@ObjectHolder("dulcedeleche:ore")
	public static final BlockMetalOre ore = null;
	private WorldGenMinable[] minables;
	private int[] minLevels;
	private int[] maxLevels;

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (minables == null) {
			Metal[] metals = ore.getMetals();
			minables = new WorldGenMinable[metals.length];
			minLevels = new int[metals.length];
			maxLevels = new int[metals.length];
			for (int i = 0; i < minables.length; i++) {
				minables[i] = new WorldGenMinable(ore.setMetal(ore.getDefaultState(), metals[i]),
						9);
				minLevels[i] = metals[i].getWorldMinLevel();
				maxLevels[i] = metals[i].getWorldMaxLevel();
			}
		}

		switch (world.provider.getDimensionId()) {
			case -1:
				break;
			case 0:
				generateSurface(world, rand, chunkX * 16, chunkZ * 16);
				break;
			case 1:
				break;
		}
	}

	private void generateSurface(World world, Random rand, int x, int z) {
		for (int i = 0; i < minables.length; i++) {
			for (int k = 0; k < 16; k++) {
				int veinX = x + rand.nextInt(16);
				int veinZ = z + rand.nextInt(16);
				int y = minLevels[i] + rand.nextInt(maxLevels[i] - minLevels[i]);
				minables[i].generate(world, rand, new BlockPos(veinX, y, veinZ));
			}
		}
	}

}
