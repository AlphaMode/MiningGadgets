package com.direwolf20.mininggadgets.common.network.packets;

import com.direwolf20.mininggadgets.common.containers.GhostSlot;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;

public class PacketGhostSlot implements C2SPacket {
    private int slotNumber;
    private ItemStack stack;

    public PacketGhostSlot(int slotNumber, ItemStack stack) {
        this.slotNumber = slotNumber;
        this.stack = stack;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(slotNumber);
        buffer.writeItem(stack);
    }

    public PacketGhostSlot(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readItem());
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer sender, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        server.execute(() -> {
            if (sender == null)
                return;

            AbstractContainerMenu container = sender.containerMenu;
            if (container == null)
                return;

            Slot slot = container.slots.get(slotNumber);
            if (slot instanceof GhostSlot)
                slot.set(stack);
        });
    }
}
