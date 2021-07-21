package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class AncientFragmentItem extends Item {
    public AncientFragmentItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }




    @Override
    public boolean verifyTagAfterLoad(CompoundNBT p_179215_1_) {
        return true;
    }

    @Override
    public void fillItemCategory(ItemGroup p_150895_1_, NonNullList<ItemStack> list) {
        super.fillItemCategory(p_150895_1_, list);
        if (this.allowdedIn(p_150895_1_)) {
            for (AncientFragment frag : AncientFragment.getAllFragments()) {
                ItemStack stack = new ItemStack(this, 1);
                stack.getOrCreateTagElement(ProgressionHelper.TAG_ELEMENT).putString(ProgressionHelper.FRAG_ID, frag.getId());
                list.add(stack);
            }
        }

    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable World level, List<ITextComponent> texts, ITooltipFlag p_77624_4_) {
        CompoundNBT nbt = stack.getTagElement(ProgressionHelper.TAG_ELEMENT);
        if (nbt == null){
            texts.add(new TranslationTextComponent("ancient_frag.no_tag"));
        }else{
            AncientFragment frag = AncientFragment.getFragmentByID(nbt.getString(ProgressionHelper.FRAG_ID));
            if (frag != null){
                texts.add(frag.getTranslation());
            }
        }

        super.appendHoverText(stack, level, texts, p_77624_4_);
    }
}
