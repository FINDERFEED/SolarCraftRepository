package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.content.blocks.primitive.ProgressionBlock;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackButton;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ProgressionBlocksScreen extends ScrollableLexiconScreen{

    private int countPerRow;
    private int sizeOfBlock;

    private int betweenBlocks;
    private int offsetsFromEdges = 5;
    public List<AbstractWidget> progressionBlocks = new ArrayList<>();

    @Override
    protected void init() {
        super.init();
        var list = ProgressionBlock.PROGRESSION_BLOCKS;
        int iter = 0;
        offsetsFromEdges = 5;
        sizeOfBlock = 48;
        int remainingPlace = this.getScreenWidth() - offsetsFromEdges * 2;
        countPerRow = remainingPlace / sizeOfBlock - 1;
        betweenBlocks = (remainingPlace - ( (countPerRow) * sizeOfBlock)) /  (countPerRow);


        progressionBlocks.clear();



        for (ProgressionBlock block : list){
            boolean isUnlocked = block.isUnlocked(Minecraft.getInstance().player);
            ItemStack item;
            Component cmp;
            if (isUnlocked){
                item = block.getUnlockedBlock().asItem().getDefaultInstance();
                cmp = Component.translatable("solarcraft.screens.buttons.progression_block." +
                        ForgeRegistries.BLOCKS.getKey(block.getUnlockedBlock()).getPath());
            }else{
                item = block.getLockedBlock().asItem().getDefaultInstance();
                cmp = Component.translatable("solarcraft.screens.buttons.progression_block.not_unlocked")
                        .append(Component.literal(": "))
                        .withStyle(ChatFormatting.RED)
                        .append(block.getRequiredProgression().getTranslation().copy().withStyle(ChatFormatting.GOLD));
            }


            int x = (iter % countPerRow) * (sizeOfBlock + betweenBlocks) + offsetsFromEdges + 15;
            int y = (iter / countPerRow) * (sizeOfBlock + betweenBlocks) + offsetsFromEdges + 15;

            ItemStackButton button = new ItemStackButton(relX + x,relY + y,sizeOfBlock - 15,sizeOfBlock - 15,btn->{},
                    item,(sizeOfBlock - 15) / 16f,((button1, graphics, mouseX, mouseY) -> {
                        this.addPostRenderEntry(((graphics1, mousex, mousey, partialTicks) -> {
                            graphics.renderTooltip(Minecraft.getInstance().font, font.split(cmp,200),mouseX,mouseY);
                        }));
                    }));
            addWidget(button);
            progressionBlocks.add(button);
            iter++;
        }
        //this.addTab(0,SCItems.SOLAR_FORGE_ITEM.get().getDefaultInstance(),relX + this.getScreenWidth() + 12,relY + 15, SolarLexiconScreen::new,Component.translatable("solarcraft.screens.buttons.progression_screen"));
        //this.addTab(1, Items.IRON_ORE.getDefaultInstance(),relX + this.getScreenWidth() + 12,relY + 15, SolarLexiconScreen::new,null);
        this.addInfo(0,relX + this.getScreenWidth() + 12,relY + 15,Component.translatable("solarcraft.screens.buttons.progression_blocks_page.info"));
    }


    @Override
    public void render(GuiGraphics graphics, int mx, int my, float pticks) {

        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        RenderingTools.renderBasicLexiconPageBackground(matrices,relX,relY,this.getScreenWidth(),this.getScreenHeight());

        RenderingTools.scissor(relX,relY,this.getScreenWidth(),this.getScreenHeight());
        for (AbstractWidget w : progressionBlocks){
            graphics.fill(w.x, w.y,w.x + w.getWidth(),w.y + w.getHeight(),0xff000000);
            RenderingTools.drawFancyBorder(matrices, w.x - 8, w.y - 8,w.getWidth() + 16,w.getHeight() + 16,0);
            w.render(graphics,mx,my,pticks);
        }
        RenderSystem.disableScissor();
        RenderingTools.renderBasicLexiconPageOutline(matrices,relX,relY,this.getScreenWidth(),this.getScreenHeight());

        this.runPostEntries(graphics,mx,my,pticks);
        matrices.popPose();
        super.render(graphics, mx, my, pticks);

    }

    @Override
    public int getScrollValue() {
        return 4;
    }

    @Override
    public int getXRightScroll() {
        return 0;
    }

    @Override
    public int getYDownScroll() {
        int coord = progressionBlocks.size() / (countPerRow+1) * (sizeOfBlock + betweenBlocks) + offsetsFromEdges;
        return coord;
    }

    @Override
    public List<AbstractWidget> getMovableWidgets() {
        return progressionBlocks;
    }

    @Override
    public int getScreenWidth() {
        return 256;
    }

    @Override
    public int getScreenHeight() {
        return 187;
    }
}
