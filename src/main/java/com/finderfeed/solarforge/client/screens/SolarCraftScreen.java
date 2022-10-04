package com.finderfeed.solarforge.client.screens;

import com.finderfeed.solarforge.local_library.other.CanTick;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class SolarCraftScreen extends Screen {
    public int relX;
    public int relY;
    private List<Runnable> postRenderEntries = new ArrayList<>();
    private List<CanTick> tickables = new ArrayList<>();

    public SolarCraftScreen() {
        super(Component.literal(""));
    }


    @Override
    protected void init() {
        super.init();
        this.tickables.clear();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2 - 30;
        this.relY = (height - 218*scale)/2/scale;
    }

    @Override
    public void tick() {
        super.tick();
        tickables.forEach(CanTick::tick);
    }


    public int getRelX() {
        return relX;
    }

    public int getRelY() {
        return relY;
    }

    protected void runPostEntries(){
        if (!postRenderEntries.isEmpty()){
            postRenderEntries.forEach(Runnable::run);
            postRenderEntries.clear();
        }
    }

    protected void addPostRenderEntry(Runnable post){
        this.postRenderEntries.add(post);
    }
}
