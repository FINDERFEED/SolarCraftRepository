package com.finderfeed.solarforge.registries;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.for_future_library.custom_registries.CustomDeferredRegistry;
import com.finderfeed.solarforge.for_future_library.custom_registries.CustomRegistryEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class ScreenSuppliers {

    public static final CustomDeferredRegistry<Screen> SCREEN_REGISTRY = CustomDeferredRegistry.create(SolarForge.MOD_ID,SolarCraftClientRegistries.SCREENS);

    public static final Screen TEST = SCREEN_REGISTRY.register("test",()-> new Screen(new TranslatableComponent("")) {
        @Override
        public Component getTitle() {
            return super.getTitle();
        }
    });

}
