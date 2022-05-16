package com.direwolf20.mininggadgets.common.sounds;

import com.direwolf20.mininggadgets.common.MiningGadgets;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public enum OurSounds {
    LASER_LOOP("mining_laser_loop"),
    LASER_START("mining_laser_start1"),
    LASER_END("mining_laser_end1");
    private SoundEvent sound;

    OurSounds(String name) {
        ResourceLocation loc = new ResourceLocation(MiningGadgets.MOD_ID, name);
        sound = Registry.register(Registry.SOUND_EVENT, loc, new SoundEvent(loc));
    }

    public SoundEvent getSound() {
        return sound;
    }

    public static void registerSounds() {
    }
}
