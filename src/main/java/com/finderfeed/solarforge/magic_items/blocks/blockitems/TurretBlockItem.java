package com.finderfeed.solarforge.magic_items.blocks.blockitems;

import com.finderfeed.solarforge.magic_items.blocks.TurretBlock;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.TurretTileEntity;
import com.finderfeed.solarforge.magic_items.items.primitive.RareSolarcraftBlockItem;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.BlockItem;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class TurretBlockItem extends RareSolarcraftBlockItem {

    public TurretBlockItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }





    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslatableComponent("solarcraft.turret_desc").withStyle(ChatFormatting.GOLD));
        if (p_77624_1_.getTagElement(TurretBlock.SUBTAG) != null){
            p_77624_3_.add(new TranslatableComponent("solarcraft.turret_level")
                    .append(new TextComponent(" "+p_77624_1_.getTagElement(TurretBlock.SUBTAG).getInt(TurretBlock.LEVEL_TAG)
                    ).withStyle(ChatFormatting.GOLD)));
        }
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
