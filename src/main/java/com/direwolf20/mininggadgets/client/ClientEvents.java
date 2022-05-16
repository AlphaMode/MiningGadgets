package com.direwolf20.mininggadgets.client;

import com.direwolf20.mininggadgets.client.renderer.BlockOverlayRender;
import com.direwolf20.mininggadgets.client.renderer.ModificationShiftOverlay;
import com.direwolf20.mininggadgets.client.renderer.RenderMiningLaser;
import com.direwolf20.mininggadgets.client.screens.ModScreens;
import com.direwolf20.mininggadgets.common.items.MiningGadget;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.fabricators_of_create.porting_lib.event.client.DrawSelectionEvents;
import io.github.fabricators_of_create.porting_lib.event.client.KeyInputCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class ClientEvents {

    public static void init() {
        DrawSelectionEvents.BLOCK.register(ClientEvents::drawBlockHighlightEvent);
        WorldRenderEvents.END.register(ClientEvents::renderWorldLastEvent);
        KeyInputCallback.EVENT.register(ClientEvents::keyPressed);
    }

    static boolean drawBlockHighlightEvent(LevelRenderer context, Camera info, HitResult target, float partialTicks, PoseStack matrix, MultiBufferSource buffers) {
        if( Minecraft.getInstance().player == null )
            return false;

        if(MiningGadget.isHolding(Minecraft.getInstance().player))
            return true;
        return false;
    }

    static void renderWorldLastEvent(WorldRenderContext context) {
        List<AbstractClientPlayer> players = Minecraft.getInstance().level.players();
        Player myplayer = Minecraft.getInstance().player;

        ItemStack myItem = MiningGadget.getGadget(myplayer);
        if (myItem.getItem() instanceof MiningGadget)
            BlockOverlayRender.render(context.matrixStack(), myItem);

        if (myplayer.isShiftKeyDown()) {
            ModificationShiftOverlay.render(context.matrixStack(), context.tickDelta(), myplayer);
        }

        for (Player player : players) {
            if (player.distanceToSqr(myplayer) > 500)
                continue;

            ItemStack heldItem = MiningGadget.getGadget(player);
            if (player.isUsingItem() && heldItem.getItem() instanceof MiningGadget) {
                if (MiningGadget.canMine(heldItem)) {
                    RenderMiningLaser.renderLaser(context.matrixStack(), player, Minecraft.getInstance().getFrameTime());
                }
            }
        }
    }

    static void keyPressed(int key, int scancode, int action, int mods) {
        if (OurKeys.shiftClickGuiBinding.consumeClick() && Minecraft.getInstance().screen == null) {
            if (Minecraft.getInstance().player == null) {
                return;
            }

            ItemStack gadget = MiningGadget.getGadget(Minecraft.getInstance().player);
            if (gadget.isEmpty()) {
                return;
            }

            ModScreens.openGadgetSettingsScreen(gadget);
        }
    }
}
