package com.finderfeed.solarforge.content.items;

import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.content.items.primitive.RareSolarcraftPickaxe;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.misc_things.ITagUser;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ExperienceOrb;

import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class SolarGodPickaxe extends RareSolarcraftPickaxe implements ITagUser {
    public SolarGodPickaxe(Tier p_i48478_1_, int p_i48478_2_, float p_i48478_3_, Properties p_i48478_4_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48478_1_, p_i48478_2_, p_i48478_3_, p_i48478_4_,fragmentSupplier);
    }


    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity player) {
        if (!world.isClientSide){

            int level = getPickaxeLevel(stack);
            if (level >= 2 ){
               dropExpWithChance(pos,world,20);
            }

            if (level >= 4) {
                excavate(pos, player.getDirection(), player.getXRot(), world, stack, player);
            }
        }
        return super.mineBlock(stack, world, state, pos, player);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean inhand) {
        if (!world.isClientSide){
            if (stack.getTagElement(SolarCraftTags.SOLAR_GOD_PICKAXE_TAG) == null){
                stack.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_PICKAXE_TAG);
                stack.getTagElement(SolarCraftTags.SOLAR_GOD_PICKAXE_TAG).putInt(SolarCraftTags.SOLAR_GOD_PICKAXE_LEVEL_TAG,1);
            }
        }

        super.inventoryTick(stack, world, entity, slot, inhand);
    }
    @Override
    public void doThingsWithTag(ItemStack prev, ItemStack stack, String tag) {
        if (prev.getTagElement(SolarCraftTags.SOLAR_GOD_PICKAXE_TAG).getInt(SolarCraftTags.SOLAR_GOD_PICKAXE_LEVEL_TAG)+1 <= 4) {
            stack.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_PICKAXE_TAG).putInt(SolarCraftTags.SOLAR_GOD_PICKAXE_LEVEL_TAG,
                    prev.getTagElement(SolarCraftTags.SOLAR_GOD_PICKAXE_TAG).getInt(SolarCraftTags.SOLAR_GOD_PICKAXE_LEVEL_TAG) + 1);
        }else{
            stack.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_PICKAXE_TAG).putInt(SolarCraftTags.SOLAR_GOD_PICKAXE_LEVEL_TAG,
                    prev.getTagElement(SolarCraftTags.SOLAR_GOD_PICKAXE_TAG).getInt(SolarCraftTags.SOLAR_GOD_PICKAXE_LEVEL_TAG));
        }
    }
    @Override
    public void fillItemCategory(CreativeModeTab p_41391_, NonNullList<ItemStack> p_41392_) {
        super.fillItemCategory(p_41391_, p_41392_);
        if (this.allowdedIn(p_41391_)) {
            ItemStack sword = new ItemStack(this);
            sword.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_PICKAXE_TAG).putInt(SolarCraftTags.SOLAR_GOD_PICKAXE_LEVEL_TAG,4);
            p_41392_.add(sword);
        }
    }

    public static void dropExpWithChance(BlockPos pos,Level world,float chance){
        if ((world.random.nextFloat() >= (1-chance/100) )) {
            world.addFreshEntity(new ExperienceOrb(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5));
        }
    }


    public int getPickaxeLevel(ItemStack stack){
        return stack.getTagElement(SolarCraftTags.SOLAR_GOD_PICKAXE_TAG).getInt(SolarCraftTags.SOLAR_GOD_PICKAXE_LEVEL_TAG);
    }



    public static void excavate(BlockPos pos, Direction dir, float rotation, Level world, ItemStack stack, LivingEntity player){
        if ((rotation >= 50) || (rotation <= -50)){
            for (BlockPos posi : Helpers.getSurroundingBlockPositionsHorizontal(pos)){
                List<ItemStack> stacks = Block.getDrops(world.getBlockState(posi),(ServerLevel) world,posi,world.getBlockEntity(posi),player,stack);

                world.destroyBlock(posi,false);

                for (ItemStack stack1 : stacks){
                    Block.popResource(world,posi,stack1);
                    dropExpWithChance(posi,world,20);
                }
            }
        }else{
            for (BlockPos posi : Helpers.getSurroundingBlockPositionsVertical(pos,dir)){
                List<ItemStack> stacks = Block.getDrops(world.getBlockState(posi),(ServerLevel) world,posi,world.getBlockEntity(posi),player,stack);
                world.destroyBlock(posi,false);

                for (ItemStack stack1 : stacks){
                    Block.popResource(world,posi,stack1);
                    dropExpWithChance(posi,world,20);
                }
            }
        }

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> text, TooltipFlag flag) {
        if ((stack.getTagElement(SolarCraftTags.SOLAR_GOD_PICKAXE_TAG) != null)) {

            int level = stack.getTagElement(SolarCraftTags.SOLAR_GOD_PICKAXE_TAG).getInt(SolarCraftTags.SOLAR_GOD_PICKAXE_LEVEL_TAG);
            text.add(Component.translatable("solarcraft.solar_god_pickaxe_desc").append(String.valueOf(level)).withStyle(ChatFormatting.GOLD));
            if (level >= 2){
                text.add(Component.translatable("solarcraft.solar_god_pickaxe_level_2").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));

            }else{
                text.add(Component.translatable("solarcraft.solar_god_pickaxe_level_2").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.STRIKETHROUGH));
            }

            if (level >= 3){
                text.add(Component.translatable("solarcraft.solar_god_pickaxe_level_3").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));

            }else{
                text.add(Component.translatable("solarcraft.solar_god_pickaxe_level_3").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.STRIKETHROUGH));
            }

            if (level >= 4){
                text.add(Component.translatable("solarcraft.solar_god_pickaxe_level_4").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));

            }else{
                text.add(Component.translatable("solarcraft.solar_god_pickaxe_level_4").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.STRIKETHROUGH));
            }



        }
        super.appendHoverText(stack, world, text, flag);
    }
}
