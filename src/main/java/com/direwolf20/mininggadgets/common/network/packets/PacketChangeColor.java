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

public class PacketChangeColor implements C2SPacket {
    private final short red;
    private final short green;
    private final short blue;
    private final short red_inner;
    private final short green_inner;
    private final short blue_inner;

    public PacketChangeColor(int red, int green, int blue, int red_inner, int green_inner, int blue_inner) {
        this.red = (short) red;
        this.green = (short) green;
        this.blue = (short) blue;
        this.red_inner = (short) red_inner;
        this.green_inner = (short) green_inner;
        this.blue_inner = (short) blue_inner;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeShort(red);
        buffer.writeShort(green);
        buffer.writeShort(blue);
        buffer.writeShort(red_inner);
        buffer.writeShort(green_inner);
        buffer.writeShort(blue_inner);
    }

    public PacketChangeColor(FriendlyByteBuf buffer) {
        this(buffer.readShort(), buffer.readShort(), buffer.readShort(), buffer.readShort(), buffer.readShort(), buffer.readShort());
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        server.execute(() -> {
            if (player == null)
                return;

            ItemStack stack = MiningGadget.getGadget(player);
            MiningProperties.setColor(stack, red, MiningProperties.COLOR_RED);
            MiningProperties.setColor(stack, green, MiningProperties.COLOR_GREEN);
            MiningProperties.setColor(stack, blue, MiningProperties.COLOR_BLUE);
            MiningProperties.setColor(stack, red_inner, MiningProperties.COLOR_RED_INNER);
            MiningProperties.setColor(stack, green_inner, MiningProperties.COLOR_GREEN_INNER);
            MiningProperties.setColor(stack, blue_inner, MiningProperties.COLOR_BLUE_INNER);
        });
    }
}
