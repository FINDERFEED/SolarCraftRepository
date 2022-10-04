package com.finderfeed.solarforge.content.items;

import com.finderfeed.solarforge.content.blocks.solar_energy.Bindable;
import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarforge.misc_things.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SolarNetworkBinder extends Item {

    public SolarNetworkBinder(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    public InteractionResult useOn(UseOnContext ctx) {
        Level world = ctx.getLevel();
        ItemStack stack = ctx.getItemInHand();
        if (!world.isClientSide && ctx.getHand() == InteractionHand.MAIN_HAND){
            if (!ctx.getPlayer().isCrouching()) {
                bindAll(world, ctx.getClickedPos(), ctx.getPlayer(),getPos1(stack),getPos2(stack), stack);
            }else{
                setNull(ctx.getItemInHand());
                ctx.getPlayer().displayClientMessage(Component.literal("Positions cleared"),true);
            }
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack item, @Nullable Level p_77624_2_, List<Component> cmps, TooltipFlag p_77624_4_) {
        cmps.add(Component.translatable("solarcraft.solar_network_binder").withStyle(ChatFormatting.GOLD));
        cmps.add(Component.literal("Pos 1: " + getPos1(item)).withStyle(ChatFormatting.GOLD));
        cmps.add(Component.literal("Pos 2: " + getPos2(item)).withStyle(ChatFormatting.GOLD));
        super.appendHoverText(item, p_77624_2_, cmps, p_77624_4_);
    }
    public void bindAll(Level world,BlockPos clickedPos,Player p,BlockPos pos1,BlockPos pos2,ItemStack stack){
        if (pos1 == null && pos2 == null){
            if (world.getBlockEntity(clickedPos) != null && (world.getBlockEntity(clickedPos) instanceof Bindable)) {
                setPos1(stack,clickedPos);
            }
        }else if (pos1 != null && pos2 == null) {
            if (world.getBlockEntity(clickedPos) != null && (world.getBlockEntity(clickedPos) instanceof Bindable)) {
                setPos2(stack,clickedPos);
            }
        }
        BlockPos p1 = getPos1(stack);
        BlockPos p2 = getPos2(stack);
        if (p1 != null && p2 != null  && world.getBlockEntity(p1) instanceof Bindable tile1) {

            if (!p1.equals(p2)) {
                if (!tile1.bind(p2)){
                    p.displayClientMessage(Component.translatable("solarcraft.failed_to_bind")
                            .withStyle(ChatFormatting.RED),false);
                }
            }
            setNull(stack);
        }
    }

    public void setNull(ItemStack stack){
        CompoundNBTHelper.writeBlockPos("pos",Helpers.NULL_POS,stack.getOrCreateTagElement("positionone"));
        CompoundNBTHelper.writeBlockPos("pos",Helpers.NULL_POS,stack.getOrCreateTagElement("positiontwo"));
    }

    private BlockPos getPos1(ItemStack stack){
        BlockPos pos =  CompoundNBTHelper.getBlockPos("pos",stack.getOrCreateTagElement("positionone"));
        return Helpers.equalsBlockPos(pos,Helpers.NULL_POS) ? null : pos;
    }
    private BlockPos getPos2(ItemStack stack){
        BlockPos pos =  CompoundNBTHelper.getBlockPos("pos",stack.getOrCreateTagElement("positiontwo"));
        return Helpers.equalsBlockPos(pos,Helpers.NULL_POS) ? null : pos;
    }

    private void setPos1(ItemStack stack,BlockPos set){
        CompoundNBTHelper.writeBlockPos("pos",set,stack.getOrCreateTagElement("positionone"));
    }
    private void setPos2(ItemStack stack,BlockPos set){
       CompoundNBTHelper.writeBlockPos("pos",set,stack.getOrCreateTagElement("positiontwo"));
    }

}
