package com.finderfeed.solarforge.content.items.primitive.solacraft_item_classes;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraftforge.fml.util.thread.EffectiveSide;

import java.util.function.Supplier;

public class SolarcraftShovel extends ShovelItem implements FragmentItem {

    private Supplier<AncientFragment> fragmentSupplier;

    public SolarcraftShovel(Tier p_43114_, float p_43115_, float p_43116_, Properties p_43117_,Supplier<AncientFragment> fragmentSupplier) {
        super(p_43114_, p_43115_, p_43116_, p_43117_);
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
