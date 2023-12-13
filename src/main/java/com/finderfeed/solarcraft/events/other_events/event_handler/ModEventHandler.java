package com.finderfeed.solarcraft.events.other_events.event_handler;


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.compat.curios.Curios;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.fml.event.lifecycle.InterModEnqueueEvent;
import org.apache.logging.log4j.Level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandler {

    @SubscribeEvent
    public static void interModComms(InterModEnqueueEvent event){
        if (ModList.get().isLoaded("curios")){
            Curios.addCuriosSlots(event);
        }
    }



}
