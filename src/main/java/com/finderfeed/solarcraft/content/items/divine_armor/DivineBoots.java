package com.finderfeed.solarcraft.content.items.divine_armor;

import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class DivineBoots extends BaseDivineArmor{
    public DivineBoots(ArmorMaterial p_40386_, ArmorItem.Type p_40387_, Properties p_40388_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_40386_, p_40387_, p_40388_, fragmentSupplier);
    }


    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if (level.isClientSide || level.getGameTime() % 20 != 0) return;
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,40,2,false,false));
    }

    @Override
    public float getMaxRunicEnergyCapacity() {
        return 1000;
    }
}
