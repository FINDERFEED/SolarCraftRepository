package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.content.blocks.blockentities.PuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.blockentity.SunShardPuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities.BeamGenerator;
import com.finderfeed.solarcraft.content.entities.OrbitalCannonExplosionEntity;
import com.finderfeed.solarcraft.content.runic_network.repeater.RunicEnergyRepeaterTile;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.StructurePatternExporter;
import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

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
//        if (!world.isClientSide){
//            StructurePatternExporter.export(world,pos,pos.offset(4,5,4));
//        }
        if (!world.isClientSide){
            if (world.getBlockEntity(ctx.getClickedPos()) instanceof SunShardPuzzleBlockEntity generator){
                Set<BlockPos> positions = new HashSet<>();
                for (int i = -1; i <= 1;i++){
                    for (int g = -1; g <= 1;g++){
                        for (int k = -1; k <= 1;k++){
                            positions.add(new BlockPos(i,g,k));
                        }
                    }
                }
                generator.destroyPositions = new ArrayList<>(positions);
                System.out.println(generator.getPuzzle());
                System.out.println(generator.destroyPositions);
            }
            OrbitalCannonExplosionEntity entity = new OrbitalCannonExplosionEntity(world,250,10,3);
            entity.setPos(pos.getX(),pos.getY(),pos.getZ());
            world.addFreshEntity(entity);
        }

//        if (!world.isClientSide){
//            for (int i = 0; i <= 100;i++){
//                BlockPos p = pos.above().offset(i*16,0,0);
////                world.setBlock(p, SolarcraftBlocks.FIRA_RUNE_BLOCK.get().defaultBlockState(),3);
////                world.setBlock(p.above(),SolarcraftBlocks.REPEATER.get().defaultBlockState(),3);
//                world.getBlockState(p);
//                System.out.println(world.isLoaded(p));
//            }
//        }

        if (!world.isClientSide && world.getBlockEntity(pos) instanceof DebugTarget dtarget){
            if (ctx.getPlayer().isShiftKeyDown() && dtarget instanceof RuneEnergyPylonTile pylon) {
                   pylon.addEnergy(pylon.getEnergyType(),200);
            }else{
                switchPylons(pos,world);

            }
//
//            if (dtarget instanceof RunicEnergyRepeaterTile repeater){
//                for (BlockPos connection : repeater.getConnections()){
//                    ((ServerLevel)world).sendParticles(ParticleTypes.FLASH,connection.getX()+0.5,connection.getY()+1.5,
//                            connection.getZ()+0.5,1,0,0,0,0);
//                }
//            }
//            for (String s : dtarget.getDebugStrings()) {
//                ctx.getPlayer().sendSystemMessage(Component.literal(s));
//            }
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
        if (!world.isClientSide){
            for (int i = 0; i <= 10;i++){
                for (int g = 0; g <= 10;g++){
                    BlockPos pos = player.getOnPos().offset(i*10,0,g*10);
                    world.setBlock(pos,SolarcraftBlocks.REPEATER.get().defaultBlockState(),3);
                    world.setBlock(pos.below(),SolarcraftBlocks.FIRA_RUNE_BLOCK.get().defaultBlockState(),3);
                }
            }
        }
        return super.use(world, player, hand);
    }
}
