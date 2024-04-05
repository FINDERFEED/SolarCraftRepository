package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarCraftButton;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class ToggleSpoilersScreen extends DefaultScreen {

    private List<FormattedCharSequence> sequences;

    @Override
    protected void init() {
        sequences = font.split(Component.translatable("solarcraft.screens.spoiler_screen.spoilers"),this.getScreenWidth() - 10);
        super.init();
        SolarCraftButton enable = new SolarCraftButton(relX + this.getScreenWidth() - 85,relY + this.getScreenHeight() - 24,65,15, Component.translatable("solarcraft.screens.spoiler_screen.enable_spoilers"),(btn)->{
            SolarLexiconScreen.SPOILER_MODE = true;
            Minecraft.getInstance().setScreen(null);
        });
        SolarCraftButton disable = new SolarCraftButton(relX + 20,relY + this.getScreenHeight() - 24,65,15,Component.translatable("solarcraft.screens.spoiler_screen.disable_spoilers"),(btn)->{
            SolarLexiconScreen.SPOILER_MODE = false;
            Minecraft.getInstance().setScreen(null);
        });
        this.addRenderableWidget(enable);
        this.addRenderableWidget(disable);

    }


    @Override
    public void render(GuiGraphics graphics, int mx, int my, float pticks) {
        RenderingTools.renderTextField(graphics.pose(),relX,relY,this.getScreenWidth(),this.getScreenHeight());
        graphics.drawCenteredString(font,Component.translatable("solarcraft.word.warning"),
                relX + this.getScreenWidth() / 2,relY + 5,SolarLexiconScreen.TEXT_COLOR);
        int y = 9;
        for (var text : sequences){
            graphics.drawCenteredString(font,text,relX + this.getScreenWidth()/2,relY + 15 + y,SolarLexiconScreen.TEXT_COLOR);
            y += font.lineHeight;
        }
        super.render(graphics, mx, my, pticks);
    }

    @Override
    public int getScreenWidth() {
        return 200;
    }

    @Override
    public int getScreenHeight() {
        return 59 + sequences.size() * font.lineHeight;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
