package com.direwolf20.mininggadgets.client;

import com.direwolf20.mininggadgets.client.screens.ModificationTableScreen;
import com.direwolf20.mininggadgets.common.Config;
import com.direwolf20.mininggadgets.common.items.MiningGadget;
import com.direwolf20.mininggadgets.common.items.ModItems;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.screen.ExclusionZones;
import me.shedaniel.rei.api.common.entry.comparison.EntryComparator;
import me.shedaniel.rei.api.common.entry.comparison.ItemComparatorRegistry;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;

public class MiningGadgetsREI implements REIClientPlugin {
    @Override
    public void registerItemComparators(ItemComparatorRegistry registry) {
        EntryComparator<ItemStack> chargedProvider = (context, stack) -> {
            if (!(stack.getItem() instanceof MiningGadget)) {
                return 0;
            }

            double energy = stack.getOrCreateTag().getDouble("energy");
            if (energy == 0) {
                return 1;
            } else if (energy == Config.MININGGADGET_MAXPOWER.get()) {
                return 2;
            }

            return 0;
        };
        registry.register(chargedProvider, ModItems.MININGGADGET.get(), ModItems.MININGGADGET_SIMPLE.get(), ModItems.MININGGADGET_FANCY.get());
    }

    @Override
    public void registerExclusionZones(ExclusionZones zones) {
        zones.register(ModificationTableScreen.class, containerScreen -> Collections.singleton(new Rectangle((containerScreen.width / 2) - 120, (containerScreen.height / 2) - 5, 25, 35)));
    }
}
