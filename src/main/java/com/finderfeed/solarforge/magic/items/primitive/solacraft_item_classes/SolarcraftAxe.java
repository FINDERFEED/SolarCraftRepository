package com.finderfeed.solarforge.magic.items.primitive.solacraft_item_classes;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraftforge.fml.util.thread.EffectiveSide;

import java.util.function.Supplier;

public class SolarcraftAxe extends AxeItem implements FragmentItem {
    private Supplier<AncientFragment> fragmentSupplier;

    public SolarcraftAxe(Tier p_40521_, float p_40522_, float p_40523_, Properties p_40524_,Supplier<AncientFragment> fragmentItemSupplier) {
        super(p_40521_, p_40522_, p_40523_, p_40524_);
        this.fragmentSupplier = fragmentItemSupplier;
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
