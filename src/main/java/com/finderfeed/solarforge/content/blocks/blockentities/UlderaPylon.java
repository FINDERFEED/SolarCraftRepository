package com.finderfeed.solarforge.content.blocks.blockentities;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.content.blocks.blockentities.projectiles.ShadowBolt;
import com.finderfeed.solarforge.content.entities.projectiles.SummoningProjectile;
import com.finderfeed.solarforge.registries.entities.SolarcraftEntityTypes;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class UlderaPylon extends BlockEntity {

    private int spawnZombieCooldown = 0;

    public UlderaPylon(BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.ULDERA_PYLON.get(), p_155229_, p_155230_);
    }

    public static void tick(UlderaPylon tile, BlockPos pos, BlockState state, Level world){
        if (!world.isClientSide){
            tile.spawnZombieCooldown--;
            if (world.getGameTime() % 20 == 0){
                AABB aabb = new AABB(-20,-20,-20,20,20,20).move(Helpers.getBlockCenter(pos));
                Vec3 p = Helpers.getBlockCenter(pos);

                List<LivingEntity> livings = world.getEntitiesOfClass(LivingEntity.class,aabb,(e)->{
                    if (e.isDeadOrDying()) return false;
                    if (e instanceof Player player){
                        if (player.isCreative() || player.isSpectator()){
                            return false;
                        }
                    }
                    if (!(e.distanceToSqr(p) <= 20 * 20)){
                        return false;
                    }
                    Vec3 v = e.position().add(0,e.getBbHeight()/2,0).subtract(p).normalize().multiply(0.5,0.5,0.5);
                    ClipContext clipContext = new ClipContext(p.add(v),e.position().add(0,e.getBbHeight()/2,0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null);
                    return world.clip(clipContext).getType() == HitResult.Type.MISS;
                });
                if (!livings.isEmpty()) {
                    LivingEntity target = livings.get(world.random.nextInt(livings.size()));
                    Vec3 ePos = target.position().add(0,target.getBbHeight()/2,0);
                    Vec3 between = ePos.subtract(p);
                    ShadowBolt bolt = new ShadowBolt(SolarcraftEntityTypes.SHADOW_BOLT.get(),world);
                    bolt.setPos(p.add(between.normalize().multiply(0.5,0.5,0.5)));
                    bolt.setDeltaMovement(between.normalize().multiply(2,2,2));
                    world.addFreshEntity(bolt);
                }

                List<Player> players = world.getEntitiesOfClass(Player.class,aabb);
                if (!players.isEmpty() && tile.spawnZombieCooldown <= 0 && Helpers.isDay(world)){
                    tile.spawnZombieCooldown = 1200;
                    SummoningProjectile projectile = new SummoningProjectile(world,SolarcraftEntityTypes.SHADOW_ZOMBIE.get(),
                            43,0,60);
                    double speedMult = world.random.nextDouble()*0.2 + 0.1;
                    Vec3 rnd = new Vec3(1,0,0).yRot(world.random.nextFloat()*360).multiply(speedMult,speedMult,speedMult);
                    projectile.setFallSpeedDecrement(0.025);
                    projectile.setPos(Helpers.getBlockCenter(tile.getBlockPos()).add(0,0.5,0));
                    projectile.setDeltaMovement(rnd.add(0,0.1,0));
                    world.addFreshEntity(projectile);
                }
            }

        }else{
            if (world.getGameTime() % 4 == 0) {
                Vec3 p = Helpers.getBlockCenter(pos).add(Helpers.randomVector().multiply(0.5,0.5,0.5));
                ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                        p.x, p.y, p.z, 0, 0.05, 0, () -> 50, () -> 0, () -> 130, 0.4f);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("cooldown",spawnZombieCooldown);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.spawnZombieCooldown = tag.getInt("cooldown");
    }
}
