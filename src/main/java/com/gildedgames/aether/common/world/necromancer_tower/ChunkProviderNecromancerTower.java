package com.gildedgames.aether.common.world.necromancer_tower;

import com.gildedgames.aether.common.registry.content.InstancesAether;
import com.gildedgames.orbis_api.data.region.IRegion;
import com.gildedgames.orbis_api.data.region.Region;
import com.gildedgames.orbis_api.preparation.impl.ChunkDataContainer;
import com.gildedgames.orbis_api.processing.BlockAccessChunkDataContainer;
import com.gildedgames.orbis_api.processing.DataPrimer;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkProviderNecromancerTower implements IChunkGenerator
{

	private final World world;

	private final Random random;

	public ChunkProviderNecromancerTower(final World world, final long seed)
	{
		this.world = world;

		if (!this.world.isRemote)
		{
			this.world.setSeaLevel(255);
		}

		this.random = new Random(seed);
	}

	@Override
	public void populate(final int chunkX, final int chunkZ)
	{

	}

	@Override
	public boolean generateStructures(final Chunk chunkIn, final int x, final int z)
	{
		return false;
	}

	@Override
	public Chunk generateChunk(final int chunkX, final int chunkZ)
	{
		final NecromancerTowerInstance inst = InstancesAether.NECROMANCER_TOWER_HANDLER.getFromDimId(this.world.provider.getDimension());

		this.random.setSeed(chunkX * 0x4f9939f508L + chunkZ * 0x1ef1565bd5L);

		final ChunkDataContainer blocks = new ChunkDataContainer(chunkX, chunkZ);

		if (inst.getTower() != null)
		{
			IRegion region = new Region(new BlockPos(chunkX * 16, 0, chunkZ * 16),
					new BlockPos(chunkX * 16, 255, chunkZ * 16).add(15, 15, 15));

			final DataPrimer dataPrimer = new DataPrimer(new BlockAccessChunkDataContainer(this.world, blocks));

			dataPrimer.place(inst.getTower(), region);
		}

		final Chunk chunk = blocks.createChunk(this.world, chunkX, chunkZ);

		chunk.generateSkylightMap();
		//chunk.resetRelightChecks();

		return chunk;
	}

	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(final EnumCreatureType creatureType, final BlockPos pos)
	{
		final Biome biome = this.world.getBiome(pos);

		if (biome == null)
		{
			return null;
		}
		else
		{
			return biome.getSpawnableList(creatureType);
		}
	}

	@Nullable
	@Override
	public BlockPos getNearestStructurePos(final World worldIn, final String structureName, final BlockPos position, final boolean findUnexplored)
	{
		return null;
	}

	@Override
	public void recreateStructures(final Chunk chunk, final int chunkX, final int chunkZ)
	{

	}

	@Override
	public boolean isInsideStructure(final World worldIn, final String structureName, final BlockPos pos)
	{
		return false;
	}

}
