package com.direwolf20.mininggadgets.client;

import com.direwolf20.mininggadgets.client.events.EventRenderGadget;
import com.direwolf20.mininggadgets.client.particles.ParticleRenderDispatcher;
import com.direwolf20.mininggadgets.client.renderer.ModificationTableTER;
import com.direwolf20.mininggadgets.client.renderer.RenderBlockTER;
import com.direwolf20.mininggadgets.client.screens.FilterScreen;
import com.direwolf20.mininggadgets.client.screens.ModificationTableScreen;
import com.direwolf20.mininggadgets.common.blocks.ModBlocks;
import com.direwolf20.mininggadgets.common.containers.ModContainers;
import io.github.fabricators_of_create.porting_lib.event.client.RenderHandCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

/**
 * Only put client code here plz.
 */
public final class ClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerRenderers();
        registerContainerScreens();
        OurKeys.register();
        ClientEvents.init();
        ParticleRenderDispatcher.registerFactories();
        RenderHandCallback.EVENT.register(EventRenderGadget::renderGadget);
    }

    /**
     * Called from some Client Dist runner in the main class
     */
    private static void registerContainerScreens() {
        MenuScreens.register(ModContainers.MODIFICATIONTABLE_CONTAINER.get(), ModificationTableScreen::new);
        MenuScreens.register(ModContainers.FILTER_CONTAINER.get(), FilterScreen::new);
    }

    /**
     * Client Registry for renders
     */
    private static void registerRenderers() {
        BlockEntityRendererRegistry.register(ModBlocks.RENDERBLOCK_TILE.get(), RenderBlockTER::new);
        BlockEntityRendererRegistry.register(ModBlocks.MODIFICATIONTABLE_TILE.get(), ModificationTableTER::new);
    }
}
