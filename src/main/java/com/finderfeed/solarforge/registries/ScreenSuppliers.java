package com.finderfeed.solarforge.registries;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.client.screens.EightElementsFragmentScreen;
import com.finderfeed.solarforge.client.screens.LoreScreen;
import com.finderfeed.solarforge.local_library.custom_registries.CustomDeferredRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

public class ScreenSuppliers {

    public static final CustomDeferredRegistry<Supplier<Screen>> SCREEN_REGISTRY = CustomDeferredRegistry.create(SolarForge.MOD_ID,SolarCraftClientRegistries.SCREENS);

    public static final Supplier<Screen> RADIANT_LAND_LORE = SCREEN_REGISTRY.register("radiant_land_lore",()->()->
            new LoreScreen(Component.translatable("radiant_land.lore"),"radiant_land"));
    public static final Supplier<Screen> RUNIC_ENERGY_TRANSMISSION = SCREEN_REGISTRY.register("runic_energy_lore",()->()->
            new LoreScreen(Component.translatable("runic_energy.lore"),"runic_energy"));
    public static final Supplier<Screen> DEFENCE_CRYSTAL = SCREEN_REGISTRY.register("defence_crystal_lore",()->()->
            new LoreScreen(Component.translatable("defence_crystal.lore"),"defence_crystal"));
    public static final Supplier<Screen> RUNIC_ELEMENTAL = SCREEN_REGISTRY.register("runic_elemental_lore",()->()->
            new LoreScreen(Component.translatable("runic_elemental.lore"),"runic_elemental"));
    public static final Supplier<Screen> SHADOW_ZOMBIE = SCREEN_REGISTRY.register("shadow_zombie_lore",()->()->
            new LoreScreen(Component.translatable("shadow_zombie.lore"),"shadow_zombie"));
    public static final Supplier<Screen> EIGHT_ELEMENTS = SCREEN_REGISTRY.register("eight_elements_lore",()->
            EightElementsFragmentScreen::new);
}
