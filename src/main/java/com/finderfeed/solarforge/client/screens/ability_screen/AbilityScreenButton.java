package com.finderfeed.solarforge.client.screens.ability_screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.content.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.registries.sounds.SolarcraftSounds;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class AbilityScreenButton extends Button {

    private int initY = 0;
    private boolean bindingMode = false;
    public AbstractAbility ability;
    private ResourceLocation textureLocation;

    public AbilityScreenButton(AbstractAbility abstractAbility,int p_93721_, int p_93722_, int p_93723_, int p_93724_, OnPress p_93726_) {
        super(p_93721_, p_93722_, p_93723_, p_93724_, new TextComponent(""), p_93726_);
        this.ability = abstractAbility;
        this.textureLocation = abstractAbility != null ? new ResourceLocation(SolarForge.MOD_ID,"textures/abilities/"+abstractAbility.id+"_new.png")
        : new ResourceLocation(SolarForge.MOD_ID,"textures/abilities/no_ability.png");
        this.setInitY(p_93722_);
    }

    public AbilityScreenButton(AbstractAbility abstractAbility,int p_93728_, int p_93729_, int p_93730_, int p_93731_, OnPress p_93733_, OnTooltip p_93734_) {
        super(p_93728_, p_93729_, p_93730_, p_93731_, new TextComponent(""), p_93733_, p_93734_);
        this.ability = abstractAbility;
        this.textureLocation = abstractAbility != null ? new ResourceLocation(SolarForge.MOD_ID,"textures/abilities/"+abstractAbility.id+"_new.png")
                : new ResourceLocation(SolarForge.MOD_ID,"textures/abilities/no_ability.png");
        this.setInitY(p_93729_);
    }

    @Override
    public void renderButton(PoseStack matrices, int mousex, int mousey, float pTicks) {
        ClientHelpers.bindText(textureLocation);
        blit(matrices,x,y,0,0,width,height,width,height);
        if (isBindingMode()){
            RenderSystem.setShaderColor(1f,1f,1f,
                    (float)Math.sin(Util.getMillis()/500f)*0.1f+0.7f);
            float xf = width/50f;
            float yf = height/50f;
            Gui.fill(matrices,x + (int)(xf * 8),y + (int)(yf * 8),
                    x + width - (int)(xf * 8),y + height - (int)(yf * 8),0xff000000);
            RenderSystem.setShaderColor(1f,1f,1f,1f);
            drawCenteredString(matrices, Minecraft.getInstance().font,
                    new TranslatableComponent("solarcraft.bind_ability"),
                    x + width/2,y + height/2 - 5,
                    0xffffff);
        }

    }

    public void setInitY(int initY) {
        this.initY = initY;
    }

    public int getInitY() {
        return initY;
    }

    public void setBindingMode(boolean bindingMode) {
        this.bindingMode = bindingMode;
    }

    public boolean isBindingMode() {
        return bindingMode;
    }

    public void setAbility(AbstractAbility ability) {
        this.ability = ability;
        this.textureLocation = ability != null ? new ResourceLocation(SolarForge.MOD_ID,"textures/abilities/"+ability.id+"_new.png")
                : new ResourceLocation(SolarForge.MOD_ID,"textures/abilities/no_ability.png");
    }

    @Override
    public void playDownSound(SoundManager manager) {
        manager.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
    }
}
