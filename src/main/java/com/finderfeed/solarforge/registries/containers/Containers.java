package com.finderfeed.solarforge.registries.containers;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.SolarFurnaceContainer;
import com.finderfeed.solarforge.solar_forge_screen.SolarForgeContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Containers {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPE = DeferredRegister.create(ForgeRegistries.CONTAINERS,"solarforge");
    public static final RegistryObject<ContainerType<SolarFurnaceContainer>> SOLAR_FURNACE_CONTAINER = CONTAINER_TYPE.register("solar_furnace_container",()-> IForgeContainerType.create(SolarFurnaceContainer::new));
}
