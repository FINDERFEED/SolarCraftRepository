package com.finderfeed.solarforge.registries;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.client.screens.EightElementsFragmentScreen;
import com.finderfeed.solarforge.client.screens.LoreScreen;
import com.finderfeed.solarforge.for_future_library.custom_registries.CustomDeferredRegistry;
import com.finderfeed.solarforge.for_future_library.custom_registries.CustomRegistryEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.function.Supplier;

public class ScreenSuppliers {

    public static final CustomDeferredRegistry<Supplier<Screen>> SCREEN_REGISTRY = CustomDeferredRegistry.create(SolarForge.MOD_ID,SolarCraftClientRegistries.SCREENS);

    public static final Supplier<Screen> RADIANT_LAND_LORE = SCREEN_REGISTRY.register("radiant_land_lore",()->()->
            new LoreScreen(new TranslatableComponent("radiant_land.lore"),"radiant_land"));
    public static final Supplier<Screen> RUNIC_ENERGY_TRANSMISSION = SCREEN_REGISTRY.register("runic_energy_lore",()->()->
            new LoreScreen(new TranslatableComponent("runic_energy.lore"),"runic_energy"));
    public static final Supplier<Screen> DEFENCE_CRYSTAL = SCREEN_REGISTRY.register("defence_crystal_lore",()->()->
            new LoreScreen(new TranslatableComponent("defence_crystal.lore"),"defence_crystal"));
    public static final Supplier<Screen> EIGHT_ELEMENTS = SCREEN_REGISTRY.register("eight_elements_lore",()->
            EightElementsFragmentScreen::new);
}
