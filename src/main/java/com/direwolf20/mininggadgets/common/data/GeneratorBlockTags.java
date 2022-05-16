package com.direwolf20.mininggadgets.common.data;

import com.direwolf20.mininggadgets.common.blocks.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.Registry;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;

public class GeneratorBlockTags extends BlockTagsProvider {
    public GeneratorBlockTags(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addTags() {
        tag(TagKey.create(Registry.BLOCK_REGISTRY, Registry.BLOCK.getKey(ModBlocks.MODIFICATION_TABLE.get()))).addTag(BlockTags.MINEABLE_WITH_PICKAXE);
    }
}
