package com.finderfeed.solarcraft.registries;

import com.finderfeed.solarcraft.local_library.custom_registries.CustomRegistryEntry;
import net.minecraft.client.gui.screens.Screen;

import java.util.function.Supplier;

public class SolarCraftClientRegistries {

    public static CustomRegistryEntry<Supplier<Screen>> SCREENS = new CustomRegistryEntry<>("screen");

}
