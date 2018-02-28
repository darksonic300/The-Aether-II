package com.gildedgames.aether.api.world.islands;

import com.gildedgames.aether.api.world.TemplateInstance;
import com.gildedgames.aether.api.world.generation.TemplateDefinition;
import com.gildedgames.aether.api.world.generation.TemplateLoc;
import com.gildedgames.orbis.api.core.BlueprintDefinition;
import com.gildedgames.orbis.api.core.ICreationData;
import com.gildedgames.orbis.api.core.PlacedBlueprint;
import com.gildedgames.orbis.api.processing.IBlockAccessExtended;
import com.gildedgames.orbis.api.util.mc.NBT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;
import java.util.List;

public interface IVirtualDataManager extends IBlockAccessExtended, NBT
{

	void placeTemplate(TemplateDefinition def, TemplateLoc loc);

	boolean dropTemplate(TemplateInstance templateInstance);

	PlacedBlueprint placeBlueprint(BlueprintDefinition def, ICreationData data);

	boolean dropBlueprint(PlacedBlueprint instance);

	List<PlacedBlueprint> getPlacedBlueprints();

	void setPlacedBlueprints(List<PlacedBlueprint> instances);

	List<TemplateInstance> getPlacedTemplates();

	Chunk createRealChunkFromVirtualData(final World world, final int chunkX, final int chunkZ);

	void dropChunk(final int chunkX, final int chunkZ);

	void dropAllChunks();

	boolean hasChunk(final int chunkX, final int chunkZ);

	@Nullable
	IVirtualChunk getChunk(final int chunkX, final int chunkZ);

	IBlockState getBlock(final int x, final int y, final int z);

	boolean setBlock(int x, int y, int z, IBlockState state);

	boolean isPrepped();

	void markPrepared();

	boolean isPreparing();

	void setPreparing(boolean preparing);

	IVirtualDataManager clone();

}
