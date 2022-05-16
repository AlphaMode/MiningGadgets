package com.direwolf20.mininggadgets.common.containers;

import com.direwolf20.mininggadgets.common.items.MiningGadget;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlotItemHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class GhostSlot extends SlotItemHandler {
    public GhostSlot(ItemStackHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void set(@Nonnull ItemStack stack) {
        super.set(stack);
    }

    @Override
    public boolean mayPickup(Player player) {
        return false;
    }


    @Override
    public void onTake(Player thePlayer, ItemStack stack) {
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return !(stack.getItem() instanceof MiningGadget);
    }

    @Override
    public int getMaxStackSize(@Nonnull ItemStack stack) {
        return 1;
    }
}
