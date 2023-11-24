package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreenWithPages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
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

    protected void runPostEntries(GuiGraphics graphics,int mx,int my,float pticks){
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
