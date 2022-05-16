package com.direwolf20.mininggadgets.common.containers;

import com.direwolf20.mininggadgets.client.ClientSetup;
import com.direwolf20.mininggadgets.common.MiningGadgets;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;

/**
 * @implNote Container screens are registered in {@link ClientSetup#setup()}
 */
public class ModContainers {
    public static final LazyRegistrar<MenuType<?>> CONTAINERS = LazyRegistrar.create(Registry.MENU, MiningGadgets.MOD_ID);

    // Our containers
    public static final RegistryObject<MenuType<ModificationTableContainer>> MODIFICATIONTABLE_CONTAINER
            = CONTAINERS.register("modificationtable", () -> new ExtendedScreenHandlerType(ModificationTableContainer::new));

    public static final RegistryObject<MenuType<FilterContainer>> FILTER_CONTAINER
            = CONTAINERS.register("filter_container", () -> new ExtendedScreenHandlerType<>(FilterContainer::new));
}
