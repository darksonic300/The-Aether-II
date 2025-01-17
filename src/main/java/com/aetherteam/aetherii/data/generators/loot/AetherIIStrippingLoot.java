package com.aetherteam.aetherii.data.generators.loot;

import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.loot.AetherIILoot;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class AetherIIStrippingLoot implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
        builder.accept(AetherIILoot.STRIP_MOSSY_WISPROOT, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Blocks.MOSS_BLOCK) //todo aether moss?
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        builder.accept(AetherIILoot.STRIP_AMBEROOT, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(AetherIIItems.GOLDEN_AMBER.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }
}
