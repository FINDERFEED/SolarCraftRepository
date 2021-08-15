package com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.screens;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.ModuleApplierMenu;
import com.finderfeed.solarforge.misc_things.PhantomInventory;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


public class ModuleApplierScreen extends ItemCombinerScreen<ModuleApplierMenu> {
    public static final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarforge","textures/gui/module_applier_gui.png");

    public ModuleApplierScreen(ModuleApplierMenu p_98901_, Inventory p_98902_, Component p_98903_) {
        super(p_98901_, p_98902_, p_98903_, MAIN_SCREEN);
        this.titleLabelX = 30;
        this.titleLabelY = 18;
    }

    @Override
    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
        RenderSystem.disableBlend();
        super.renderLabels(p_97808_, p_97809_, p_97810_);
    }
}
