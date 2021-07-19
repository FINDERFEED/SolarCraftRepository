package com.finderfeed.solarforge.rendering.on_screen_rendering;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TestRenderEvent {


    public int tick = 0;

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Pre event){


    }


    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Post event){

    }
}
