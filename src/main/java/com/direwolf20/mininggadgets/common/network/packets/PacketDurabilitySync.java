package com.direwolf20.mininggadgets.common.network.packets;

import com.direwolf20.mininggadgets.common.tiles.RenderBlockTileEntity;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.Tuple;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PacketDurabilitySync implements S2CPacket {
    private final List<Tuple<BlockPos, Integer>> updateList;
    //private final int priorDurability;

    public PacketDurabilitySync(List<Tuple<BlockPos, Integer>> updateList) {
        this.updateList = updateList;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        List<Tuple<BlockPos, Integer>> thisList = updateList;
        CompoundTag tag = new CompoundTag();
        ListTag nbtList = new ListTag();
        for (int i = 0; i < thisList.size(); i++) {
            CompoundTag nbt = new CompoundTag();
            nbt.put("pos", NbtUtils.writeBlockPos(thisList.get(i).getA()));
            nbt.putInt("dur", thisList.get(i).getB());
            nbtList.add(i, nbt);
        }
        tag.put("list", nbtList);
        buffer.writeNbt(tag);
    }

    public PacketDurabilitySync(FriendlyByteBuf buffer) {
        CompoundTag tag = buffer.readNbt();
        ListTag nbtList = tag.getList("list", Tag.TAG_COMPOUND);
        List<Tuple<BlockPos, Integer>> thisList = new ArrayList<>();
        for (int i = 0; i < nbtList.size(); i++) {
            CompoundTag nbt = nbtList.getCompound(i);
            thisList.add(new Tuple<>(NbtUtils.readBlockPos(nbt.getCompound("pos")), nbt.getInt("dur")));
        }
        this.updateList = thisList;
    }

    @Override
    public void handle(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel) {
        client.execute(() -> clientPacketHandler(this));
    }

    public static void clientPacketHandler(PacketDurabilitySync msg) {
        List<Tuple<BlockPos, Integer>> thisList = msg.updateList;

        for (int i = 0; i < thisList.size(); i++) {
            BlockPos pos = thisList.get(i).getA();
            int durability = thisList.get(i).getB();
            BlockEntity clientTE = Minecraft.getInstance().level.getBlockEntity(pos);
            if (!(clientTE instanceof RenderBlockTileEntity)) return;
            ((RenderBlockTileEntity) clientTE).setClientDurability(durability);
        }
    }
}
