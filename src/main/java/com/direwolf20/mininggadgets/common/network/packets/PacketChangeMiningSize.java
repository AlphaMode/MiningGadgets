package com.direwolf20.mininggadgets.common.network.packets;

import com.direwolf20.mininggadgets.common.items.MiningGadget;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;

public class PacketChangeMiningSize implements C2SPacket {
    public PacketChangeMiningSize() {}

    @Override
    public void encode(FriendlyByteBuf buffer) {}
    public PacketChangeMiningSize(FriendlyByteBuf buffer) { this(); }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        server.execute(() -> {
            if (player == null)
                return;

            ItemStack stack = MiningGadget.getGadget(player);
            MiningGadget.changeRange(stack);
        });
    }
}
