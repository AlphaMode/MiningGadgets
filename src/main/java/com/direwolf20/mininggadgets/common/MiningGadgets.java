package com.direwolf20.mininggadgets.common;

import com.direwolf20.mininggadgets.client.particles.ModParticles;
import com.direwolf20.mininggadgets.common.blocks.ModBlocks;
import com.direwolf20.mininggadgets.common.containers.ModContainers;
import com.direwolf20.mininggadgets.common.events.ServerTickHandler;
import com.direwolf20.mininggadgets.common.items.EnergisedItem;
import com.direwolf20.mininggadgets.common.items.MiningGadget;
import com.direwolf20.mininggadgets.common.items.ModItems;
import com.direwolf20.mininggadgets.common.network.PacketHandler;
import com.direwolf20.mininggadgets.common.sounds.OurSounds;
import io.github.fabricators_of_create.porting_lib.util.LazyItemGroup;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.reborn.energy.api.EnergyStorage;

public class MiningGadgets implements ModInitializer
{
    public static final String MOD_ID = "mininggadgets";
    private static final Logger LOGGER = LogManager.getLogger();

    public static CreativeModeTab itemGroup = new LazyItemGroup(MiningGadgets.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            ItemStack itemStack = new ItemStack(ModItems.MININGGADGET.get());
            itemStack.getOrCreateTag().putInt("energy", Integer.MAX_VALUE);
            return itemStack;
        }
    };

    @Override
    public void onInitialize() {
        // Register all of our items, blocks, item blocks, etc
        ModItems.ITEMS.register();
        ModItems.UPGRADE_ITEMS.register();
        ModBlocks.BLOCKS.register();
        ModBlocks.TILES_ENTITIES.register();
        ModContainers.CONTAINERS.register();
        ModParticles.PARTICLE_TYPES.register();

        OurSounds.registerSounds();

        this.setup();

        ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.COMMON, Config.COMMON_CONFIG);

        // Register the setup method for modloading
        UseBlockCallback.EVENT.register(this::rightClickEvent);

        Config.loadConfig(Config.CLIENT_CONFIG, FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + "-client.toml"));
        Config.loadConfig(Config.COMMON_CONFIG, FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + "-common.toml"));

        EnergyStorage.ITEM.registerFallback((itemStack, context) -> {
            if (itemStack.getItem() instanceof MiningGadget)
                return new EnergisedItem(itemStack, Config.MININGGADGET_MAXPOWER.get());
            return null;
        });
    }

    public InteractionResult rightClickEvent(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = MiningGadget.getGadget(player);
        if( stack.getItem() instanceof MiningGadget ) {
            if (this.stackIsAnnoying(player.getMainHandItem())
                    || this.stackIsAnnoying(player.getOffhandItem())
                    || world.getBlockState(hitResult.getBlockPos()).getBlock() instanceof RedStoneOreBlock) {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    /**
     * I've tried to identity annoying offhand items that can be placed whilst mining.
     * I assume some level of logic, so we assume that you'd have that item in your offhand
     * whilst using the gadget.
     */
    private boolean stackIsAnnoying(ItemStack stack) {
        // This should never happen but I like casting safety
        if (!(stack.getItem() instanceof BlockItem))
            return false;

        Block block = ((BlockItem) stack.getItem()).getBlock();
        return block instanceof TorchBlock || block instanceof LanternBlock || block.equals(Blocks.GLOWSTONE)
                || block instanceof RedstoneLampBlock || block instanceof EndRodBlock;
    }

    private void setup()
    {
        PacketHandler.register();
        ServerTickEvents.END_SERVER_TICK.register(ServerTickHandler::handleTickEndEvent);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
