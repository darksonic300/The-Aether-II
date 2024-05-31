package com.aetherteam.aetherii.data.resources.registries;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.data.resources.builders.AetherIIStructureBuilders;
import com.aetherteam.aetherii.data.resources.registries.pools.OutpostPools;
import com.aetherteam.aetherii.world.structure.AetherJigsawStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.List;
import java.util.Optional;

public class AetherIIStructures {
    public static final ResourceKey<Structure> OUTPOST = createKey("outpost");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(AetherII.MODID, name));
    }

    public static void bootstrap(BootstapContext<Structure> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);

        context.register(OUTPOST, new AetherJigsawStructure(
                AetherIIStructureBuilders.structure(biomes.getOrThrow(AetherIITags.Biomes.HAS_STRUCTURE_OUTPOST), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN),
                templatePools.getOrThrow(OutpostPools.START), Optional.empty(), 10, ConstantHeight.of(VerticalAnchor.absolute(0)), Optional.of(Heightmap.Types.WORLD_SURFACE_WG), 32, 64, List.of()));
    }
}