package com.finderfeed.solarforge.content.blocks.blockentities.containers.screens;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.client.particles.screen.RuneTileParticle;
import com.finderfeed.solarforge.client.screens.buttons.RuneButtonRunicTable;
import com.finderfeed.solarforge.local_library.client.particles.ScreenParticlesRenderHandler;
import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.content.blocks.blockentities.containers.RunicTableContainer;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.RunePattern;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.RunicTablePacket;
import com.finderfeed.solarforge.client.rendering.item_renderers.TransparentItemrenderer;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.ProgressionHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.phys.Vec2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RunicTableContainerScreen extends AbstractContainerScreen<RunicTableContainer> {
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarforge","textures/gui/runic_table_gui_new.png");
    public final ResourceLocation WIN_POS = new ResourceLocation("solarforge","textures/misc/win_positions.png");
    int relX = 0;
    int relY = 0;


    public RunePattern pattern;


    public RunicTableContainerScreen(RunicTableContainer p_i51105_1_, Inventory p_i51105_2_, Component p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
        this.inventoryLabelY+=2;
        this.titleLabelY-=25;
    }


    @Override
    protected void init() {

        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;
        super.init();
        RunePattern ptrn = new RunePattern(Minecraft.getInstance().player);
        this.pattern = ptrn;
        ItemStack stack =menu.inventory.getStackInSlot(0);
        if (!stack.isEmpty() && stack.getTagElement(ProgressionHelper.TAG_ELEMENT) == null) {
            initPattern();
        }


    }

    private void initPattern(){
        if (!menu.hideRuneButtons ) {
            this.pattern = new RunePattern(ClientHelpers.getClientPlayer());
            int[][] pattern = this.pattern.getPattern();

            for (int h = 0; h < pattern.length; h++) {
                for (int l = 0; l < pattern[h].length; l++) {
                    int finalH = h;
                    int finalL = l;
                    int rune = pattern[finalH][finalL];
                    if (rune != RunePattern.OPENED) {
                        this.addRenderableWidget(new RuneButtonRunicTable(relX + 98 + l * 15, relY + 11 + h * 15, 15, 15,
                                (button) -> {
                                    SolarForgePacketHandler.INSTANCE.sendToServer(new RunicTablePacket(finalL, finalH, menu.tile.getBlockPos()));
                                    RuneButtonRunicTable v = ((RuneButtonRunicTable) button);

                                    hideButton(this.pattern, v, rune, finalL, finalH);
                                    if (this.pattern.isCompleted()){
                                        this.pattern = new RunePattern(ClientHelpers.getClientPlayer());
                                        List<Widget> toRemove = new ArrayList<>();
                                        for (Widget w : this.renderables){
                                            if (w instanceof RuneButtonRunicTable){
                                                toRemove.add(w);
                                            }
                                        }
                                        for (Widget w : toRemove){
                                            this.removeWidget(((RuneButtonRunicTable)w));
                                        }

                                    }
                                }, (button, matrices, mousex, mousey) -> {
                        }, pattern[h][l]));
                    }
                }
            }


        }
    }

    private void hideButton(RunePattern pattern,RuneButtonRunicTable v,int rune,int x,int y){
        for (int i = 0; i < menu.inventory.getSlots(); i++) {
            if (menu.inventory.getStackInSlot(i).getItem() == ProgressionHelper.RUNES[rune]) {
                v.turnedOff = true;
                if (pattern.isWinPosition(x,y)){
                    ClientHelpers.playsoundInEars(SoundEvents.EXPERIENCE_ORB_PICKUP,1,1);
                }
                this.pattern.setOpened(x,y);
                break;
            }
        }
        if (!v.turnedOff) {
            if (ClientHelpers.getClientPlayer().getInventory().contains(ProgressionHelper.RUNES[rune].getDefaultInstance())) {
                if (pattern.isWinPosition(x,y)){
                    ClientHelpers.playsoundInEars(SoundEvents.EXPERIENCE_ORB_PICKUP,1,1);
                }
                v.turnedOff = true;
                this.pattern.setOpened(x,y);
            }
        }
        if (v.turnedOff) {
            Random random = new Random();
            for (int i = 0; i < 3 + random.nextInt(4);i++){
                random = new Random();
                Vec2 vec2 = new Vec2(random.nextFloat()* FDMathHelper.randomPlusMinus(),random.nextFloat()*-1).add(new Vec2(0,-2.5f));

                RuneTileParticle particle = new RuneTileParticle(60,v.x + 11,v.y + 11,vec2.x,vec2.y,0,1,255,255,255,255);
                particle.setSize(6.5);
                particle.setRotationPerTick(FDMathHelper.randomPlusMinus()*20);
                ScreenParticlesRenderHandler.addParticle(particle);
            }
            v.active = false;
            v.visible = false;
        }

    }


    @Override
    public void onClose() {
        super.onClose();
        ScreenParticlesRenderHandler.clearAllParticles();
    }
    public void forceUpdate(RunePattern pattern, boolean s){
        menu.hideRuneButtons = s;
    }

    @Override
    protected void slotClicked(Slot p_97778_, int p_97779_, int p_97780_, ClickType p_97781_) {
        super.slotClicked(p_97778_, p_97779_, p_97780_, p_97781_);
        ItemStack stack = menu.inventory.getStackInSlot(0);
        if (!stack.isEmpty() && stack.getTagElement(ProgressionHelper.TAG_ELEMENT) == null){
            int count = 0;
            for (AbstractWidget b : ClientHelpers.getScreenButtons(this)){

                if (b instanceof RuneButtonRunicTable v){
                    if (!v.turnedOff) {
                        b.active = true;
                        b.visible = true;
                        count++;
                    }
                }

            }
            if (count == 0){
                initPattern();
            }
        }else{
            for (AbstractWidget b : ClientHelpers.getScreenButtons(this)){
                if (b instanceof RuneButtonRunicTable){
                    b.active = false;
                    b.visible = false;
                }
            }
        }
    }

    @Override
    public void render(PoseStack stack, int rouseX, int rouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, rouseX, rouseY, partialTicks);
        this.renderTooltip(stack,rouseX,rouseY);
    }

    @Override
    protected void renderBg(PoseStack matrices, float partialTicks, int mousex, int mousey) {

        ClientHelpers.bindText(MAIN_SCREEN);
        int scale = (int) minecraft.getWindow().getGuiScale();
        int a = 1;
        if (scale == 2) {
            a = 0;
        }
        blit(matrices,relX+a+3,relY+4,0,0,256,256,256,256);
        if (menu.hideRuneButtons){
            drawCenteredString(matrices,font,new TranslatableComponent("solarcraft.no_fragments_available"),relX+135,relY+40,0xffffff);
            drawCenteredString(matrices,font,new TranslatableComponent("solarcraft.no_fragments_available2"),relX+135,relY+48,0xffffff);

        }else{
            ItemStack stack = menu.inventory.getStackInSlot(0);
            if (pattern != null && !menu.inventory.getStackInSlot(0).isEmpty() && stack.getTagElement(ProgressionHelper.TAG_ELEMENT) == null){
                int[] winPos = pattern.getXyWinPositions();
                ClientHelpers.bindText(WIN_POS);
                matrices.pushPose();
                for (int i = 0; i < winPos.length;i+=2){
                    int x = relX + 98 + winPos[i]*15;
                    int y = relY + 11 + winPos[i+1]*15;

                    blit(matrices,x,y,0,0,15,15,15,15);

                }
                matrices.popPose();
            }
        }

    }

    private void renderItem(ItemStack stack,int x,int y){

        TransparentItemrenderer.INSTANCE.renderGuiItem(stack,relX+x,relY+y);
            //Minecraft.getInstance().getItemRenderer().renderGuiItem(stack,relX+x,relY+y);
    }


}
