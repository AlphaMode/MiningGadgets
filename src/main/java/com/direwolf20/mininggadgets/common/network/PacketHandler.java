package com.direwolf20.mininggadgets.common.network;

import com.direwolf20.mininggadgets.common.MiningGadgets;
import com.direwolf20.mininggadgets.common.network.packets.*;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = Integer.toString(2);
    private static short index = 0;

    public static final SimpleChannel HANDLER = new SimpleChannel(new ResourceLocation(MiningGadgets.MOD_ID, "main_network_channel"));

    public static void register() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
            HANDLER.initClientListener();
        HANDLER.initServerListener();
        int id = 0;

        // Server side
        HANDLER.registerC2SPacket(PacketExtractUpgrade.class,     id++);
        HANDLER.registerC2SPacket(PacketUpdateUpgrade.class,      id++);
        HANDLER.registerC2SPacket(PacketChangeMiningSize.class,   id++);
        HANDLER.registerC2SPacket(PacketChangeRange.class,        id++);
        HANDLER.registerC2SPacket(PacketChangeBreakType.class,    id++);
        HANDLER.registerC2SPacket(PacketChangeColor.class,        id++);
        HANDLER.registerC2SPacket(PacketGhostSlot.class,          id++);
        HANDLER.registerC2SPacket(PacketOpenFilterContainer.class,id++);
        HANDLER.registerC2SPacket(PacketToggleFilters.class,      id++);
        HANDLER.registerC2SPacket(PacketTogglePrecision.class,    id++);
        HANDLER.registerC2SPacket(PacketChangeVolume.class,       id++);
        HANDLER.registerC2SPacket(PacketChangeFreezeDelay.class,  id++);

        //Client Side
        HANDLER.registerS2CPacket(PacketDurabilitySync.class,     id++);
        HANDLER.registerC2SPacket(PacketInsertUpgrade.class,      id++);
    }

    public static void sendTo(S2CPacket msg, ServerPlayer player) {
//        if (!(player instanceof FakePlayer))
            HANDLER.sendToClient(msg, player);
    }

    public static void sendToAll(S2CPacket msg, Level world) {
        //Todo Maybe only send to nearby players?
        for (Player player : world.players()) {
//            if (!(player instanceof FakePlayer))
                HANDLER.sendToClient(msg, (ServerPlayer) player);
        }
    }

    public static void sendToServer(C2SPacket msg) {
        HANDLER.sendToServer(msg);
    }
}
