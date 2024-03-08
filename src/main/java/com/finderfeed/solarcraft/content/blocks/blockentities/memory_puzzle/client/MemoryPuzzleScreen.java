package com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle.client;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle.MemoryPuzzle;
import com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle.MemoryPuzzleValuePacket;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

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
        this.addRayButton(49,39,1,SunRayButton.LEFT_UP);
        this.addRayButton(39,49,0,SunRayButton.LEFT_UP);

        this.addRayButton(49,87,6,SunRayButton.LEFT_DOWN);
        this.addRayButton(39,77,7,SunRayButton.LEFT_DOWN);

        this.addRayButton(77,39,2,SunRayButton.RIGHT_UP);
        this.addRayButton(87,49,3,SunRayButton.RIGHT_UP);

        this.addRayButton(77,87,5,SunRayButton.RIGHT_DOWN);
        this.addRayButton(87,77,4,SunRayButton.RIGHT_DOWN);
    }

    private void addRayButton(int x,int y,int id,ResourceLocation texture){
        SunRayButton button = new SunRayButton(relX + x,relY + y,id,texture,(btn)->{
           if (!buttonsBlocked) {
               MemoryPuzzleValuePacket valuePacket = new MemoryPuzzleValuePacket(tilePos,id);
               FDPacketUtil.sendToServer(valuePacket);
               buttonsBlocked = true;
           }
        });
        addRenderableWidget(button);
        buttons.put(id,button);
    }

    @Override
    public void tick() {
        super.tick();
        this.closeIfFaraway();
        this.processStages();
        for (SunRayButton button : buttons.values()){
            button.tick();
        }
    }

    private void processStages(){
        if (currentStage == null){
            if (!stages.isEmpty()) {
                currentStage = stages.poll();
                currentStage.init(this);
                currentStage.tick(this);
            }
        } else {
            if (currentStage.ended){
                currentStage = null;
            }else{
                currentStage.tick(this);
            }
        }
    }

    private void closeIfFaraway(){
        Player player = Minecraft.getInstance().player;
        if (player.position().distanceTo(Helpers.getBlockCenter(tilePos)) > 10){
            Minecraft.getInstance().setScreen(null);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mx, int my, float pticks) {
        PoseStack matrices = graphics.pose();
        graphics.fill(0,0,1000,1000,0xaa000000);
        matrices.pushPose();
        ClientHelpers.bindText(GUI);
        RenderingTools.blitWithBlend(matrices,this.relX,this.relY,0,0,this.getScreenWidth(),this.getScreenHeight(),256,256,0,1f);
        matrices.popPose();
        super.render(graphics, mx, my, pticks);
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
        if (values != null){
            if (!wasTrue) {
                this.stages.add(new ValueClickedStage(false, 10, value));
                ClientHelpers.playsoundInEars(SoundEvents.VILLAGER_NO,1f,1f);
            }else{
                this.stages.add(new ValueClickedStage(true, 30, -1));
                ClientHelpers.playsoundInEars(SoundEvents.EXPERIENCE_ORB_PICKUP,1f,1f);
            }
            this.stages.add(new ShowValuesStage(values,10));
        }else{
            this.stages.add(new ValueClickedStage(wasTrue,10,value));
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
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
                screen.buttonsBlocked = false;
            }else{
                screen.buttonsBlocked = true;
            }
        }

        @Override
        void init(MemoryPuzzleScreen screen) {
            if (value != -1) {
                SunRayButton button = screen.buttons.get(value);
                if (button != null) {
                    button.glowWithMode(correct ? SunRayButton.Mode.GREEN : SunRayButton.Mode.RED, tick);
                }
            }else{
                for (SunRayButton b : screen.buttons.values()){
                    b.glowWithMode(correct ? SunRayButton.Mode.GREEN : SunRayButton.Mode.RED, tick);
                }
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
