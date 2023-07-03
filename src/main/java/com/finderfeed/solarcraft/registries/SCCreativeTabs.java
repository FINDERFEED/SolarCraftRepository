package com.finderfeed.solarcraft.registries;

import com.finderfeed.solarcraft.*;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


public class SCCreativeTabs {

    public static final ResourceLocation BACKGROUND = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_items_tab.png");
    public static final ResourceLocation TABS = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_items_tabs.png");



    public static final CreativeModeTab SOLAR_GROUP = CreativeModeTab.builder()
            .icon(()->SolarcraftItems.SOLAR_SHARD.get().getDefaultInstance())
            .title(Component.literal("itemGroup.solar_forge_group"))
            .withTabsImage(TABS)
            .withBackgroundLocation(BACKGROUND)
            .withLabelColor(ChatFormatting.GOLD.getColor())
            .build();

//    public static final CreativeModeTab SOLAR_GROUP = new SolarGroup("solar_forge_group");
//    public static final CreativeModeTab SOLAR_GROUP_BLOCKS = new SolarGroupBlocks("solar_forge_group_blocks");
    public static final CreativeModeTab SOLAR_GROUP_BLOCKS = CreativeModeTab.builder()
            .icon(()->SolarcraftItems.MAGISTONE_BRICKS.get().getDefaultInstance())
            .title(Component.literal("itemGroup.solar_forge_group_blocks"))
            .withTabsImage(TABS)
            .withBackgroundLocation(BACKGROUND)
            .withLabelColor(ChatFormatting.GOLD.getColor())
            .build();


//    public static final CreativeModeTab SOLAR_GROUP_TOOLS = new SolarGroupTools("solar_forge_group_tools");
    public static final CreativeModeTab SOLAR_GROUP_TOOLS = CreativeModeTab.builder()
            .icon(()->SolarcraftItems.SOLAR_NETWORK_BINDER.get().getDefaultInstance())
            .title(Component.literal("itemGroup.solar_forge_group_tools"))
            .withTabsImage(TABS)
            .withBackgroundLocation(BACKGROUND)
            .withLabelColor(ChatFormatting.GOLD.getColor())
            .build();


//    public static final CreativeModeTab SOLAR_GROUP_MATERIALS = new SolarGroupThemed("solar_group_materials", SolarcraftItems.ILLIDIUM_INGOT);
    public static final CreativeModeTab SOLAR_GROUP_MATERIALS = CreativeModeTab.builder()
            .icon(()->SolarcraftItems.ILLIDIUM_INGOT.get().getDefaultInstance())
            .title(Component.literal("itemGroup.solar_group_materials"))
            .withTabsImage(TABS)
            .withBackgroundLocation(BACKGROUND)
            .withLabelColor(ChatFormatting.GOLD.getColor())
            .build();


//    public static final CreativeModeTab SOLAR_GROUP_WEAPONS = new SolarGroupThemed("solar_group_weapons", SolarcraftItems.ILLIDIUM_SWORD);
    public static final CreativeModeTab SOLAR_GROUP_WEAPONS = CreativeModeTab.builder()
            .icon(()->SolarcraftItems.ILLIDIUM_SWORD.get().getDefaultInstance())
            .title(Component.literal("itemGroup.solar_group_weapons"))
            .withTabsImage(TABS)
            .withBackgroundLocation(BACKGROUND)
            .withLabelColor(ChatFormatting.GOLD.getColor())
            .build();

//    public static final CreativeModeTab SOLAR_GROUP_FRAGMENTS = new SolarGroupFragments("solar_forge_group_fragments");
    public static final CreativeModeTab SOLAR_GROUP_FRAGMENTS = CreativeModeTab.builder()
            .icon(()->SolarcraftItems.INFO_FRAGMENT.get().getDefaultInstance())
            .title(Component.literal("itemGroup.solar_group_weapons"))
            .withTabsImage(TABS)
            .withBackgroundLocation(BACKGROUND)
            .withLabelColor(ChatFormatting.GOLD.getColor())
            .build();


}
