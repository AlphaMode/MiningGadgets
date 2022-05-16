package com.direwolf20.mininggadgets.common.items;

import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.world.item.ItemStack;
import team.reborn.energy.api.EnergyStorage;

public class EnergisedItem extends SnapshotParticipant<Long> implements EnergyStorage {
    private ItemStack stack;
    public long amount = 0;
    public long capacity;

    public EnergisedItem(ItemStack stack, long capacity) {
        this.capacity = getMaxCapacity(stack, capacity);

        this.stack = stack;
        this.amount = stack.hasTag() && stack.getTag().contains("energy") ? stack.getTag().getLong("energy") : 0;
    }

    private static long getMaxCapacity(ItemStack stack, long capacity) {
        if( !stack.hasTag() || !stack.getTag().contains("max_energy") )
            return capacity;

        return stack.getTag().getLong("max_energy");
    }

    public void updatedMaxEnergy(long max) {
        stack.getOrCreateTag().putLong("max_energy", max);
        this.capacity = max;

        // Ensure the current stored energy is up to date with the new max.
        try (Transaction t = TransferUtil.getTransaction()) {
            this.insert(1, t);
            t.commit();
        }
    }

    @Override
    public long insert(long maxAmount, TransactionContext transaction) {
        long stored = this.getAmount() + Long.MAX_VALUE;
        if (stored < 0) {
            return 0;
        }
        updateSnapshots(transaction);
        amount += maxAmount;

        return Math.min(capacity - this.amount, Long.MAX_VALUE);
    }

    @Override
    public long extract(long maxAmount, TransactionContext transaction) {
        return 0;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getCapacity() {
        return capacity;
    }

    @Override
    protected Long createSnapshot() {
        return amount;
    }

    @Override
    protected void readSnapshot(Long snapshot) {
        amount = snapshot;
    }

    @Override
    protected void onFinalCommit() {
        stack.getOrCreateTag().putLong("energy", this.amount);
    }
}
