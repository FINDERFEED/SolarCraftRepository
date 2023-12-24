package com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.neoforged.fml.util.thread.EffectiveSide;
import java.util.function.Supplier;

public class SolarcraftPickaxe extends PickaxeItem implements FragmentItem {
    private Supplier<AncientFragment> fragmentSupplier;

    public SolarcraftPickaxe(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_,Supplier<AncientFragment> fragmentSupplier) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
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
