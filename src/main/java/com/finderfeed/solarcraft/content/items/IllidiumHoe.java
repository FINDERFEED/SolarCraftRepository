package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.config.SCItemConfig;
import com.finderfeed.solarcraft.content.items.primitive.RareSolarcraftHoe;
import com.finderfeed.solarcraft.content.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarcraft.content.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.SCConfigs;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.item.TooltipFlag;

import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
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
        String cost = "%.1f".formatted(SCConfigs.ITEMS.illidiumHoeAbilityCost);
        components.add(Component.translatable("solarcraft.illidium_hoe",cost).withStyle(ChatFormatting.GOLD));
        super.appendHoverText(item, world, components, p_77624_4_);
        ItemRunicEnergy.addRunicEnergyTextComponents(item,this,components);
    }


    @Override
    public float getMaxRunicEnergyCapacity() {
        return SCConfigs.ITEMS.illidiumHoeEnergyCapacity;
    }

    @Override
    public List<RunicEnergy.Type> allowedInputs() {
        return List.of(RunicEnergy.Type.FIRA);
    }

    @Override
    public RunicEnergyCost getCost() {
        return new RunicEnergyCost().set(RunicEnergy.Type.FIRA,SCConfigs.ITEMS.illidiumHoeAbilityCost);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }
}
