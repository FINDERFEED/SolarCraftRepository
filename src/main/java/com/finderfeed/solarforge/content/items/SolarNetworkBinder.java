package com.finderfeed.solarforge.content.items;

import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarforge.misc_things.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
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
                ctx.getPlayer().displayClientMessage(new TextComponent("Positions cleared"),true);
            }
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        p_77624_3_.add(new TextComponent("Click on relay,energy generator,energy user or core to set two positions. When two positions exist they are reset and the blocks are connected.").withStyle(ChatFormatting.GOLD));

        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
    public void bindAll(Level world,BlockPos clickedPos,Player p,BlockPos pos1,BlockPos pos2,ItemStack stack){
        if (pos1 == null && pos2 == null){
            if (world.getBlockEntity(clickedPos) != null && (world.getBlockEntity(clickedPos) instanceof IBindable)) {
                setPos1(stack,clickedPos);
            }
        }else if (pos1 != null && pos2 == null) {
            if (world.getBlockEntity(clickedPos) != null && (world.getBlockEntity(clickedPos) instanceof IBindable)) {
                setPos2(stack,clickedPos);
            }
        }

        if (getPos1(stack) != null && getPos2(stack) != null ) {
            IBindable tile1 = (IBindable) world.getBlockEntity(getPos1(stack));
            IBindable tile2 = (IBindable) world.getBlockEntity(getPos2(stack));
            if (tile1 != null) {
                tile1.bindPos(getPos2(stack));
            }
            setNull(stack);
        }
    }

    public void setNull(ItemStack stack){
        CompoundNBTHelper.writeBlockPos("pos",BlockPos.ZERO,stack.getOrCreateTagElement("positionone"));
        CompoundNBTHelper.writeBlockPos("pos",BlockPos.ZERO,stack.getOrCreateTagElement("positiontwo"));
    }

    private BlockPos getPos1(ItemStack stack){
        BlockPos pos =  CompoundNBTHelper.getBlockPos("pos",stack.getOrCreateTagElement("positionone"));
        return Helpers.equalsBlockPos(pos,BlockPos.ZERO) ? null : pos;
    }
    private BlockPos getPos2(ItemStack stack){
        BlockPos pos =  CompoundNBTHelper.getBlockPos("pos",stack.getOrCreateTagElement("positiontwo"));
        return Helpers.equalsBlockPos(pos,BlockPos.ZERO) ? null : pos;
    }

    private void setPos1(ItemStack stack,BlockPos set){
        CompoundNBTHelper.writeBlockPos("pos",set,stack.getOrCreateTagElement("positionone"));
    }
    private void setPos2(ItemStack stack,BlockPos set){
       CompoundNBTHelper.writeBlockPos("pos",set,stack.getOrCreateTagElement("positiontwo"));
    }

}
