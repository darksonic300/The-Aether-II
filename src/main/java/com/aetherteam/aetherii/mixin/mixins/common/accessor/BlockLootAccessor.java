package com.aetherteam.aetherii.mixin.mixins.common.accessor;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockLootSubProvider.class)
public interface BlockLootAccessor {
    @Accessor("HAS_SILK_TOUCH")
    static LootItemCondition.Builder aether_ii$hasSilkTouch() {
        throw new AssertionError();
    }

    @Accessor("HAS_SHEARS_OR_SILK_TOUCH")
    static LootItemCondition.Builder aether_ii$hasShearsOrSilkTouch() {
        throw new AssertionError();
    }

    @Accessor("NORMAL_LEAVES_SAPLING_CHANCES")
    static float[] aether_ii$getNormalLeavesSaplingChances() {
        throw new AssertionError();
    }
}