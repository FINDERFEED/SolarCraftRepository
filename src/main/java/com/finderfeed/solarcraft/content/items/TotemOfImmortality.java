package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.config.SCItemConfig;
import com.finderfeed.solarcraft.content.items.primitive.RareSolarcraftItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.registries.SCConfigs;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class TotemOfImmortality extends RareSolarcraftItem {
    public TotemOfImmortality(Properties p_i48487_1_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48487_1_,fragmentSupplier);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        p_77624_3_.add(Component.translatable("solarcraft.totem_of_immortality", "%d".formatted(
                SCConfigs.ITEMS.totemOfImmortalityEffectTime / 20
        )).withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }

}
