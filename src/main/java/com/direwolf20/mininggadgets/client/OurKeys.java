package com.direwolf20.mininggadgets.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;

public class OurKeys {
    public static final KeyMapping shiftClickGuiBinding = new KeyMapping("mininggadgets.text.open_gui", InputConstants.UNKNOWN.getValue(), "itemGroup.mininggadgets");

    public static void register() {
        KeyBindingHelper.registerKeyBinding(shiftClickGuiBinding);
    }
}
