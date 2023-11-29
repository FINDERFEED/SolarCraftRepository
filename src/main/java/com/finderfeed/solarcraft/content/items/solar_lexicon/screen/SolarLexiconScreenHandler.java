package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

import java.util.Stack;

public class SolarLexiconScreenHandler {

    public Stack<LexiconScreen> lexiconScreens = new Stack<>();


    public SolarLexiconScreenHandler(){}


    public void memorizeAndClose(){
        Minecraft.getInstance().setScreen(null);
    }
    public void escapePressed(){
        Screen current = Minecraft.getInstance().screen;
        LexiconScreen lastScreen = this.getLastScreen();
        if (lastScreen == null || (!(current instanceof LexiconScreen))) return;
        boolean x = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_X);
        boolean c = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_C);
        if (lexiconScreens.size() > 1 && !(x || c)){
            lexiconScreens.pop();
            Minecraft.getInstance().setScreen(this.getLastScreen());
        }else {
            if (x || lexiconScreens.size() == 1) {
                lexiconScreens.clear();
                Minecraft.getInstance().setScreen(null);
            }else if (c){
                this.memorizeAndClose();
            }
        }
    }

    public void open(){
        LexiconScreen screen = this.getLastScreen();
        if (screen != null){
            Minecraft.getInstance().setScreen(screen);
        }else{
            Minecraft.getInstance().setScreen(new SolarLexiconScreen());
        }
    }


    public void onNewScreenOpened(LexiconScreen screen){
        if (screen != this.getLastScreen()) {
            this.lexiconScreens.add(screen);
        }
    }
    public void onLogout(){
        lexiconScreens.clear();
    }

    public LexiconScreen getLastScreen(){
        if (lexiconScreens.isEmpty()){
            return null;
        }else{
            return lexiconScreens.peek();
        }
    }

}
