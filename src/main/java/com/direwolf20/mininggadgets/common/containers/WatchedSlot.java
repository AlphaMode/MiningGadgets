package com.direwolf20.mininggadgets.common.containers;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlotItemHandler;

import java.util.function.Consumer;

/**
 * This isn't required but does make the cache very effective and quick.
 */
public class WatchedSlot extends SlotItemHandler {
    private Consumer<Integer> onPress;

    public WatchedSlot(ItemStackHandler itemHandler, int index, int xPosition, int yPosition, Consumer<Integer> onPress) {
        super(itemHandler, index, xPosition, yPosition);
        this.onPress = onPress;
    }

    @Override
    public void setChanged() {
        super.setChanged();

        this.onPress.accept(this.getSlotIndex());
    }
}
