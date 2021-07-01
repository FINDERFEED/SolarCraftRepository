package com.finderfeed.solarforge.magic_items.blocks.blockitems;

import com.finderfeed.solarforge.magic_items.blocks.TurretBlock;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.TurretTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TurretBlockItem extends BlockItem {

    public TurretBlockItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }



    @Override
    public boolean verifyTagAfterLoad(CompoundNBT p_179215_1_) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslationTextComponent("solarcraft.turret_desc").withStyle(TextFormatting.GOLD));
        if (p_77624_1_.getTagElement(TurretBlock.SUBTAG) != null){
            p_77624_3_.add(new TranslationTextComponent("solarcraft.turret_level")
                    .append(new StringTextComponent(" "+p_77624_1_.getTagElement(TurretBlock.SUBTAG).getInt(TurretBlock.LEVEL_TAG)
                    ).withStyle(TextFormatting.GOLD)));
        }
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
