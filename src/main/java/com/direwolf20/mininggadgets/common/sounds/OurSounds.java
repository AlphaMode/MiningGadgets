package com.direwolf20.mininggadgets.common.sounds;

import com.direwolf20.mininggadgets.common.MiningGadgets;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public interface OurSounds {
    LazyRegistrar<SoundEvent> SOUND_REGISTRY = LazyRegistrar.create(Registry.SOUND_EVENT, MiningGadgets.MOD_ID);

    RegistryObject<SoundEvent> LASER_LOOP = SOUND_REGISTRY.register("mining_laser_loop", () -> new SoundEvent(new ResourceLocation(MiningGadgets.MOD_ID, "mining_laser_loop")));
    RegistryObject<SoundEvent> LASER_START = SOUND_REGISTRY.register("mining_laser_start1", () -> new SoundEvent(new ResourceLocation(MiningGadgets.MOD_ID, "mining_laser_start1")));
    RegistryObject<SoundEvent> LASER_END = SOUND_REGISTRY.register("mining_laser_end1", () -> new SoundEvent(new ResourceLocation(MiningGadgets.MOD_ID, "mining_laser_end1")));
}
