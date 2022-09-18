package com.finderfeed.solarforge.content.items;

import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.content.entities.not_alive.BallLightningProjectile;
import com.finderfeed.solarforge.content.items.primitive.RareSolarcraftItem;
import com.finderfeed.solarforge.content.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarforge.content.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarforge.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.entities.SolarcraftEntityTypes;
import com.finderfeed.solarforge.registries.sounds.SolarcraftSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class LightningGun extends RareSolarcraftItem implements IRunicEnergyUser {

    public static final RunicEnergyCost COST = new RunicEnergyCost().set(RunicEnergy.Type.KELDA,50);

    public LightningGun(Properties p_41383_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_41383_, fragmentSupplier);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide && hand == InteractionHand.MAIN_HAND){
            if (ItemRunicEnergy.spendEnergy(this.getCost(),player.getMainHandItem(),this,player)){
                if (!player.isCreative()){
                    player.getCooldowns().addCooldown(this,60);
                }
                world.playSound(player,player.getX(),player.getY()+0.5f,player.getZ(),
                        SolarcraftSounds.LIGHTNING_GUN_SHOT.get(),SoundSource.PLAYERS,1,10);
                BallLightningProjectile p = new BallLightningProjectile(SolarcraftEntityTypes.BALL_LIGHTNING.get(),world);
                p.setPos(player.getX(),player.getY() + player.getEyeHeight(player.getPose())* 0.8,player.getZ());
                p.setDeltaMovement(player.getLookAngle().multiply(0.7,0.7,0.7));
                world.addFreshEntity(p);
            }
        }
        if (world.isClientSide){
            ClientHelpers.playsoundInEars(SolarcraftSounds.LIGHTNING_GUN_SHOT.get(),1,10);
        }
        return super.use(world, player, hand);
    }


    @Override
    public void appendHoverText(ItemStack item, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(new TranslatableComponent("solarcraft.lightning_gun").withStyle(ChatFormatting.GOLD));
        ItemRunicEnergy.addRunicEnergyTextComponents(item,this,components);
        super.appendHoverText(item, p_41422_, components, p_41424_);
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
