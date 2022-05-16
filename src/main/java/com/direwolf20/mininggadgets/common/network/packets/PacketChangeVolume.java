package com.direwolf20.mininggadgets.common.network.packets;

import com.direwolf20.mininggadgets.common.items.gadget.MiningProperties;
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

public class PacketChangeVolume implements C2SPacket {
    private float volume;

    public PacketChangeVolume(float volume) {
        this.volume = volume;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeFloat(volume);
    }

    public PacketChangeVolume(FriendlyByteBuf buffer) {
        this(buffer.readFloat());
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        server.execute(() -> {
            if (player == null)
                return;

            ItemStack stack = MiningGadget.getGadget(player);

            // Active toggle feature
            MiningProperties.setVolume(stack, volume);
        });
    }
}
