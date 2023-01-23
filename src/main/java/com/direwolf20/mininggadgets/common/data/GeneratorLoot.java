package com.direwolf20.mininggadgets.common.data;

import com.direwolf20.mininggadgets.common.blocks.ModBlocks;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import io.github.fabricators_of_create.porting_lib.data.ModdedBlockLoot;
import io.github.fabricators_of_create.porting_lib.data.ModdedLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GeneratorLoot extends ModdedLootTableProvider {
    public GeneratorLoot(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
                Pair.of(Blocks::new, LootContextParamSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationresults) {
        map.forEach((name, table) -> LootTables.validate(validationresults, name, table));
    }

    private static class Blocks extends ModdedBlockLoot {
        @Override
        protected void addTables() {
            this.dropSelf(ModBlocks.MODIFICATION_TABLE.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return Collections.singletonList(ModBlocks.MODIFICATION_TABLE.get());
        }
    }
}
