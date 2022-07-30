package com.finderfeed.solarforge.events.hotkey_use_event;


import com.finderfeed.solarforge.SolarcraftClientRegistry;

import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.CastAbilityPacket;
import com.finderfeed.solarforge.magic.blocks.solar_forge_block.solar_forge_screen.SolarForgeAbilityConfigScreen;
import com.finderfeed.solarforge.packet_handler.packets.RequestAbilityScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.client.event.InputEvent;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class HotkeyEventListener {



    @SubscribeEvent
    public static void ListenToEvent(final InputEvent.KeyInputEvent event){


        if (SolarcraftClientRegistry.FIRST_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            SolarForgePacketHandler.INSTANCE.sendToServer(new CastAbilityPacket(1));

        }
        if (SolarcraftClientRegistry.SECOND_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            SolarForgePacketHandler.INSTANCE.sendToServer(new CastAbilityPacket(2));

        }
        if (SolarcraftClientRegistry.THIRD_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            SolarForgePacketHandler.INSTANCE.sendToServer(new CastAbilityPacket(3));

        }
        if (SolarcraftClientRegistry.FORTH_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            SolarForgePacketHandler.INSTANCE.sendToServer(new CastAbilityPacket(4));

        }
        if (SolarcraftClientRegistry.OPEN_GUI_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            Minecraft.getInstance().setScreen(new SolarForgeAbilityConfigScreen());

        }

        if (SolarcraftClientRegistry.GUI_ABILITY_BUY_SCREEN.isDown() && event.getAction() == GLFW.GLFW_PRESS){
            SolarForgePacketHandler.INSTANCE.sendToServer(new RequestAbilityScreen(false));
        }


    }


}
