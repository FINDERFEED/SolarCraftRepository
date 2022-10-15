package com.finderfeed.solarcraft.registries.containers;

import com.finderfeed.solarcraft.content.blocks.blockentities.containers.*;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexiconContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SolarcraftContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPE = DeferredRegister.create(ForgeRegistries.MENU_TYPES,"solarcraft");
    public static final RegistryObject<MenuType<SolarFurnaceContainer>> SOLAR_FURNACE_CONTAINER = CONTAINER_TYPE.register("solar_furnace_container",()-> IForgeMenuType.create(SolarFurnaceContainer::new));
    public static final RegistryObject<MenuType<RunicTableContainer>> RUNIC_TABLE_CONTAINER = CONTAINER_TYPE.register("runic_table_container",()-> IForgeMenuType.create(RunicTableContainer::new));
    public static final RegistryObject<MenuType<SolarLexiconContainer>> SOLAR_LEXICON_CONTAINER = CONTAINER_TYPE.register("solar_lexicon_container",()-> IForgeMenuType.create(SolarLexiconContainer::new));
    public static final RegistryObject<MenuType<ModuleApplierMenu>> MODULE_APPLIER_CONTAINER = CONTAINER_TYPE.register("module_applier",()->IForgeMenuType.create(ModuleApplierMenu::new));
    public static final RegistryObject<MenuType<InfusingTableTileContainer>> INFUSING_TABLE_TILE = CONTAINER_TYPE.register("infusing_crafting_table_container",()-> IForgeMenuType.create(InfusingTableTileContainer::new));
    public static final RegistryObject<MenuType<EnchanterContainer>> ENCHANTER = CONTAINER_TYPE.register("enchanter_container",()-> IForgeMenuType.create(EnchanterContainer::new));
    public static final RegistryObject<MenuType<RunicEnergyChargerContainer>> RUNIC_ENERGY_CHARGER = CONTAINER_TYPE.register("runic_energy_charger_container",()-> IForgeMenuType.create(RunicEnergyChargerContainer::new));
}
