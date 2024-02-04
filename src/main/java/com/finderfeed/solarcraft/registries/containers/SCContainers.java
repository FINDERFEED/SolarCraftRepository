package com.finderfeed.solarcraft.registries.containers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.blockentities.containers.*;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserContainer;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarForgeContainer;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexiconContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.neoforged.neoforge.registries.DeferredHolder;

public class SCContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPE = DeferredRegister.create(Registries.MENU, SolarCraft.MOD_ID);

    public static final DeferredHolder<MenuType<?>,MenuType<SolarForgeContainer>> SOLAR_FORGE_CONTAINER = CONTAINER_TYPE.register("solarcraft_container",()-> IMenuTypeExtension.create(SolarForgeContainer::new));
    public static final DeferredHolder<MenuType<?>,MenuType<InfuserContainer>> INFUSING_TABLE_CONTAINER = CONTAINER_TYPE.register("infusing_stand_container",()-> IMenuTypeExtension.create(InfuserContainer::new));
    public static final DeferredHolder<MenuType<?>,MenuType<SolarFurnaceContainer>> SOLAR_FURNACE_CONTAINER = CONTAINER_TYPE.register("solar_furnace_container",()-> IMenuTypeExtension.create(SolarFurnaceContainer::new));
    public static final DeferredHolder<MenuType<?>,MenuType<RunicTableContainer>> RUNIC_TABLE_CONTAINER = CONTAINER_TYPE.register("runic_table_container",()-> IMenuTypeExtension.create(RunicTableContainer::new));
    public static final DeferredHolder<MenuType<?>,MenuType<SolarLexiconContainer>> SOLAR_LEXICON_CONTAINER = CONTAINER_TYPE.register("solar_lexicon_container",()-> IMenuTypeExtension.create(SolarLexiconContainer::new));
    public static final DeferredHolder<MenuType<?>,MenuType<ModuleApplierMenu>> MODULE_APPLIER_CONTAINER = CONTAINER_TYPE.register("module_applier",()->IMenuTypeExtension.create(ModuleApplierMenu::new));
    public static final DeferredHolder<MenuType<?>,MenuType<InfusingTableTileContainer>> INFUSING_TABLE_TILE = CONTAINER_TYPE.register("infusing_crafting_table_container",()-> IMenuTypeExtension.create(InfusingTableTileContainer::new));
    public static final DeferredHolder<MenuType<?>,MenuType<EnchanterContainer>> ENCHANTER = CONTAINER_TYPE.register("enchanter_container",()-> IMenuTypeExtension.create(EnchanterContainer::new));
    public static final DeferredHolder<MenuType<?>,MenuType<RunicEnergyChargerContainer>> RUNIC_ENERGY_CHARGER = CONTAINER_TYPE.register("runic_energy_charger_container",()-> IMenuTypeExtension.create(RunicEnergyChargerContainer::new));
    public static final DeferredHolder<MenuType<?>,MenuType<ElementWeaverContainer>> ELEMENT_WEAVER = CONTAINER_TYPE.register("element_weaver_container",()-> IMenuTypeExtension.create(ElementWeaverContainer::new));
}
