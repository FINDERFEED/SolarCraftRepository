package com.finderfeed.solarcraft.registries.animations;


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnimationLoadEventHandler {

    @SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent event){
        event.addListener(AnimationReloadableResourceListener.INSTANCE);
    }

    @SubscribeEvent
    public static void onDataSync(OnDatapackSyncEvent event){
        if (event.getPlayer() != null){
            AnimationReloadableResourceListener.INSTANCE.sendAnimationsPacket(event.getPlayer());
        }else{
            for (ServerPlayer player : event.getPlayerList().getPlayers()){
                AnimationReloadableResourceListener.INSTANCE.sendAnimationsPacket(player);
            }
        }
    }

}
