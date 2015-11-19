package com.hea3ven.dulcedeleche.industry.world;

import java.util.Random;

import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
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
	private Metal[] metals;

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		if (minables == null) {
			metals = ore.getMetalComponent().getMetals();
			minables = new WorldGenMinable[metals.length];
			for (int i = 0; i < minables.length; i++) {
				minables[i] = new WorldGenMinable(ore.setMetal(ore.getDefaultState(), metals[i]),
						metals[i].getWorldSize(), (i < 2) ? BlockHelper.forBlock(Blocks.stone) :
						BlockHelper.forBlock(Blocks.netherrack));
			}
		}

		switch (world.provider.getDimensionId()) {
			case -1:
				generateNether(world, rand, chunkX * 16, chunkZ * 16);
			case 0:
				generateSurface(world, rand, chunkX * 16, chunkZ * 16);
				break;
			case 1:
				break;
		}
	}

	private void generateSurface(World world, Random rand, int x, int z) {
		for (int i = 0; i < 2; i++) {
			for (int k = 0; k < metals[i].getWorldCount(); k++) {
				int veinX = x + rand.nextInt(16);
				int veinZ = z + rand.nextInt(16);
				int y = metals[i].getWorldMinLevel() + rand.nextInt(
						metals[i].getWorldMaxLevel() - metals[i].getWorldMinLevel());
				minables[i].generate(world, rand, new BlockPos(veinX, y, veinZ));
			}
		}
	}

	private void generateNether(World world, Random rand, int x, int z) {
		for (int k = 0; k < metals[2].getWorldCount(); k++) {
			int veinX = x + rand.nextInt(16);
			int veinZ = z + rand.nextInt(16);
			int y = metals[2].getWorldMinLevel() + rand.nextInt(
					metals[2].getWorldMaxLevel() - metals[2].getWorldMinLevel());
			minables[2].generate(world, rand, new BlockPos(veinX, y, veinZ));
		}
	}
}
