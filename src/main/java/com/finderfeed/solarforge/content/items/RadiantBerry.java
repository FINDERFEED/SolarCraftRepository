package com.finderfeed.solarforge.content.items;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class RadiantBerry extends Item {

    private final List<MobEffect> EFFECTS = List.of(MobEffects.DAMAGE_RESISTANCE,MobEffects.FIRE_RESISTANCE,MobEffects.REGENERATION,MobEffects.MOVEMENT_SPEED);

    public RadiantBerry(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (this.isEdible() && !world.isClientSide){
            MobEffect effect = EFFECTS.get(world.random.nextInt(EFFECTS.size()));
            if (!entity.hasEffect(effect)){
                entity.addEffect(new MobEffectInstance(effect,200,0));
            }
        }
        return super.finishUsingItem(stack, world, entity);
    }
}
