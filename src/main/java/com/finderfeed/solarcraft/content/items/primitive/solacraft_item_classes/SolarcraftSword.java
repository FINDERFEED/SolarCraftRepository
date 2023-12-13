package com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.neoforged.neoforge.fml.util.thread.EffectiveSide;

import java.util.function.Supplier;

public class SolarcraftSword extends SwordItem implements FragmentItem {
    private Supplier<AncientFragment> fragmentSupplier;

    public SolarcraftSword(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_,Supplier<AncientFragment> fragmentSupplier) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
        this.fragmentSupplier = fragmentSupplier;
    }


    @Override
    public Component getName(ItemStack stack) {
        if (EffectiveSide.get().isClient()){
            if (ClientHelpers.isComponentObfuscated(stack)) {
                return super.getName(stack).copy().withStyle(ChatFormatting.OBFUSCATED);
            }else {
                return super.getName(stack);
            }
        }
        return super.getName(stack);
    }

    @Override
    public AncientFragment getNeededFragment() {
        return fragmentSupplier.get();
    }
}
