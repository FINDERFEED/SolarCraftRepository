package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.RuneEnergyPylonTile;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;

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
        if (world.getGameTime() % 20 == 0) {
            world.addParticle(ParticlesList.SOLAR_EXPLOSION_PARTICLE.get(), player.getX(), player.getY()+player.getBbHeight()/2, player.getZ(),
                    player.getLookAngle().x*0.05, player.getLookAngle().y*0.05, player.getLookAngle().z*0.05);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {

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


        return InteractionResult.SUCCESS;
    }
}
