package com.finderfeed.solarcraft.content.items.solar_lexicon;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarcraft.misc_things.IScrollable;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.items.SlotItemHandler;
import org.lwjgl.glfw.GLFW;

public class SolarLexiconContScreen extends AbstractContainerScreen<SolarLexiconContainer> implements IScrollable {

    public static final ResourceLocation LOC = new ResourceLocation("solarcraft","textures/gui/solar_lexicon_inventory.png");

    public int relX;
    public int relY;
    private int currentRow = 1;


    public SolarLexiconContScreen(SolarLexiconContainer p_i51105_1_, Inventory p_i51105_2_, Component p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        if (key == GLFW.GLFW_KEY_UP || key == GLFW.GLFW_KEY_W){
            if (currentRow > 1){
                currentRow--;
                for (SlotItemHandler slot : menu.getScrollableSlots()){
                    slot.y += 18;
                }
            }
        }else if (key == GLFW.GLFW_KEY_S || key == GLFW.GLFW_KEY_DOWN){
            if (currentRow < menu.getMaxRows()) {
                currentRow++;
                for (SlotItemHandler slot : menu.getScrollableSlots()) {
                    slot.y -= 18;
                }
            }
        }

        return super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    public void performScroll(int keyCode) {

    }

    @Override
    protected void slotClicked(Slot slot, int p_97779_, int p_97780_, ClickType type) {
        super.slotClicked(slot, p_97779_, p_97780_, type);
    }

    @Override
    protected void init() {
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2 - 3;
        this.relY = (height - 218*scale)/2/scale;
        super.init();
        int a = 0;
        if (scale != 2){
            a = 1;
        }
        InfoButton button = new InfoButton(relX + 180 + a,relY + 40,12,12,(btn,matrices,x,y)->{
           this.renderTooltip(matrices,font.split(Component.translatable("solarcraft.lexicon_inventory_description"),200),x,y,font);
        });
        addRenderableWidget(button);
    }

    @Override
    public void render(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);

        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.renderTooltip(p_230430_1_,p_230430_2_,p_230430_3_);
    }

    @Override
    protected void renderBg(PoseStack matrices, float partialTicks, int mousex, int mousey) {
        ClientHelpers.bindText(LOC);

        int a = 0;
        if (minecraft.getWindow().getGuiScale() != 2){
            a = 1;
        }

        blit(matrices,relX+3+a,relY+33,0,0,256,256,256,256);
    }



    @Override
    public int getCurrentScrollX() {
        return 0;
    }

    @Override
    public int getCurrentScrollY() {
        return 0;
    }
}
