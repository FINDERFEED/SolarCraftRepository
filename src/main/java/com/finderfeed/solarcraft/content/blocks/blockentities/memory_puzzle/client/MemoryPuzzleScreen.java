package com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle.client;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle.MemoryPuzzle;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class MemoryPuzzleScreen extends DefaultScreen {

    public static final ResourceLocation GUI = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/memory_puzzle/main.png");
    private BlockPos tilePos;
    private Map<Integer,SunRayButton> buttons = new HashMap<>();
    private boolean buttonsBlocked = false;
    private Queue<Stage> stages;
    private Stage currentStage;
    public MemoryPuzzleScreen(BlockPos tilePos, Stack<Integer> values){
        this.tilePos = tilePos;
        this.stages = new ArrayDeque<>();
        stages.add(new DelayStage(50));
        stages.add(new ShowValuesStage(values,20));
    }


    @Override
    protected void init() {
        super.init();

    }

    @Override
    public void tick() {
        super.tick();
        for (SunRayButton button : buttons.values()){
            button.tick();
        }
        if (!stages.isEmpty()){
            if (currentStage == null){
                currentStage = stages.poll();
                currentStage.init(this);
            }
            currentStage.tick(this);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mx, int my, float pticks) {
        super.render(graphics, mx, my, pticks);
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        ClientHelpers.bindText(GUI);
        RenderingTools.blitWithBlend(matrices,this.relX,this.relY,0,0,this.getScreenWidth(),this.getScreenHeight(),256,256,0,1f);
        matrices.popPose();
    }

    @Override
    public int getScreenWidth() {
        return 126;
    }

    @Override
    public int getScreenHeight() {
        return 126;
    }

    public void valuePushed(int value,Stack<Integer> values,boolean wasTrue){
        this.stages.add(new ValueClickedStage(wasTrue,20,value));
        if (values != null){
            this.stages.add(new ShowValuesStage(values,20));
        }
    }

    static class DelayStage extends Stage {
        DelayStage(int delay){
            this.tick = delay;
        }
        @Override
        void tick(MemoryPuzzleScreen screen) {
            if (tick-- <= 0){
                ended = true;
            }
        }

        @Override
        void init(MemoryPuzzleScreen screen) {

        }
    }

    static class ValueClickedStage extends Stage {
        private int value;
        private boolean correct;
        public ValueClickedStage(boolean correct,int flickerTime,int value){
            this.value = value;
            this.tick = flickerTime;
            this.correct = correct;
        }

        @Override
        void tick(MemoryPuzzleScreen screen) {
            if (tick-- <= 0){
                ended = true;
            }
        }

        @Override
        void init(MemoryPuzzleScreen screen) {
            SunRayButton button = screen.buttons.get(value);
            if (button != null){
                button.glowWithMode(correct ? SunRayButton.Mode.GREEN : SunRayButton.Mode.RED,tick);
            }
        }
    }

    static class ShowValuesStage extends Stage {
        private Stack<Integer> values;
        private Integer currentValue = null;
        private int flickerTime;
        ShowValuesStage(Stack<Integer> values,int flickerTime){
            this.values = values;
            this.flickerTime = flickerTime;
        }

        @Override
        void tick(MemoryPuzzleScreen screen) {
            screen.buttonsBlocked = true;
            if (tick-- <= 0){
                if (values.isEmpty()){
                    screen.buttonsBlocked = false;
                    ended = true;
                }else{
                    currentValue = values.pop();
                    tick = flickerTime;
                }
            } else if (currentValue != null) {
                SunRayButton button = screen.buttons.get(currentValue);
                if (button != null){
                    currentValue = null;
                    button.glowWithMode(SunRayButton.Mode.GREEN,flickerTime);
                }
            }
        }

        @Override
        void init(MemoryPuzzleScreen screen) {

        }
    }
    static abstract class Stage{
        protected boolean ended = false;
        protected int tick = 0;
        abstract void tick(MemoryPuzzleScreen screen);
        abstract void init(MemoryPuzzleScreen screen);
    }
}
