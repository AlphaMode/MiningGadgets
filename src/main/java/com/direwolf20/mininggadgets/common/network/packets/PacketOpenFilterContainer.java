package com.direwolf20.mininggadgets.common.network.packets;

import com.direwolf20.mininggadgets.common.containers.FilterContainer;
import com.direwolf20.mininggadgets.common.items.gadget.MiningProperties;
import com.direwolf20.mininggadgets.common.items.MiningGadget;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;

import java.util.function.Supplier;

public class PacketOpenFilterContainer implements C2SPacket {
    public PacketOpenFilterContainer() { }

    @Override
    public void encode(FriendlyByteBuf buffer) {}
    public PacketOpenFilterContainer(FriendlyByteBuf buffer) { this(); }

    @Override
    public void handle(MinecraftServer server, ServerPlayer sender, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        server.execute(() -> {
            if (sender == null)
                return;

            AbstractContainerMenu container = sender.containerMenu;
            if (container == null)
                return;

            ItemStack stack = MiningGadget.getGadget(sender);
            if( stack.isEmpty() )
                return;

            ItemStackHandler ghostInventory = new ItemStackHandler(30) {
                @Override
                protected void onContentsChanged(int slot) {
                    stack.getOrCreateTag().put(MiningProperties.KEY_FILTERS, serializeNBT());
                }
            };

            ghostInventory.deserializeNBT(stack.getOrCreateTagElement(MiningProperties.KEY_FILTERS));
            sender.openMenu(new SimpleMenuProvider(
                    (windowId, playerInventory, playerEntity) -> new FilterContainer(windowId, playerInventory, ghostInventory), new TextComponent("")
            ));
        });
    }
}
