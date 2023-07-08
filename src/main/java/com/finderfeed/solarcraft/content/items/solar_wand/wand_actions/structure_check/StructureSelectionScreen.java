package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.structure_check;


import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.local_library.client.screens.RadialMenu;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.SetREDrainTypePacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StructureSelectionScreen extends DefaultScreen {

    private final List<MultiblockStructure> structures;
    private BlockPos checkPos;
    private RadialMenu menu;

    public StructureSelectionScreen(BlockPos checkPos, List<MultiblockStructure> structures){
        this.structures = structures;
        this.checkPos = checkPos;
    }


    @Override
    public void init(){
        super.init();
        Minecraft minecraft = Minecraft.getInstance();
        RadialMenu.RadialMenuShaderSettings settings =
                new RadialMenu.RadialMenuShaderSettings(0,0.35f,0.1f,
                        new float[]{0.3f,0.3f,0.3f,0.9f},
                        new float[]{0.6f,0.6f,0.6f,0.9f},
                        0.05f
                );
        List<RadialMenu.RadialMenuSection> sections = new ArrayList<>();

        for (int i = 0; i < structures.size(); i++){
            MultiblockStructure structure  = structures.get(i);
            try {
                if (!ProgressionHelper.doPlayerHasFragment(minecraft.player, AncientFragment.STRUCTURE_FRAGMENTS.get(structure))) {
                    continue;
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            RadialMenu.RadialMenuSection section = new RadialMenu.RadialMenuSection(
                    ()->{
                        processStructure(checkPos,structure,3);
                    },
                    (graphics, x, y) -> {
                        RenderingTools.renderScaledGuiItemCentered(
                                graphics,
                                structure.mainBlock.getBlock()
                                        .asItem().getDefaultInstance(),
                                x,y,1f,300
                        );
                    },
                    (graphics, x, y) -> {
                        graphics.renderTooltip(font, Component.translatable("solarcraft.structure." + structure.getId()),(int)x,(int)y);
                    }
            );
            sections.add(section);
        }

        RadialMenu menu = new RadialMenu(settings,relX,relY,100,
                sections
        );
        this.menu = menu;
    }



    public static void processStructure(BlockPos checkPos, MultiblockStructure structure,int blocksToShow){
        Minecraft minecraft = Minecraft.getInstance();
        List<MultiblockStructure.IncorrectState> incStates = structure.checkWithInfo(Minecraft.getInstance().level,checkPos,true);
        if (incStates.isEmpty()){
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Structure is correct")
                    .withStyle(ChatFormatting.GREEN));
        }else{
            for (int i = 0; i < Math.min(blocksToShow,incStates.size());i++){
                processIncorrectState(incStates.get(i));
            }
            if (incStates.size() > blocksToShow){
                minecraft.player.sendSystemMessage(Component.literal("And more...").withStyle(ChatFormatting.RED));
            }
        }
    }

    private static void processIncorrectState(MultiblockStructure.IncorrectState incState){
        Minecraft minecraft = Minecraft.getInstance();
        if (incState.tag() == null){
            String message1 = "Structure incorrect at: " + ChatFormatting.GOLD + incState.atPos();
            String message2 = "BlockState should be: "  + ChatFormatting.GOLD +incState.correct();
            String message3 = "Now: " + ChatFormatting.GOLD +  incState.incorrect();
            minecraft.player.sendSystemMessage(Component.literal(message1)
                    .withStyle(ChatFormatting.RED));
            minecraft.player.sendSystemMessage(Component.literal(message2)
                    .withStyle(ChatFormatting.RED));
            minecraft.player.sendSystemMessage(Component.literal(message3)
                    .withStyle(ChatFormatting.RED));
        }else{
            String message1 = "Structure incorrect at: " + ChatFormatting.GOLD + incState.atPos();
            String message2 = "BlockState is not in " + ChatFormatting.GOLD + incState.tag() + " tag";
            minecraft.player.sendSystemMessage(Component.literal(message1)
                    .withStyle(ChatFormatting.RED));
            minecraft.player.sendSystemMessage(Component.literal(message2)
                    .withStyle(ChatFormatting.RED));
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mx, int my, float pTicks) {
        super.render(graphics, mx, my, pTicks);
        menu.render(graphics,mx,my,pTicks,0);
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_94697_) {
        if (menu.getSectionUnderMouse((float)x,(float)y) != -1) {
            menu.mouseClicked((float) x, (float) y);
            Minecraft.getInstance().setScreen(null);
        }

        return super.mouseClicked(x, y, p_94697_);
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public int getScreenWidth() {
        return 0;
    }

    @Override
    public int getScreenHeight() {
        return 0;
    }

}
