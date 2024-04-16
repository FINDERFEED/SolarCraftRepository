package com.finderfeed.solarcraft.events.other_events.event_handler;


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.compat.curios.Curios;
import com.finderfeed.solarcraft.config.LegacyJsonConfig;
import com.finderfeed.solarcraft.config.JsonFragmentsHelper;
import com.finderfeed.solarcraft.config.enchanter_config.EnchanterConfigInit;
import com.finderfeed.solarcraft.config.json_config.JsonConfig;
import com.finderfeed.solarcraft.config.json_config.reflective.ReflectiveJsonConfig;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool.InfusingStand;
import com.finderfeed.solarcraft.content.items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarcraft.registries.LegacyConfigRegistry;
import com.finderfeed.solarcraft.registries.SCConfigs;
import com.finderfeed.solarcraft.registries.Tags;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import com.finderfeed.solarcraft.registries.wand_actions.SolarCraftWandActionRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.TierSortingRegistry;
import net.neoforged.neoforge.common.world.chunk.RegisterTicketControllersEvent;
import net.neoforged.neoforge.common.world.chunk.TicketController;

import java.util.List;

@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandler {


    public static TicketController TICKET_CONTROLLER = new TicketController(new ResourceLocation(SolarCraft.MOD_ID,"ticker_controller"));


    @SubscribeEvent
    public static void registerTicketController(RegisterTicketControllersEvent event){
        event.register(TICKET_CONTROLLER);
    }

    @SubscribeEvent
    public static void interModComms(InterModEnqueueEvent event){
        if (ModList.get().isLoaded("curios")){
            Curios.addCuriosSlots(event);
        }
    }

    @SubscribeEvent
    public static void commonSetupEvent(FMLCommonSetupEvent event){

        SCConfigs.init();
        for (JsonConfig config : SCConfigs.CONFIG_REGISTRY.values()){
            if (config instanceof ReflectiveJsonConfig c){
                c.memorizeDefaultValues();
            }
        }

        JsonFragmentsHelper.setupJSON();
        EnchanterConfigInit.setupJSON();
        for (LegacyJsonConfig post : LegacyConfigRegistry.POST_LOAD_CONFIGS.values()){
            post.init();
        }
        Tags.init();
        AbilitiesRegistry.init();
        SolarCraftWandActionRegistry.init();
        TierSortingRegistry.registerTier(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,new ResourceLocation(SolarCraft.MOD_ID,"illidium"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,new ResourceLocation(SolarCraft.MOD_ID,"qualadium"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,new ResourceLocation(SolarCraft.MOD_ID,"charged_qualadium"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.SOLAR_GOD_TOOL_TIER,new ResourceLocation(SolarCraft.MOD_ID,"solar_god"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.DIVINE_TIER,new ResourceLocation(SolarCraft.MOD_ID,"divine"), List.of(Tiers.DIAMOND),List.of());



        NeoForge.EVENT_BUS.addListener(InfusingStand::placeBlockEvent);
        event.enqueueWork(()->{

        });
    }



}
