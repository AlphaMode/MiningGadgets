package com.direwolf20.mininggadgets.common.network.packets;

import com.direwolf20.mininggadgets.common.items.upgrade.Upgrade;
import com.direwolf20.mininggadgets.common.items.upgrade.UpgradeTools;
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

public class PacketUpdateUpgrade implements C2SPacket {
    private final String upgrade;

    public PacketUpdateUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(upgrade);
    }

    public PacketUpdateUpgrade(FriendlyByteBuf buffer) {
        this(buffer.readUtf(100));
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        server.execute(() -> {
            if (player == null)
                return;

            Upgrade upgrade = UpgradeTools.getUpgradeByName(this.upgrade);
            if( upgrade == null )
                return;

            ItemStack stack = MiningGadget.getGadget(player);
            UpgradeTools.updateUpgrade(stack, upgrade); //todo: change.
        });
    }
}
