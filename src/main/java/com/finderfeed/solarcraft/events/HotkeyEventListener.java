package com.finderfeed.solarcraft.events;


import com.finderfeed.solarcraft.SolarcraftClientInit;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.client.SunShardPuzzleScreen;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template.Puzzle;
import com.finderfeed.solarcraft.content.items.solar_wand.client.WandModeSelectionScreen;
import com.finderfeed.solarcraft.helpers.ClientHelpers;

import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.CastAbilityPacket;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.client.event.InputEvent;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class HotkeyEventListener {



    @SubscribeEvent
    public static void ListenToEvent(final InputEvent.Key event){
        if (Minecraft.getInstance().screen != null) return;

        if (SolarcraftClientInit.FIRST_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){
            Minecraft.getInstance().setScreen(new SunShardPuzzleScreen(Puzzle.createTestPuzzle(),new BlockPos(0,0,0)));
            SCPacketHandler.INSTANCE.sendToServer(new CastAbilityPacket(1));
        }
        if (SolarcraftClientInit.SECOND_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            SCPacketHandler.INSTANCE.sendToServer(new CastAbilityPacket(2));

        }
        if (SolarcraftClientInit.THIRD_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            SCPacketHandler.INSTANCE.sendToServer(new CastAbilityPacket(3));

        }
        if (SolarcraftClientInit.FORTH_ABILITY_KEY.isDown() && event.getAction() == GLFW.GLFW_PRESS){

            SCPacketHandler.INSTANCE.sendToServer(new CastAbilityPacket(4));

        }

        if (SolarcraftClientInit.GUI_ABILITY_BUY_SCREEN.isDown() && event.getAction() == GLFW.GLFW_PRESS){
            ClientHelpers.requestAbilityScreen(false);
        }

        if (SolarcraftClientInit.GUI_WAND_MODE_SELECTION.isDown() && event.getAction() == GLFW.GLFW_PRESS
        && Minecraft.getInstance().player != null && Minecraft.getInstance().player.getMainHandItem().is(SolarcraftItems.SOLAR_WAND.get())){
            Minecraft.getInstance().setScreen(new WandModeSelectionScreen());
        }


    }


}
