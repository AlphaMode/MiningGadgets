package com.direwolf20.mininggadgets.common.network.packets;

import com.direwolf20.mininggadgets.common.containers.ModificationTableCommands;
import com.direwolf20.mininggadgets.common.containers.ModificationTableContainer;
import com.direwolf20.mininggadgets.common.tiles.ModificationTableTileEntity;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class PacketExtractUpgrade implements C2SPacket {

    private final BlockPos pos;
    private final String upgrade;
    private final int nameLength;

    public PacketExtractUpgrade(BlockPos blockPos, String upgrade, int nameLength) {
        this.pos = blockPos;
        this.upgrade = upgrade;
        this.nameLength = nameLength;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(nameLength);
        buffer.writeBlockPos(pos);
        buffer.writeUtf(upgrade);
    }

    public PacketExtractUpgrade(FriendlyByteBuf buffer) {
        int strLength = buffer.readInt();
        this.pos = buffer.readBlockPos();
        this.upgrade = buffer.readUtf(strLength);
        this.nameLength = strLength;
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        server.execute(() -> {
            if (player == null) return;

            Level world = player.level;

            BlockEntity te = world.getBlockEntity(pos);
            if (!(te instanceof ModificationTableTileEntity)) return;
            ModificationTableContainer container = ((ModificationTableTileEntity) te).getContainer(player);

            ModificationTableCommands.extractButton(container, player, upgrade);
        });
    }
}
