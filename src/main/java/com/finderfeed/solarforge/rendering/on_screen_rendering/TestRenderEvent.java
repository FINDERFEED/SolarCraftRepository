package com.finderfeed.solarforge.rendering.on_screen_rendering;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TestRenderEvent {


    public int tick = 0;

    @SubscribeEvent
    public void render(RenderWorldLastEvent event){


    }

}
