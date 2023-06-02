package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.content.blocks.blockentities.PuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.blockentity.SunShardPuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities.BeamGenerator;
import com.finderfeed.solarcraft.content.entities.OrbitalCannonExplosionEntity;
import com.finderfeed.solarcraft.content.entities.projectiles.OrbitalExplosionProjectile;
import com.finderfeed.solarcraft.content.runic_network.repeater.RunicEnergyRepeaterTile;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.StructurePatternExporter;
import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarcraft.registries.entities.SolarcraftEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SolarcraftDebugStick extends Item {
    public SolarcraftDebugStick(Properties p_41383_) {
        super(p_41383_);
    }

    private BlockPos first;
    private BlockPos second;

    @Override
    public void inventoryTick(ItemStack item, Level world, Entity player, int slot, boolean held) {
        super.inventoryTick(item, world, player, slot, held);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        if (!world.isClientSide){
            StructurePatternExporter.export(world,pos.above(), pos.above().offset(4,4,4));
        }
        return InteractionResult.SUCCESS;
    }

    public void switchPylons(BlockPos pos,Level world){
        if (world.getBlockEntity(pos) instanceof RuneEnergyPylonTile tile){
            tile.setType(RunicEnergy.Type.getAll()[(tile.getEnergyType().getIndex() + 1) % RunicEnergy.Type.getAll().length]);
            Helpers.updateTile(tile);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
//        if (!world.isClientSide){
//            ItemStack stack = player.getItemInHand(hand);
//            boolean mode = stack.getOrCreateTagElement("pylon_mode").getBoolean("isCyclingPylons");
//            if (mode){
//                stack.getOrCreateTagElement("pylon_mode").putBoolean("isCyclingPylons",false);
//            }else{
//                stack.getOrCreateTagElement("pylon_mode").putBoolean("isCyclingPylons",true);
//            }
//            SummoningProjectile bolt = new SummoningProjectile(world,SolarcraftEntityTypes.SHADOW_ZOMBIE.get(),
//                    43,0,87);
//            bolt.setPos(player.position().add(0,2,0));
//            bolt.setDeltaMovement(player.getLookAngle());
//            world.addFreshEntity(bolt);
//        }
//        if (!world.isClientSide){
//            for (int i = 0; i <= 10;i++){
//                for (int g = 0; g <= 10;g++){
//                    BlockPos pos = player.getOnPos().offset(i*10,0,g*10);
//                    world.setBlock(pos,SolarcraftBlocks.REPEATER.get().defaultBlockState(),3);
//                    world.setBlock(pos.below(),SolarcraftBlocks.FIRA_RUNE_BLOCK.get().defaultBlockState(),3);
//                }
//            }
//        }
//        if (!world.isClientSide){
//            OrbitalExplosionProjectile projectile = new OrbitalExplosionProjectile(SolarcraftEntityTypes.ORBITAL_EXPLOSION_PROJECTILE.get(),
//                    world);
//
//            ClipContext context = new ClipContext(player.position(),player.position().add(
//                    player.getLookAngle().multiply(200,200,200)
//            ), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null);
//            BlockHitResult r = world.clip(context);
//            Vec3 v = r.getLocation();
//
//            projectile.setPos(v);
//
//
//            projectile.setExplosionDepth(100);
//            projectile.setExplosionRadius(200);
////            projectile.setDestination((int)v.x,(int)v.z);
//            projectile.setDestination(1000000,1000000);
//            world.addFreshEntity(projectile);
//        }
        return super.use(world, player, hand);
    }
}
