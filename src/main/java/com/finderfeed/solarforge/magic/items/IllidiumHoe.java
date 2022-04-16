package com.finderfeed.solarforge.magic.items;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic.items.primitive.RareSolarcraftHoe;
import com.finderfeed.solarforge.magic.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarforge.magic.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.misc_things.ManaConsumer;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.item.TooltipFlag;

import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;

public class IllidiumHoe extends RareSolarcraftHoe implements IRunicEnergyUser {


    public static final RunicEnergyCost COST = new RunicEnergyCost().set(RunicEnergy.Type.FIRA,75);

    public IllidiumHoe(Tier p_i231595_1_, int p_i231595_2_, float p_i231595_3_, Properties p_i231595_4_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i231595_1_, p_i231595_2_, p_i231595_3_, p_i231595_4_,fragmentSupplier);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (!ctx.getLevel().isClientSide &&  ctx.getPlayer().isCrouching()){
            if (ctx.getLevel().getBlockState(ctx.getClickedPos()).getBlock() instanceof BonemealableBlock &&
                    ItemRunicEnergy.spendEnergy(this.getCost(),ctx.getItemInHand(),this,ctx.getPlayer())){
                BoneMealItem.applyBonemeal(Items.BONE_MEAL.getDefaultInstance(),ctx.getLevel(),ctx.getClickedPos(),ctx.getPlayer());
            }
        }
        return super.useOn(ctx);
    }

    @Override
    public void appendHoverText(ItemStack item, @Nullable Level world, List<Component> components, TooltipFlag p_77624_4_) {
        components.add(new TranslatableComponent("solarforge.illidium_hoe").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(item, world, components, p_77624_4_);
        ItemRunicEnergy.addRunicEnergyTextComponents(item,this,components);
    }


    @Override
    public float getMaxRunicEnergyCapacity() {
        return 3000;
    }

    @Override
    public List<RunicEnergy.Type> allowedInputs() {
        return List.of(RunicEnergy.Type.FIRA);
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
