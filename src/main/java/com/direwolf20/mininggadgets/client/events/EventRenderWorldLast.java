package com.direwolf20.mininggadgets.client.events;

import com.direwolf20.mininggadgets.client.renderer.RenderMiningLaser;
import com.direwolf20.mininggadgets.common.items.MiningGadget;
import com.direwolf20.mininggadgets.common.util.MiscTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "mininggadgets", value = Dist.CLIENT)
public class EventRenderWorldLast {
    @SubscribeEvent
    static void renderWorldLastEvent(RenderWorldLastEvent evt) {

        List<AbstractClientPlayerEntity> players = Minecraft.getInstance().world.getPlayers();
        PlayerEntity myplayer = Minecraft.getInstance().player;

        for (PlayerEntity player : players) {
            ItemStack heldItem = MiscTools.getGadget(player);
            if (player.isHandActive() && heldItem.getItem() instanceof MiningGadget) {
                RenderMiningLaser.renderLaser(player, evt.getPartialTicks());
            }
        }
    }
}