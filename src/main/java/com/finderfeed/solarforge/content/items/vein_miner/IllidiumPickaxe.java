package com.finderfeed.solarforge.content.items.vein_miner;

import com.finderfeed.solarforge.content.items.primitive.RareSolarcraftPickaxe;
import com.finderfeed.solarforge.content.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarforge.content.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarforge.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;


import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;


import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;


import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.Tags;

public class IllidiumPickaxe extends RareSolarcraftPickaxe implements IRunicEnergyUser {

    public static final RunicEnergyCost COST = new RunicEnergyCost().set(RunicEnergy.Type.TERA,2);


    public IllidiumPickaxe(Tier p_i48478_1_, int p_i48478_2_, float p_i48478_3_, Properties p_i48478_4_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48478_1_, p_i48478_2_, p_i48478_3_, p_i48478_4_,fragmentSupplier);

    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entity) {

        if (!world.isClientSide && entity.isCrouching() && entity instanceof Player player) {
            List<BlockPos> posList = new ArrayList<>();
            populateList(pos, world, posList,0);
            for (BlockPos a : posList) {
                if (ItemRunicEnergy.spendEnergy(this.getCost(),stack,this,player)) {
                    Block.dropResources(world.getBlockState(a), world, a, world.getBlockEntity(a), entity, stack);
                    world.destroyBlock(a, false);
                }

            }
        }else if(!world.isClientSide && !entity.isCrouching() && entity instanceof Player player) {
            ItemRunicEnergy.spendEnergy(this.getCost(),stack,this,player);
        }

        return super.mineBlock(stack,world,state,pos,entity);
    }



    public static void populateList(BlockPos pos, Level world, List<BlockPos> posList,int depth){
        if (depth != 20) {
            List<BlockPos> poslis = new ArrayList<>();
            poslis.add(pos.above());
            poslis.add(pos.below());
            poslis.add(pos.south());
            poslis.add(pos.east());
            poslis.add(pos.north());
            poslis.add(pos.west());
            posList.add(pos);
            poslis.removeIf(posList::contains);
            for (BlockPos a : poslis) {
                BlockState state = world.getBlockState(a);
                if (state.is(Tags.Blocks.ORES)) {
                    posList.add(a);
                    populateList(a, world, posList, depth + 1);
                }
            }
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag p_77624_4_) {
        components.add(Component.translatable("solarforge.veinminer").withStyle(ChatFormatting.GOLD));
        ItemRunicEnergy.addRunicEnergyTextComponents(stack,this,components);
        super.appendHoverText(stack, world, components, p_77624_4_);
    }

    @Override
    public boolean isEnchantable(ItemStack p_77616_1_) {
        return true;
    }

    @Override
    public float getMaxRunicEnergyCapacity() {
        return 5000;
    }

    @Override
    public List<RunicEnergy.Type> allowedInputs() {
        return List.of(RunicEnergy.Type.TERA);
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

