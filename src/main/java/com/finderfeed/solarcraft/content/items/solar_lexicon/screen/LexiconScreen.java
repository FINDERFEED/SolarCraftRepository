package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.events.other_events.event_handler.SCClientModEventHandler;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreenWithPages;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class LexiconScreen extends DefaultScreenWithPages {


    private List<PostRender> postRenderEntries = new ArrayList<>();


    public LexiconScreen(){

    }


    @Override
    public int getPagesCount() {
        return 0;
    }

    @Override
    public void onPageChanged(int newPage) {

    }

    @Override
    public void render(GuiGraphics graphics, int mx, int my, float pticks) {
        super.render(graphics, mx, my, pticks);
        this.renderEscapeText(graphics);
    }

    @Override
    public void renderBackground(GuiGraphics p_283688_, int p_296369_, int p_296477_, float p_294317_) {

    }

    public void renderEscapeText(GuiGraphics graphics){
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        Minecraft mc = Minecraft.getInstance();
        int color = 0xccffffff;

        int y = mc.font.lineHeight / 2;
        matrices.scale(0.5f,0.5f,1);
        graphics.drawString(mc.font,Component.literal("ESC - ")
                        .append(Component.translatable("solarcraft.screens.lexicon_screen.esc")),
                y,mc.font.lineHeight * 2 + y,color);
        graphics.drawString(mc.font,SCClientModEventHandler.CLOSE_ALL_PAGES.getTranslatedKeyMessage().copy().append(Component.literal(" + ESC - ")
                        .append(Component.translatable("solarcraft.screens.lexicon_screen.x_esc"))),
                y,mc.font.lineHeight + y,color);
        graphics.drawString(mc.font,SCClientModEventHandler.MEMORIZE_AND_CLOSE.getTranslatedKeyMessage().copy().append(Component.literal(" + ESC - ")
                        .append(Component.translatable("solarcraft.screens.lexicon_screen.c_esc"))),
                y,0 + y,color);
        matrices.popPose();
    }

    protected void runPostEntries(GuiGraphics graphics, int mx, int my, float pticks){
        if (!postRenderEntries.isEmpty()){
            for (PostRender render : postRenderEntries){
                render.doRender(graphics,mx,my,pticks);
            }
            postRenderEntries.clear();
        }
    }

    protected void addPostRenderEntry(PostRender post){
        this.postRenderEntries.add(post);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    public void addTab(int idx,ItemStack item,int x, int y, Supplier<LexiconScreen> screen, Component cmp){
        this.addRenderableWidget(new ItemStackTabButton(x,y + idx * 20,16,16,(btn)->{
            Minecraft.getInstance().setScreen(screen.get());
        },item,0.7f,((button, graphics, mouseX, mouseY) -> {
            if (cmp != null) {
                graphics.renderTooltip(Minecraft.getInstance().font, cmp, mouseX, mouseY);
            }
        })));
    }

    public void addInfo(int idx,int x,int y,Component cmp){
        this.addRenderableWidget(new InfoButton(x + 1,y + idx * 20,14,14,((button, graphics, mouseX, mouseY) -> {
            graphics.renderTooltip(font,
                    font.split(cmp,150),
                    mouseX,mouseY);
        })));
    }
}
