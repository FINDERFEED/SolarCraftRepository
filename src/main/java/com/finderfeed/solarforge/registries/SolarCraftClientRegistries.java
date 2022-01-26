package com.finderfeed.solarforge.registries;

import com.finderfeed.solarforge.local_library.custom_registries.CustomRegistryEntry;
import net.minecraft.client.gui.screens.Screen;

import java.util.function.Supplier;

public class SolarCraftClientRegistries {

    public static CustomRegistryEntry<Supplier<Screen>> SCREENS = new CustomRegistryEntry<>("screen");

}
