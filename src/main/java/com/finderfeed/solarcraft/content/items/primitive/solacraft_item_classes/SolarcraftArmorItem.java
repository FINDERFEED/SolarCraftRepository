package com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.util.thread.EffectiveSide;

import java.util.function.Supplier;

public class SolarcraftArmorItem extends ArmorItem implements FragmentItem {
    private Supplier<AncientFragment> fragmentSupplier;

    public SolarcraftArmorItem(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_,Supplier<AncientFragment> fragmentSupplier) {
        super(p_40386_, p_40387_, p_40388_);
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
