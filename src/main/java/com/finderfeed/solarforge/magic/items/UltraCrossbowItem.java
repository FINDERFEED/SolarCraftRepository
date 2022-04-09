package com.finderfeed.solarforge.magic.items;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic.items.primitive.RareSolarcraftItem;
import com.finderfeed.solarforge.magic.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarforge.magic.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.magic.projectiles.UltraCrossbowProjectile;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.misc_things.ManaConsumer;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.entities.EntityTypes;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class UltraCrossbowItem extends RareSolarcraftItem implements IRunicEnergyUser {
    public static double DAMAGE_PER_SECOND = 3.5d;

    public static final RunicEnergyCost COST = new RunicEnergyCost().set(RunicEnergy.Type.KELDA,100);

    public UltraCrossbowItem(Properties p_i50040_1_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i50040_1_,fragmentSupplier);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (!player.level.isClientSide && (player instanceof Player) ) {
            if (count % 20 == 0){
                player.level.playSound(null,player, Sounds.CROSSBOW_CHARGING.get(), SoundSource.AMBIENT,1f,1f);
            }

            if ((float)(72000-count)/20*DAMAGE_PER_SECOND < 120) {
                ((Player) player).displayClientMessage(new TextComponent("-" + String.format("%.1f", (float) (72000 - count) / 20 * DAMAGE_PER_SECOND) + "-").withStyle(ChatFormatting.GOLD), true);
            }else{
                ((Player) player).displayClientMessage(new TextComponent("-" + 120.0 + "-").withStyle(ChatFormatting.GOLD), true);
            }
        }

        super.onUsingTick(stack, player, count);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity player, int remainingSeconds) {
        if (player instanceof Player pl && ItemRunicEnergy.isEnough(this.getCost(),stack,this,pl)){
            player.push(-player.getLookAngle().x*2,-player.getLookAngle().y*2,-player.getLookAngle().z*2);
        }
        if (!world.isClientSide){

            if (player instanceof Player pl && ItemRunicEnergy.spendEnergy(this.getCost(),stack,this,pl)) {
            UltraCrossbowProjectile proj = new UltraCrossbowProjectile(EntityTypes.ULTRA_CROSSBOW_SHOT.get(),world);
            proj.setPos(player.getX() + player.getLookAngle().x,player.getY()+1.5f+player.getLookAngle().y,player.getZ()+player.getLookAngle().z);
            player.level.playSound(null,player, Sounds.CROSSBOW_SHOOT_SOUND.get(), SoundSource.AMBIENT,1,1);
            proj.setYAW(player.getYRot());
            proj.setPITCH(player.getXRot());
            if ((float)(72000 - remainingSeconds)/20*DAMAGE_PER_SECOND < 120) {
                proj.setDamage((float) (72000 - remainingSeconds) / 20 * DAMAGE_PER_SECOND);
            }else {
                proj.setDamage(120);
            }
            proj.setDeltaMovement(player.getLookAngle().multiply(4,4,4));
            world.addFreshEntity(proj);
            }
        }
        super.releaseUsing(stack,world, player, remainingSeconds);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
        p_77659_2_.startUsingItem(p_77659_3_);
        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_77624_2_, List<Component> components, TooltipFlag p_77624_4_) {
        components.add(new TranslatableComponent("solarforge.ultra_crossbow").withStyle(ChatFormatting.GOLD));
        ItemRunicEnergy.addRunicEnergyTextComponents(stack,this,components);
        super.appendHoverText(stack, p_77624_2_, components, p_77624_4_);
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }


    @Override
    public float getMaxRunicEnergyCapacity() {
        return 5000;
    }

    @Override
    public List<RunicEnergy.Type> allowedInputs() {
        return List.of(RunicEnergy.Type.KELDA);
    }

    @Override
    public RunicEnergyCost getCost() {
        return COST;
    }
}
