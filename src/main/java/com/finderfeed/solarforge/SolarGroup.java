package com.finderfeed.solarforge;

import com.finderfeed.solarforge.registries.items.SolarcraftItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

public class SolarGroup extends CreativeModeTab {
    public SolarGroup(String label) {
        super(label);
        this.setBackgroundImage(new ResourceLocation("solarforge","textures/gui/solar_items_tab.png"));
    }

    @Override
    public ItemStack makeIcon() {
        return SolarForge.TEST_ITEM.get().getDefaultInstance();
    }

    @Override
    public ResourceLocation getTabsImage() {
        return new ResourceLocation("solarforge","textures/gui/solar_items_tabs.png");
    }
}

class SolarGroupBlocks extends CreativeModeTab{

    public SolarGroupBlocks(String label) {
        super(label);
        this.setBackgroundImage(new ResourceLocation("solarforge","textures/gui/solar_items_tab.png"));
    }


    @Override
    public ResourceLocation getTabsImage() {
        return new ResourceLocation("solarforge","textures/gui/solar_items_tabs.png");
    }
    @Override
    public ItemStack makeIcon() {
        return SolarcraftItems.SOLAR_STONE_BRICKS.get().getDefaultInstance();
    }
}

class SolarGroupTools extends CreativeModeTab{

    public SolarGroupTools(String label) {
        super(label);
        this.setBackgroundImage(new ResourceLocation("solarforge","textures/gui/solar_items_tab.png"));
    }


    @Override
    public ResourceLocation getTabsImage() {
        return new ResourceLocation("solarforge","textures/gui/solar_items_tabs.png");
    }
    @Override
    public ItemStack makeIcon() {
        return SolarcraftItems.ILLIDIUM_AXE.get().getDefaultInstance();
    }
}
class SolarGroupThemed extends CreativeModeTab{

    public RegistryObject<Item> icon;

    public SolarGroupThemed(String label,RegistryObject<Item> icon) {
        super(label);
        this.icon = icon;
        this.setBackgroundImage(new ResourceLocation("solarforge","textures/gui/solar_items_tab.png"));
    }
    @Override
    public ResourceLocation getTabsImage() {
        return new ResourceLocation("solarforge","textures/gui/solar_items_tabs.png");
    }
    @Override
    public ItemStack makeIcon() {
        return icon.get().getDefaultInstance();
    }
}

class SolarGroupFragments extends CreativeModeTab{

    public SolarGroupFragments(String label) {
        super(label);
        this.setBackgroundImage(new ResourceLocation("solarforge","textures/gui/solar_items_tab.png"));
    }


    @Override
    public ResourceLocation getTabsImage() {
        return new ResourceLocation("solarforge","textures/gui/solar_items_tabs.png");
    }
    @Override
    public ItemStack makeIcon() {
        return SolarcraftItems.INFO_FRAGMENT.get().getDefaultInstance();
    }
}
