package com.direwolf20.mininggadgets.common.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Generator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
//         if( event.includeServer() )
            registerServerProviders(gen);

//        if( event.includeClient() )
            registerClientProviders(gen);
    }

    private static void registerServerProviders(FabricDataGenerator generator) {
        generator.addProvider(new GeneratorLoot(generator));
        generator.addProvider(new GeneratorRecipes(generator));
    }

    private static void registerClientProviders(FabricDataGenerator generator) {
        ExistingFileHelper helper = null;

        generator.addProvider(new GeneratorBlockTags(generator));
        generator.addProvider(new GeneratorBlockStates(generator, helper));
        generator.addProvider(new GeneratorItemModels(generator, helper));
        generator.addProvider(new GeneratorLanguage(generator));
    }
}
