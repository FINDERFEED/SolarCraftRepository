package com.finderfeed.solarforge.SolarAbilities.screens;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.AbstractAbility;
import com.finderfeed.solarforge.for_future_library.custom_registries.RegistryDelegate;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen.SolarForgeButton;
import com.finderfeed.solarforge.registries.SolarcraftRegistries;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class AbilityBuyScreen extends Screen {

    public int relX;
    public int relY;
    private final ResourceLocation LOC = new ResourceLocation("solarforge","textures/gui/ability_buy_screen.png");

    public AbilityBuyScreen() {
        super(new TextComponent(""));
    }


    @Override
    protected void init() {
        super.init();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;

        int count = 0;
        int maxPages = 0;

        for (AbstractAbility ability : RegistryDelegate.getAllRegisteredEntriesFor(SolarcraftRegistries.ABILITIES)){

            int yOffset = (count%7)*20;
            int xOffset = ((int)Math.floor((float)count/7))*70;


            addRenderableWidget(new SolarForgeButton(relX-10+xOffset,relY+35+yOffset,65,15,new TranslatableComponent("solarcraft_button."+ability.id),
                    (button)->{

                    },
                    (button,matrices,mousex,mousey)->{
                        ClientHelpers.bindText(new ResourceLocation("solarforge","textures/abilities/"+ability.id+".png"));
                        blit(matrices,relX-20,relY-33+20,0,0,38,38,38,38);
                        drawString(matrices, Minecraft.getInstance().font, new TranslatableComponent("name."+ability.id),relX-20+39 ,relY -30+20,0xffffff);
                        drawString(matrices, Minecraft.getInstance().font, new TranslatableComponent("desc."+ability.id),relX-20+39 ,relY -21+20,0xffffff);
                        drawString(matrices, Minecraft.getInstance().font, new TranslatableComponent("cost."+ability.id),relX-20+39 ,relY -12+20,0xffffff);
                    }
            ));
            count++;
        }
    }




    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        ClientHelpers.bindText(LOC);
        blit(matrices,relX-20,relY+5+20,0,0,256,256);
        super.render(matrices, mousex, mousey, partialTicks);
    }
}
