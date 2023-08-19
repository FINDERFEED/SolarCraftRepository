package com.finderfeed.solarcraft.content.items;


import com.finderfeed.solarcraft.content.items.primitive.RareSolarcraftItem;
import com.finderfeed.solarcraft.content.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarcraft.content.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.entities.projectiles.UltraCrossbowProjectile;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import net.minecraft.util.Mth;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
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
    public void onUseTick(Level level, LivingEntity living, ItemStack stack, int count) {
        if (!living.level.isClientSide && (living instanceof Player player) ) {
            if (count % 20 == 0){
                player.level.playSound(null,player, SolarcraftSounds.CROSSBOW_CHARGING.get(), SoundSource.AMBIENT,1f,1f);
            }

            if ((float)(72000-count)/20*DAMAGE_PER_SECOND < 120) {
                player.displayClientMessage(Component.literal("-" + String.format("%.1f", (float) (72000 - count) / 20 * DAMAGE_PER_SECOND) + "-").withStyle(ChatFormatting.GOLD), true);
            }else{
                player.displayClientMessage(Component.literal("-" + 120.0 + "-").withStyle(ChatFormatting.GOLD), true);
            }
        }
        super.onUseTick(level, living, stack, count);
    }


    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity player, int remainingSeconds) {
        if (player instanceof Player pl && ItemRunicEnergy.isEnough(this.getCost(),stack,this,pl)){
            player.push(-player.getLookAngle().x*2,-player.getLookAngle().y*2,-player.getLookAngle().z*2);
        }
        if (!world.isClientSide){

            if (player instanceof Player pl && ItemRunicEnergy.spendEnergy(this.getCost(),stack,this,pl)) {
            UltraCrossbowProjectile proj = new UltraCrossbowProjectile(SCEntityTypes.ULTRA_CROSSBOW_SHOT.get(),world);
            proj.setOwner(pl);
            proj.setPos(player.getX() + player.getLookAngle().x,player.getY()+1.5f+player.getLookAngle().y,player.getZ()+player.getLookAngle().z);
            player.level.playSound(null,player, SolarcraftSounds.CROSSBOW_SHOOT_SOUND.get(), SoundSource.AMBIENT,1,1);


            proj.setDamage(Mth.clamp((float) (72000 - remainingSeconds) / 20 * DAMAGE_PER_SECOND,0,120));

            proj.setDeltaMovement(player.getLookAngle().multiply(4,4,4));
            world.addFreshEntity(proj);
            }
        }
        super.releaseUsing(stack,world, player, remainingSeconds);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (ItemRunicEnergy.isEnough(getCost(),player.getItemInHand(hand),this,player)) {
            player.startUsingItem(hand);
        }
        return super.use(world, player, hand);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_77624_2_, List<Component> components, TooltipFlag p_77624_4_) {
        components.add(Component.translatable("solarcraft.ultra_crossbow").withStyle(ChatFormatting.GOLD));
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

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }
}
