package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.misc_things.*;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;


import net.minecraft.world.item.Item.Properties;

public class SolarNetworkBinder extends Item {
    public BlockPos pos1 = null;
    public BlockPos pos2 = null;
    public SolarNetworkBinder(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

//    @Override
//    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
//        if (p_77659_1_.isClientSide){
//            Minecraft.getInstance().gameRenderer.displayItemActivation(p_77659_2_.getItemInHand(p_77659_3_));
//        }
//
//        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
//    }

    public InteractionResult useOn(UseOnContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        Level world = ctx.getLevel();
        if (!world.isClientSide){

            if (!ctx.getPlayer().isCrouching()) {
                bindAll(world, ctx.getClickedPos(), ctx.getPlayer());
            }else{
                setNull();
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
    public void bindAll(Level world,BlockPos clickedPos,Player p){
                if (pos1 == null && pos2 == null){
            if (world.getBlockEntity(clickedPos) != null && (world.getBlockEntity(clickedPos) instanceof IBindable)) {
                pos1 = clickedPos;
            }
        }else if (pos1 != null && pos2 == null) {
            if (world.getBlockEntity(clickedPos) != null && (world.getBlockEntity(clickedPos) instanceof IBindable)) {
                pos2 = clickedPos;
            }
        }

        if (pos1 != null && pos2 != null ) {
            IBindable tile1 = (IBindable) world.getBlockEntity(pos1);
            IBindable tile2 = (IBindable) world.getBlockEntity(pos2);
            if (tile1 != null) {
                tile1.bindPos(pos2);
            }
            setNull();
        }
    }

//    public void bindAll(World world,BlockPos clickedPos,PlayerEntity p){
//        if (pos1 == null && pos2 == null){
//            if (world.getBlockEntity(clickedPos) != null && (world.getBlockEntity(clickedPos) instanceof AbstractSolarNetworkRepeater
//            || world.getBlockEntity(clickedPos) instanceof AbstractEnergyGeneratorTileEntity
//                    || world.getBlockEntity(clickedPos) instanceof AbstractSolarCore
//                    || world.getBlockEntity(clickedPos) instanceof IEnergyUser)) {
//                pos1 = clickedPos;
//            }
//        }else if (pos1 != null && pos2 == null) {
//            if (world.getBlockEntity(clickedPos) != null && (world.getBlockEntity(clickedPos) instanceof AbstractSolarNetworkRepeater
//                    || world.getBlockEntity(clickedPos) instanceof AbstractEnergyGeneratorTileEntity
//                    || world.getBlockEntity(clickedPos) instanceof IEnergyUser
//                    || world.getBlockEntity(clickedPos) instanceof AbstractSolarCore)) {
//                pos2 = clickedPos;
//            }
//        }
//
//        if (pos1 != null && pos2 != null ){
//            Vector3d length = new Vector3d(pos2.getX()-pos1.getX(),pos2.getY()-pos1.getY(),pos2.getZ()-pos1.getZ());
//            TileEntity tileAtPos1 = world.getBlockEntity(pos1);
//            TileEntity tileAtPos2 = world.getBlockEntity(pos2);
//            if (isValid(tileAtPos2,length.length())) {
//                if (tileAtPos1 instanceof AbstractEnergyGeneratorTileEntity) {
//                    if (tileAtPos2 instanceof AbstractSolarNetworkRepeater) {
//                        if (Helpers.isReachable(world, pos1, pos2) && !((AbstractEnergyGeneratorTileEntity) tileAtPos1).poslist.contains(pos2)) {
//                            ((AbstractEnergyGeneratorTileEntity) tileAtPos1).poslist.add(pos2);
//                            setNull();
//                            print(true, p);
//                        } else {
//                            print(false, p);
//                            setNull();
//                        }
//                    } else if (tileAtPos2 instanceof IEnergyUser) {
//                        if (Helpers.isReachable(world, pos1, pos2) && !((AbstractEnergyGeneratorTileEntity) tileAtPos1).poslist.contains(pos2)) {
//                            ((AbstractEnergyGeneratorTileEntity) tileAtPos1).poslist.add(pos2);
//                            setNull();
//                            print(true, p);
//                        } else {
//                            print(false, p);
//                            setNull();
//                        }
//                    } else if (tileAtPos2 instanceof AbstractSolarCore) {
//                        if (Helpers.isReachable(world, pos1, pos2) && !((AbstractEnergyGeneratorTileEntity) tileAtPos1).poslist.contains(pos2)) {
//                            ((AbstractEnergyGeneratorTileEntity) tileAtPos1).poslist.add(pos2);
//                            setNull();
//                            print(true, p);
//                        } else {
//                            print(false, p);
//                            setNull();
//                        }
//                    } else if (tileAtPos2 instanceof AbstractEnergyGeneratorTileEntity) {
//                        print(false, p);
//                        setNull();
//                    }
//
//                } else if (tileAtPos1 instanceof AbstractSolarNetworkRepeater) {
//                    if (tileAtPos2 instanceof AbstractSolarNetworkRepeater) {
//                        if (Helpers.isReachable(world, pos1, pos2) && !((AbstractSolarNetworkRepeater) tileAtPos2).connectedTo.equals(pos1) && !pos1.equals(pos2)) {
//                            ((AbstractSolarNetworkRepeater) tileAtPos1).connectedTo = pos2;
//                            print(true, p);
//                            setNull();
//                        } else {
//                            print(false, p);
//                            setNull();
//                        }
//                    } else if (tileAtPos2 instanceof IEnergyUser) {
//                        if (Helpers.isReachable(world, pos1, pos2)) {
//                            ((AbstractSolarNetworkRepeater) tileAtPos1).connectedTo = pos2;
//                            print(true, p);
//                            setNull();
//                        } else {
//                            print(false, p);
//                            setNull();
//                        }
//                    } else if (tileAtPos2 instanceof AbstractSolarCore) {
//                        if (Helpers.isReachable(world, pos1, pos2) && !((AbstractSolarCore) tileAtPos2).poslist.contains(pos1)) {
//                            ((AbstractSolarCore) tileAtPos2).poslist.add(pos1);
//                            setNull();
//                            print(true, p);
//                        } else {
//                            print(false, p);
//                            setNull();
//                        }
//                    } else if (tileAtPos2 instanceof AbstractEnergyGeneratorTileEntity) {
//                        if (Helpers.isReachable(world, pos1, pos2) && !((AbstractEnergyGeneratorTileEntity) tileAtPos2).poslist.contains(pos1)) {
//                            ((AbstractEnergyGeneratorTileEntity) tileAtPos2).poslist.add(pos1);
//                            setNull();
//                            print(true, p);
//                        } else {
//                            print(false, p);
//                            setNull();
//                        }
//                    }
//                } else if (tileAtPos1 instanceof IEnergyUser) {
//                    if (tileAtPos2 instanceof AbstractSolarNetworkRepeater) {
//                        if (Helpers.isReachable(world, pos1, pos2)) {
//                            ((AbstractSolarNetworkRepeater) tileAtPos2).connectedTo = pos1;
//                            print(true, p);
//                            setNull();
//                        } else {
//                            print(false, p);
//                            setNull();
//                        }
//                    } else if (tileAtPos2 instanceof IEnergyUser) {
//                        print(false, p);
//                        setNull();
//                    } else if (tileAtPos2 instanceof AbstractSolarCore) {
//                        if (Helpers.isReachable(world, pos1, pos2) && !((AbstractSolarCore) tileAtPos2).poslist.contains(pos1)) {
//                            ((AbstractSolarCore) tileAtPos2).poslist.add(pos1);
//                            setNull();
//                            print(true, p);
//                        } else {
//                            print(false, p);
//                            setNull();
//                        }
//                    } else if (tileAtPos2 instanceof AbstractEnergyGeneratorTileEntity) {
//                        if (Helpers.isReachable(world, pos1, pos2) && !((AbstractEnergyGeneratorTileEntity) tileAtPos2).poslist.contains(pos1)) {
//                            ((AbstractEnergyGeneratorTileEntity) tileAtPos2).poslist.add(pos1);
//                            setNull();
//                            print(true, p);
//                        } else {
//                            print(false, p);
//                            setNull();
//                        }
//                    }
//                } else if (tileAtPos1 instanceof AbstractSolarCore) {
//                    if (tileAtPos2 instanceof AbstractSolarNetworkRepeater) {
//                        if (Helpers.isReachable(world, pos1, pos2) && !((AbstractSolarCore) tileAtPos1).poslist.contains(pos2)) {
//                            ((AbstractSolarCore) tileAtPos1).poslist.add(pos2);
//                            print(true, p);
//                            setNull();
//                        } else {
//                            print(false, p);
//                            setNull();
//                        }
//                    } else if (tileAtPos2 instanceof AbstractSolarCore) {
//                        print(false, p);
//                        setNull();
//                    } else if (tileAtPos2 instanceof IEnergyUser) {
//                        if (Helpers.isReachable(world, pos1, pos2)) {
//                            ((AbstractSolarCore) tileAtPos1).poslist.add(pos2);
//                            setNull();
//                            print(true, p);
//                        } else {
//                            print(false, p);
//                            setNull();
//                        }
//                    } else if (tileAtPos2 instanceof AbstractEnergyGeneratorTileEntity) {
//                        if (Helpers.isReachable(world, pos1, pos2) && !((AbstractEnergyGeneratorTileEntity) tileAtPos2).poslist.contains(pos1)) {
//                            ((AbstractEnergyGeneratorTileEntity) tileAtPos2).poslist.add(pos1);
//                            setNull();
//                            print(true, p);
//                        } else {
//                            print(false, p);
//                            setNull();
//                        }
//                    }
//                }
//            }else{
//                print(false,p);
//                setNull();
//            }
//        }
//    }

    public boolean isValid(BlockEntity tile,double length){
        if (tile instanceof AbstractSolarNetworkRepeater){
            if (((AbstractSolarNetworkRepeater) tile).getRadius() < length){
                return false;
            }
        }else if(tile instanceof AbstractSolarCore){
            if (((AbstractSolarCore) tile).getRadius() < length){
                return false;
            }
        }else if (tile instanceof IEnergyUser){
            if (((IEnergyUser) tile).getRadius() < length){
                return false;
            }
        }else if (tile instanceof AbstractEnergyGeneratorTileEntity){
            if (((AbstractEnergyGeneratorTileEntity) tile).getRadius() < length){
                return false;
            }

        }
        return true;
    }


    public void setNull(){
        pos1 = null;
        pos2 = null;
    }
    public void print(boolean a, Player playerEntity){
        if (a){
            playerEntity.displayClientMessage(new TextComponent("Binding sucesseful"),true);
        }else{
            playerEntity.displayClientMessage(new TextComponent("Binding failed"),true);
        }
    }
}
