package com.finderfeed.solarforge.magic_items.items.primitive.solacraft_item_classes;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.fml.util.thread.EffectiveSide;

import java.util.function.Supplier;

public class SolarcraftShield extends ShieldItem implements FragmentItem {
    private Supplier<AncientFragment> fragmentSupplier;

    public SolarcraftShield(Properties p_43089_,Supplier<AncientFragment> fragmentSupplier) {
        super(p_43089_);
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
