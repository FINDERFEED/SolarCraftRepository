package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.magic.blocks.blockentities.RuneEnergyPylonTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class SolarcraftDebugStick extends Item {
    public SolarcraftDebugStick(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public void inventoryTick(ItemStack item, Level world, Entity player, int slot, boolean held) {
        super.inventoryTick(item, world, player, slot, held);
//        System.out.println(ProjectileUtil.getEntityHitResult(world,null,player.position(),player.position().add(player.getLookAngle().multiply(10,10,10)),
//                new AABB(player.position().add(-10,-10,-10),player.position().add(10,10,10)),(d)->{
//                    return true;
//                },1));

        if (player instanceof  Player pl){
            if (!pl.isCreative() && !pl.isSpectator()){

                pl.getAbilities().mayfly = true;
            }
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();

        if (!ctx.getLevel().isClientSide && (ctx.getLevel().getBlockEntity(ctx.getClickedPos()) instanceof DebugTarget target) && ctx.getPlayer() != null && !ctx.getPlayer().isCrouching()){
            target.getDebugStrings().forEach((string)->{
                ctx.getPlayer().sendMessage(new TextComponent(string),ctx.getPlayer().getUUID());
            });
        }

        if (!ctx.getLevel().isClientSide && ctx.getPlayer() != null && ctx.getPlayer().isCrouching()){
            if (ctx.getLevel().getBlockEntity(ctx.getClickedPos()) instanceof RuneEnergyPylonTile pylon){
                pylon.addEnergy(pylon.getEnergyType(),200);
                ctx.getPlayer().sendMessage(new TextComponent(Float.toString(pylon.getCurrentEnergy())),ctx.getPlayer().getUUID());
            }
        }

        if (!world.isClientSide){
            System.out.println(world.getBlockState(pos));
        }


        return InteractionResult.SUCCESS;
    }
}
