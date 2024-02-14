package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.content.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.ParticleEmitterData;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.ParticleEmmitterPacket;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_emitter_processors.instances.ebpe_processor.EBPEmitterProcessorData;
import com.finderfeed.solarcraft.local_library.client.particles.particle_emitters.particle_processors.instances.random_speed.RandomSpeedProcessorData;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

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
            //StructurePatternExporter.export(world,pos.above(), pos.above().offset(4,4,4));
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
        if (!world.isClientSide){
            Vec3 iPos = player.position().add(0,player.getEyeHeight(),0);
            Vec3 ePos = iPos.add(player.getLookAngle().multiply(10,10,10));
            EntityHitResult entity = ProjectileUtil.getEntityHitResult(
                    player.level,player,iPos,ePos,new AABB(iPos,ePos).inflate(2),(e)->true
            );
            if (entity != null){
                FDPacketUtil.sendToPlayer((ServerPlayer) player,
                        new ParticleEmmitterPacket(
                                new ParticleEmitterData()
                                        .setPos(entity.getEntity().position())
                                        .setFrequency(1)
                                        .setParticle(ParticleTypes.ANGRY_VILLAGER)
                                        .addParticleEmitterProcessor(
                                                new EBPEmitterProcessorData(entity.getEntity().getId())
                                        )
                                        .addParticleProcessor(
                                                new RandomSpeedProcessorData(0.5,0.5,0.5)
                                        )
                        ));
            }
        }
        return super.use(world, player, hand);
    }
}
