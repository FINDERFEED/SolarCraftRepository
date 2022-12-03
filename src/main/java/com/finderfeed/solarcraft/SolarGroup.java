package com.finderfeed.solarcraft;

import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

public class SolarGroup extends CreativeModeTab {
    public SolarGroup(String label) {
        super(label);
        this.setBackgroundImage(new ResourceLocation("solarcraft","textures/gui/solar_items_tab.png"));
    }

    @Override
    public ItemStack makeIcon() {
        return SolarcraftItems.SOLAR_SHARD.get().getDefaultInstance();
    }

    @Override
    public ResourceLocation getTabsImage() {
        return new ResourceLocation("solarcraft","textures/gui/solar_items_tabs.png");
    }
    @Override
    public Component getDisplayName() {
        return super.getDisplayName().copy().withStyle(ChatFormatting.GOLD);
    }
}

class SolarGroupBlocks extends CreativeModeTab{

    public SolarGroupBlocks(String label) {
        super(label);
        this.setBackgroundImage(new ResourceLocation("solarcraft","textures/gui/solar_items_tab.png"));
    }


    @Override
    public ResourceLocation getTabsImage() {
        return new ResourceLocation("solarcraft","textures/gui/solar_items_tabs.png");
    }
    @Override
    public ItemStack makeIcon() {
        return SolarcraftItems.SOLAR_STONE_BRICKS.get().getDefaultInstance();
    }

    @Override
    public Component getDisplayName() {
        return super.getDisplayName().copy().withStyle(ChatFormatting.GOLD);
    }
}

class SolarGroupTools extends CreativeModeTab{

    public SolarGroupTools(String label) {
        super(label);
        this.setBackgroundImage(new ResourceLocation("solarcraft","textures/gui/solar_items_tab.png"));
    }


    @Override
    public ResourceLocation getTabsImage() {
        return new ResourceLocation("solarcraft","textures/gui/solar_items_tabs.png");
    }
    @Override
    public ItemStack makeIcon() {
        return SolarcraftItems.ILLIDIUM_AXE.get().getDefaultInstance();
    }

    @Override
    public Component getDisplayName() {
        return super.getDisplayName().copy().withStyle(ChatFormatting.GOLD);
    }
}
class SolarGroupThemed extends CreativeModeTab{

    public RegistryObject<Item> icon;

    public SolarGroupThemed(String label,RegistryObject<Item> icon) {
        super(label);
        this.icon = icon;
        this.setBackgroundImage(new ResourceLocation("solarcraft","textures/gui/solar_items_tab.png"));
    }
    @Override
    public ResourceLocation getTabsImage() {
        return new ResourceLocation("solarcraft","textures/gui/solar_items_tabs.png");
    }
    @Override
    public ItemStack makeIcon() {
        return icon.get().getDefaultInstance();
    }

    @Override
    public Component getDisplayName() {
        return super.getDisplayName().copy().withStyle(ChatFormatting.GOLD);
    }
}

class SolarGroupFragments extends CreativeModeTab{

    public SolarGroupFragments(String label) {
        super(label);
        this.setBackgroundImage(new ResourceLocation("solarcraft","textures/gui/solar_items_tab.png"));
    }


    @Override
    public ResourceLocation getTabsImage() {
        return new ResourceLocation("solarcraft","textures/gui/solar_items_tabs.png");
    }
    @Override
    public ItemStack makeIcon() {
        return SolarcraftItems.INFO_FRAGMENT.get().getDefaultInstance();
    }

    @Override
    public Component getDisplayName() {
        return super.getDisplayName().copy().withStyle(ChatFormatting.GOLD);
    }
}
