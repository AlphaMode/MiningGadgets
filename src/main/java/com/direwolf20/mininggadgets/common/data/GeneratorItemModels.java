package com.direwolf20.mininggadgets.common.data;

import com.direwolf20.mininggadgets.common.MiningGadgets;
import com.direwolf20.mininggadgets.common.blocks.ModBlocks;
import com.direwolf20.mininggadgets.common.items.ModItems;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GeneratorItemModels extends ItemModelProvider {
    public GeneratorItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MiningGadgets.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Register all of the upgrade items
        ModItems.UPGRADE_ITEMS.getEntries().forEach(item -> {
            String path = item.getId().getPath();
            singleTexture(path, mcLoc("item/handheld"), "layer0", modLoc("item/" + path));
        });

        // Our block items
        registerBlockModel(ModBlocks.MODIFICATION_TABLE);
        registerBlockModel(ModBlocks.MINERS_LIGHT);
    }

    private void registerBlockModel(RegistryObject<Block> block) {
        String path = block.getId().getPath();
        getBuilder(path).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + path)));
    }

    @Override
    public String getName() {
        return "Item Models";
    }
}
