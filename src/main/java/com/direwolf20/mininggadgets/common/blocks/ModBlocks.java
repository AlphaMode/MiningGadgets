package com.direwolf20.mininggadgets.common.blocks;

import com.direwolf20.mininggadgets.common.MiningGadgets;
import com.direwolf20.mininggadgets.common.tiles.ModificationTableTileEntity;
import com.direwolf20.mininggadgets.common.tiles.RenderBlockTileEntity;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlocks {
    /**
     * Deferred Registers for the our Main class to load.
     */
    public static final LazyRegistrar<Block> BLOCKS = LazyRegistrar.create(Registry.BLOCK, MiningGadgets.MOD_ID);
    public static final LazyRegistrar<BlockEntityType<?>> TILES_ENTITIES = LazyRegistrar.create(Registry.BLOCK_ENTITY_TYPE, MiningGadgets.MOD_ID);

    /**
     * Register our blocks to the above registers to be loaded when the mod is initialized
     */
    public static final RegistryObject<Block> RENDER_BLOCK = BLOCKS.register("renderblock", RenderBlock::new);
    public static final RegistryObject<Block> MINERS_LIGHT = BLOCKS.register("minerslight", MinersLight::new);
    public static final RegistryObject<Block> MODIFICATION_TABLE = BLOCKS.register("modificationtable", ModificationTable::new);

    /**
     * TileEntity Registers to the above deferred registers to be loaded in from the mods main class.
     */
    public static final RegistryObject<BlockEntityType<RenderBlockTileEntity>> RENDERBLOCK_TILE =
            TILES_ENTITIES.register("renderblock", () -> BlockEntityType.Builder.of(RenderBlockTileEntity::new, ModBlocks.RENDER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ModificationTableTileEntity>> MODIFICATIONTABLE_TILE =
            TILES_ENTITIES.register("modificationtable", () -> BlockEntityType.Builder.of(ModificationTableTileEntity::new, ModBlocks.MODIFICATION_TABLE.get()).build(null));
}
