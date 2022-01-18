package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.magic_items.items.primitive.solacraft_item_classes.SolarcraftItem;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class EffectAmulet extends SolarcraftItem {

    private MobEffect effect;

    public EffectAmulet(Properties p_41383_, MobEffect effect, Supplier<AncientFragment> fragmentSupplier) {
        super(p_41383_,fragmentSupplier);
        this.effect= effect;
    }


    @Override
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity entity, int p_41407_, boolean p_41408_) {
        if (entity instanceof LivingEntity living){
            if (!living.hasEffect(effect) && !effect.equals(MobEffects.NIGHT_VISION)){
                if (p_41405_.getGameTime() % 20 == 0) {
                    living.addEffect(new MobEffectInstance(effect, 400, 0));
                }
            }
            if (effect.equals(MobEffects.NIGHT_VISION)){
                if (p_41405_.getGameTime() % 20 == 0) {
                    living.addEffect(new MobEffectInstance(effect, 400, 0));
                }
            }
        }
        super.inventoryTick(p_41404_, p_41405_, entity, p_41407_, p_41408_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(effect.getDisplayName().copy().withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }
}
