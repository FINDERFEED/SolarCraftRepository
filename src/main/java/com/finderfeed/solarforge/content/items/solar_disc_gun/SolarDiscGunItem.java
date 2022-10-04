package com.finderfeed.solarforge.content.items.solar_disc_gun;

import com.finderfeed.solarforge.content.items.primitive.solacraft_item_classes.SolarcraftItem;
import com.finderfeed.solarforge.content.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarforge.content.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarforge.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.entities.SolarcraftEntityTypes;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class SolarDiscGunItem extends SolarcraftItem implements IRunicEnergyUser {

    public static final RunicEnergyCost COST = new RunicEnergyCost().set(RunicEnergy.Type.ULTIMA,5);

    public SolarDiscGunItem(Properties p_i48487_1_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48487_1_,fragmentSupplier);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        if (!world.isClientSide && hand == InteractionHand.MAIN_HAND && ItemRunicEnergy.spendEnergy(this.getCost(),player.getMainHandItem(),this,player)){
            SolarDiscProjectile projectile = new SolarDiscProjectile(SolarcraftEntityTypes.SOLAR_DISC.get(),world);
            projectile.setPos(player.position().x,player.position().y+1.4,player.position().z);
            projectile.setDeltaMovement(player.getLookAngle().multiply(1.5,1.5,1.5));

            projectile.pitch = player.getXRot();
            projectile.yaw = player.yHeadRot;


            world.addFreshEntity(projectile);

        }

        return super.use(world, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("solar_disk.launcher").withStyle(ChatFormatting.GOLD));
        ItemRunicEnergy.addRunicEnergyTextComponents(stack,this,components);
        super.appendHoverText(stack, world, components, flag);
    }


    @Override
    public float getMaxRunicEnergyCapacity() {
        return 1000;
    }

    @Override
    public List<RunicEnergy.Type> allowedInputs() {
        return List.of(RunicEnergy.Type.ULTIMA);
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
