package com.finderfeed.solarcraft.content.items;


import com.finderfeed.solarcraft.config.SCItemConfig;
import com.finderfeed.solarcraft.content.items.primitive.RareSolarcraftItem;
import com.finderfeed.solarcraft.content.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarcraft.content.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.entities.projectiles.UltraCrossbowProjectile;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.SCConfigs;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;
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

    public static final RunicEnergyCost COST = new RunicEnergyCost().set(RunicEnergy.Type.KELDA,100);

    public UltraCrossbowItem(Properties p_i50040_1_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i50040_1_,fragmentSupplier);
    }

    @Override
    public void onUseTick(Level level, LivingEntity living, ItemStack stack, int count) {
        if (!living.level.isClientSide && (living instanceof Player player) ) {
            if (count % 20 == 0){
                player.level.playSound(null,player, SCSounds.CROSSBOW_CHARGING.get(), SoundSource.AMBIENT,1f,1f);
            }

            float damagePerSecond = SCConfigs.ITEMS.solarCrossbowDamageGain;

            if ((float)(this.getUseDuration(stack)-count)/20*damagePerSecond < SCConfigs.ITEMS.solarCrossbowMaxDamage) {
                player.displayClientMessage(Component.literal("-" + String.format("%.1f", (float) (this.getUseDuration(stack) - count) / 20 * damagePerSecond) + "-").withStyle(ChatFormatting.GOLD), true);
            }else{
                player.displayClientMessage(Component.literal("-" + "%.1f".formatted(SCConfigs.ITEMS.solarCrossbowMaxDamage) + "-").withStyle(ChatFormatting.GOLD), true);
            }
        }
        super.onUseTick(level, living, stack, count);
    }


    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity player, int remainingSeconds) {
        if (player instanceof Player pl && ItemRunicEnergy.isEnough(this.getCost(),stack,this,pl)){
            float recoil = SCConfigs.ITEMS.solarCrossbowRecoilStrength;
            player.push(-player.getLookAngle().x*recoil,-player.getLookAngle().y*recoil,-player.getLookAngle().z*recoil);
        }
        if (!world.isClientSide){

            if (player instanceof Player pl && ItemRunicEnergy.spendEnergy(this.getCost(),stack,this,pl)) {
            UltraCrossbowProjectile proj = new UltraCrossbowProjectile(SCEntityTypes.ULTRA_CROSSBOW_SHOT.get(),world);
            proj.setOwner(pl);
            proj.setPos(player.getX() + player.getLookAngle().x,player.getY()+1.5f+player.getLookAngle().y,player.getZ()+player.getLookAngle().z);
            player.level.playSound(null,player, SCSounds.CROSSBOW_SHOOT_SOUND.get(), SoundSource.AMBIENT,1,1);

            float damagePerSecond = SCConfigs.ITEMS.solarCrossbowDamageGain;

            proj.setDamage(Mth.clamp((float) (this.getUseDuration(stack) - remainingSeconds) / 20 * damagePerSecond,0,SCConfigs.ITEMS.solarCrossbowMaxDamage));


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
        components.add(Component.translatable("solarcraft.ultra_crossbow"
        ,"%.1f".formatted(SCConfigs.ITEMS.solarCrossbowDamageGain),
                "%.1f".formatted(SCConfigs.ITEMS.solarCrossbowShotCost)
        ).withStyle(ChatFormatting.GOLD));
        ItemRunicEnergy.addRunicEnergyTextComponents(stack,this,components);
        super.appendHoverText(stack, p_77624_2_, components, p_77624_4_);
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return Integer.MAX_VALUE;
    }


    @Override
    public float getMaxRunicEnergyCapacity() {
        return SCConfigs.ITEMS.solarCrossbowRunicEnergyCapacity;
    }

    @Override
    public List<RunicEnergy.Type> allowedInputs() {
        return List.of(RunicEnergy.Type.KELDA);
    }

    @Override
    public RunicEnergyCost getCost() {
        return new RunicEnergyCost().set(RunicEnergy.Type.KELDA,SCConfigs.ITEMS.solarCrossbowShotCost);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }
}
