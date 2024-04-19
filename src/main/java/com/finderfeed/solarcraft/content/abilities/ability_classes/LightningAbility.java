package com.finderfeed.solarcraft.content.abilities.ability_classes;

import com.finderfeed.solarcraft.client.particles.server_data.shapes.SendShapeParticlesPacket;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.instances.LightningAbilityParticleShape;
import com.finderfeed.solarcraft.content.abilities.AbilityStats;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.entities.not_alive.MyFallingBlockEntity;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.registries.SCConfigs;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;

public class LightningAbility extends AbstractAbility{

    public static String EXPLOSION_POWER_TOP = "explosionPowerOnTop";
    public static String EXPLOSION_POWER_BOTTOM = "explosionPowerBottom";

    public LightningAbility() {
        super("lightning");
    }

    @Override
    public void cast(ServerPlayer entity, ServerLevel world) {
        if (AbilityHelper.isAbilityUsable(entity,this,true)) {

            Vec3 vec = entity.getLookAngle().multiply(200, 200, 200);
            ClipContext ctx = new ClipContext(entity.position().add(0, 1.5, 0), entity.position().add(0, 1.5, 0).add(vec), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
            BlockHitResult result = world.clip(ctx);
            if (result.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = result.getBlockPos();
                if (world.canSeeSky(pos.above())) {
                    LightningBolt entityBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
                    entityBolt.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    AbilityStats stats = SCConfigs.ABILITIES.lightningAbilityStats;
                    float top = stats.getStat(EXPLOSION_POWER_TOP);
                    float bottom = stats.getStat(EXPLOSION_POWER_BOTTOM);
                    if (Helpers.isSpellGriefingEnabled(world)) {
                        Explosion explosion = Helpers.oldExplosionConstructor(world, null, null, StoneDestroyerCalculator.INSTANCE_01, pos.getX(), pos.getY() - 3, pos.getZ(), top, true, Explosion.BlockInteraction.DESTROY);
                        spawnFallingBlocks(world, pos.below(3), explosion);

                        world.explode(entity, null, StoneDestroyerCalculator.INSTANCE_01, pos.getX(), pos.getY() - 1, pos.getZ(), top, true, Level.ExplosionInteraction.BLOCK);
                        world.explode(entity, null, StoneDestroyerCalculator.INSTANCE_01, pos.getX(), pos.getY() - 5, pos.getZ(), bottom, true, Level.ExplosionInteraction.BLOCK);
                    }else{
                        world.explode(entity, null, StoneDestroyerCalculator.INSTANCE_01, pos.getX(), pos.getY() - 1, pos.getZ(), top, true, Level.ExplosionInteraction.BLOCK);
                        world.explode(entity, null, StoneDestroyerCalculator.INSTANCE_01, pos.getX(), pos.getY() - 5, pos.getZ(), bottom, true, Level.ExplosionInteraction.BLOCK);
                    }
                    AbilityHelper.spendAbilityEnergy(entity,this);
                    world.addFreshEntity(entityBolt);
                    LightningAbilityParticleShape shape = new LightningAbilityParticleShape();
                    FDPacketUtil.sendToTrackingEntity(entityBolt,new SendShapeParticlesPacket(shape, ParticleTypes.FIREWORK,
                            pos.getX() + 0.5,pos.getY(),pos.getZ() + 0.5,0,0,0));
                }
            }
        }
    }

    private void spawnParticles(){

    }




    public void spawnFallingBlocks(Level world,BlockPos mainpos,Explosion expl){
        Vec3 center = Helpers.getBlockCenter(mainpos);

        for (int x = -2; x <= 2;x++){
            for (int y = -2; y <= 2;y++){
                for (int z = -2; z <= 2;z++){
                    BlockPos offsettedPos = mainpos.offset(x,y,z);
                    Vec3 position = Helpers.getBlockCenter(offsettedPos);
                    Vec3 speed = position.subtract(center).multiply(0.3*world.random.nextDouble(),0.1*world.random.nextDouble(),0.3*world.random.nextDouble()).add(0,1.5,0);
                    if (speed.x  == 0 && speed.z == 0){
                        speed = speed.add(world.random.nextFloat() * 0.2 - 0.1,0,world.random.nextFloat() * 0.2 - 0.1);
                    }
                    BlockState state = world.getBlockState(offsettedPos);
                    if (state.getExplosionResistance(world,mainpos,expl) <= 6) {
                        MyFallingBlockEntity fallingBlock = new MyFallingBlockEntity(world, position.x, position.y + 3, position.z, state);
                        fallingBlock.setDeltaMovement(speed);
                        world.addFreshEntity(fallingBlock);
                    }
                }
            }
        }

    }

    @Override
    public RunicEnergyCost getCastCost() {
        return SCConfigs.ABILITIES.lightningAbilityStats.getDefaultAbilityStats().getCastCost();
    }

    @Override
    public int getBuyCost() {
        return SCConfigs.ABILITIES.lightningAbilityStats.getDefaultAbilityStats().getBuyCost();
    }
}


