package com.direwolf20.mininggadgets.common.network.packets;

import com.direwolf20.mininggadgets.common.containers.ModificationTableCommands;
import com.direwolf20.mininggadgets.common.containers.ModificationTableContainer;
import com.direwolf20.mininggadgets.common.tiles.ModificationTableTileEntity;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public final class PacketInsertUpgrade implements C2SPacket {
    public PacketInsertUpgrade(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readItem());
    }

    private final BlockPos pos;
    private final ItemStack upgrade;

    public PacketInsertUpgrade(BlockPos blockPos, ItemStack stack) {
        this.pos = blockPos;
        this.upgrade = stack;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeItem(upgrade);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        server.execute(() -> {
            if (player == null) return;

            Level world = player.level;
            BlockPos pos = this.pos;

            BlockEntity te = world.getBlockEntity(pos);
            if (!(te instanceof ModificationTableTileEntity)) return;
            ModificationTableContainer container = ((ModificationTableTileEntity) te).getContainer(player);

            ItemStack stack = player.containerMenu.getCarried();
            if (!stack.sameItem(upgrade)) {
                return;
            }

            if (ModificationTableCommands.insertButton(container, this.upgrade)) {
                player.containerMenu.setCarried(ItemStack.EMPTY);
            }
        });
    }
}
