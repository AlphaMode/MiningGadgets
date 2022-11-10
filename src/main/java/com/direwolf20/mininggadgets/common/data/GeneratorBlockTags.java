package com.direwolf20.mininggadgets.common.data;

import com.direwolf20.mininggadgets.common.blocks.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tags.BlockTags;

public class GeneratorBlockTags extends FabricTagProvider.BlockTagProvider {
    public GeneratorBlockTags(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    protected void generateTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.MODIFICATION_TABLE.get());
    }
}
