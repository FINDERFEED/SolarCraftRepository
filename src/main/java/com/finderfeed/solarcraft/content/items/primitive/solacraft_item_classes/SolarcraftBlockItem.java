package com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.fml.util.thread.EffectiveSide;

import java.util.function.Supplier;

public class SolarcraftBlockItem extends BlockItem implements FragmentItem {
    private Supplier<AncientFragment> fragmentSupplier;

    public SolarcraftBlockItem(Block p_40565_, Properties p_40566_,Supplier<AncientFragment> fragmentSupplier) {
        super(p_40565_, p_40566_);
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
