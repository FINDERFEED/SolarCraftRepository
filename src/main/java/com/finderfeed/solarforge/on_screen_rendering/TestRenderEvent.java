package com.finderfeed.solarforge.on_screen_rendering;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TestRenderEvent {


    public int tick = 0;

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Pre event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
//            RenderingTools.renderTest(event, tick);
//
//
//            tick++;

        }
    }
}
