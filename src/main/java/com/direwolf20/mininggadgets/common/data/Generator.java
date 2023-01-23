package com.direwolf20.mininggadgets.common.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

public class Generator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var includeServer = true;
        var includeClient = true;
        var existingData = System.getProperty("mininggadgets.data.existingData").split(";");
        var helper = new ExistingFileHelper(Arrays.stream(existingData).map(Paths::get).toList(), Collections.emptySet(),
                true, null, null);

        // Client
        generator.addProvider(includeClient, new GeneratorLanguage(generator));
        generator.addProvider(includeClient, new GeneratorItemModels(generator, helper));

        // Server
        generator.addProvider(includeServer, new GeneratorLoot(generator));
        generator.addProvider(includeServer, new GeneratorRecipes(generator));
        generator.addProvider(includeServer, new GeneratorBlockTags(generator));
        generator.addProvider(includeServer, new GeneratorBlockStates(generator, helper));
    }
}
