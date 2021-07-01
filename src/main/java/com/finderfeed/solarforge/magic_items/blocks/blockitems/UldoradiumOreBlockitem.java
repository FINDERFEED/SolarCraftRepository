package com.finderfeed.solarforge.magic_items.blocks.blockitems;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.blocks.UldoradiumOre;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.thread.EffectiveSide;

public class UldoradiumOreBlockitem extends BlockItem {



    public UldoradiumOreBlockitem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        if (EffectiveSide.get().isClient()){
            return ClientHelpers.getNameBasedOnProgression(stack);
        }
        return super.getName(stack);
    }



}
