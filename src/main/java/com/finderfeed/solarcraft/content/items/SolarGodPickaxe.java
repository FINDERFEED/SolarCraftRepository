package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.content.items.primitive.RareSolarcraftPickaxe;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.misc_things.IUpgradable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ExperienceOrb;

import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class SolarGodPickaxe extends RareSolarcraftPickaxe implements IUpgradable {
    public SolarGodPickaxe(Tier p_i48478_1_, int p_i48478_2_, float p_i48478_3_, Properties p_i48478_4_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48478_1_, p_i48478_2_, p_i48478_3_, p_i48478_4_,fragmentSupplier);
    }


    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity player) {
        if (!world.isClientSide){

            int level = getItemLevel(stack);
            if (level >= 1){
               dropExpWithChance(pos,world,20);
            }

            if (level >= 3) {
                excavate(pos, player.getDirection(), player.getXRot(), world, stack, player);
            }
        }
        return super.mineBlock(stack, world, state, pos, player);
    }



    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {
        if (this.allowedIn(tab)){
//            ItemStack stack = new ItemStack(this);
//            setItemLevel(stack,0);
//            stacks.add(stack);
//            ItemStack stack2 = new ItemStack(this);
//            setItemLevel(stack2,getMaxUpgrades());
//            stacks.add(stack2);
            for (int i = 0; i <= getMaxUpgrades();i++){
                ItemStack stack = new ItemStack(this);
                setItemLevel(stack,i);
                stacks.add(stack);
            }
        }
    }

    public static void dropExpWithChance(BlockPos pos,Level world,float chance){
        if ((world.random.nextFloat() >= (1-chance/100) )) {
            world.addFreshEntity(new ExperienceOrb(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5));
        }
    }






    public void excavate(BlockPos pos, Direction dir, float rotation, Level world, ItemStack stack, LivingEntity living){
        int level = getItemLevel(stack);
        if (living instanceof Player player) {

            if ((rotation >= 50) || (rotation <= -50)) {
                for (BlockPos posi : Helpers.getSurroundingBlockPositionsHorizontal(pos)) {
                    List<ItemStack> stacks = Block.getDrops(world.getBlockState(posi), (ServerLevel) world, posi, world.getBlockEntity(posi), living, stack);
                    if (player.hasCorrectToolForDrops(world.getBlockState(posi))) {
                        world.destroyBlock(posi, false);

                        for (ItemStack stack1 : stacks) {
                            Block.popResource(world, posi, stack1);
                            if (level >= 1) {
                                dropExpWithChance(posi, world, 20);
                            }
                        }
                    }
                }
            } else {
                for (BlockPos posi : Helpers.getSurroundingBlockPositionsVertical(pos, dir)) {
                    List<ItemStack> stacks = Block.getDrops(world.getBlockState(posi), (ServerLevel) world, posi, world.getBlockEntity(posi), living, stack);
                    if (player.hasCorrectToolForDrops(world.getBlockState(posi))) {

                        world.destroyBlock(posi, false);

                        for (ItemStack stack1 : stacks) {
                            Block.popResource(world, posi, stack1);
                            if (level >= 1) {
                                dropExpWithChance(posi, world, 20);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> text, TooltipFlag flag) {
        addComponents(stack,text);
        super.appendHoverText(stack, world, text, flag);
    }

    @Override
    public String getUpgradeTagString() {
        return SolarCraftTags.SOLAR_GOD_PICKAXE_TAG;
    }

    @Override
    public int getMaxUpgrades() {
        return 3;
    }

    @Override
    public List<Component> getUpgradeDescriptions() {
        return List.of(
                Component.translatable("solarcraft.solar_god_pickaxe_level_2"),
                Component.translatable("solarcraft.solar_god_pickaxe_level_3"),
                Component.translatable("solarcraft.solar_god_pickaxe_level_4")
        );
    }
}
