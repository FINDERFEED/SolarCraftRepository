package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.client.particles.ParticleTypesRegistry;
import com.finderfeed.solarforge.entities.EarthquakeEntity;
import com.finderfeed.solarforge.magic.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarforge.magic.blocks.blockentities.projectiles.ShadowBolt;
import com.finderfeed.solarforge.magic.projectiles.SolarFireballProjectile;
import com.finderfeed.solarforge.registries.entities.EntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

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
            if (!world.isClientSide){
                if (world.getGameTime() % 20 == 0) {
//                    Vec3 vec = player.getLookAngle().multiply(1, 0, 1).normalize();
//                    System.out.println("vector " + -1*Math.toDegrees(Math.atan2(vec.x, vec.z)));
//                    System.out.println("head " + pl.yBodyRot % 180);
                }
            }
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
                boolean mode = ctx.getItemInHand().getOrCreateTagElement("pylon_mode").getBoolean("isCyclingPylons");
                if (!mode) {
                    pylon.addEnergy(pylon.getEnergyType(), 200);
                    ctx.getPlayer().sendMessage(new TextComponent(Float.toString(pylon.getCurrentEnergy())), ctx.getPlayer().getUUID());
                }else{
                    pylon.setType(RunicEnergy.Type.byIndex((pylon.getEnergyType().getIndex() + 1) % 8));
                }
            }
        }


        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide){
            ItemStack stack = player.getItemInHand(hand);
            boolean mode = stack.getOrCreateTagElement("pylon_mode").getBoolean("isCyclingPylons");
            if (mode){
                stack.getOrCreateTagElement("pylon_mode").putBoolean("isCyclingPylons",false);
            }else{
                stack.getOrCreateTagElement("pylon_mode").putBoolean("isCyclingPylons",true);
            }
            ShadowBolt bolt = new ShadowBolt(EntityTypes.SHADOW_BOLT.get(),world);
            bolt.setPos(player.position().add(0,2,0));
            bolt.setDeltaMovement(player.getLookAngle());
            world.addFreshEntity(bolt);
        }
        return super.use(world, player, hand);
    }
}
