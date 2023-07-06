package com.finderfeed.solarcraft.content.blocks.blockentities.containers.screens;

import com.finderfeed.solarcraft.content.blocks.blockentities.containers.ModuleApplierMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


public class ModuleApplierScreen extends ItemCombinerScreen<ModuleApplierMenu> {
    public static final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarcraft","textures/gui/module_applier_gui.png");

    public ModuleApplierScreen(ModuleApplierMenu p_98901_, Inventory p_98902_, Component p_98903_) {
        super(p_98901_, p_98902_, p_98903_, MAIN_SCREEN);
        this.titleLabelX = 7;
        this.titleLabelY = 33;
    }

    @Override
    protected void renderLabels(GuiGraphics p_97808_, int p_97809_, int p_97810_) {
        RenderSystem.disableBlend();
        super.renderLabels(p_97808_, p_97809_, p_97810_);
    }

    @Override
    protected void renderErrorIcon(GuiGraphics p_281990_, int p_266822_, int p_267045_) {

    }
}
