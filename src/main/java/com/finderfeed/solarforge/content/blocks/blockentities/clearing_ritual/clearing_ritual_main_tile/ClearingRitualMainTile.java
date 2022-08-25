package com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.ClearingRitual;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ClearingRitualMainTile extends BlockEntity {


    public ClearingRitual ritual = new ClearingRitual(this);


    public ClearingRitualMainTile(BlockPos pos, BlockState state) {
        super(SolarcraftTileEntityTypes.CLEARING_RITUAL_MAIN_BLOCK.get(), pos, state);
    }

    public static void tick(ClearingRitualMainTile tile, BlockPos pos, BlockState state, Level world){
        tile.ritual.tick();
        if (world.isClientSide && tile.ritual.ritualOnline()){
            handleRitualParticles(tile,pos,state,world);
        }
    }

    private static void handleRitualParticles(ClearingRitualMainTile tile, BlockPos pos, BlockState state, Level world){
        Vec3 center = Helpers.getBlockCenter(pos);
        for (int i = 0;i <= 4;i++) {
            Vec3 rnd = new Vec3(16,0,0).yRot((float)Math.toRadians(360*world.random.nextDouble()));
            Vec3 particleSpawnPos = center.add(rnd).add(0, -2, 0);
            Vec3 between = center.subtract(particleSpawnPos);
            Vec3 pSpeed = between.multiply(0.03, 0.03, 0.03);
            ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    particleSpawnPos.x, particleSpawnPos.y, particleSpawnPos.z, pSpeed.x, pSpeed.y, pSpeed.z,
                    220 + world.random.nextInt(35), 220 + world.random.nextInt(35), world.random.nextInt(20),
                    0.5f);
            for (int g = 0; g < 3;g++) {
                Vec3 pSpawnPosOuterRing = center.add(new Vec3(23, 0, 0).yRot((float) Math.toRadians(360 * world.random.nextDouble()))).add(0, -2, 0);
                double rxoRing = world.random.nextDouble();
                double ryoRing = world.random.nextDouble()*2.5;
                double rzoRing = world.random.nextDouble();
                double rxdoRing = world.random.nextDouble() * 0.06 - 0.03;
                double rydoRing = world.random.nextDouble() * 0.06 - 0.03;
                double rzdoRing = world.random.nextDouble() * 0.06 - 0.03;
                ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                        pSpawnPosOuterRing.x + rxoRing, pSpawnPosOuterRing.y + ryoRing, pSpawnPosOuterRing.z + rzoRing,
                        rxdoRing, rydoRing, rzdoRing,
                        220 + world.random.nextInt(35), 220 + world.random.nextInt(35), world.random.nextInt(20),
                        0.7f);
            }
        }



    }

    public void notifyCrystalExploded(){
        this.ritual.stopRitual();
    }

    public void startRitual(){
        ritual.tryStartRitual();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return Helpers.createTilePacket(this,this.getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);

        return tag;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ritual.save(tag);
    }


    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ritual.load(tag);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return Helpers.createAABBWithRadius(Helpers.getBlockCenter(this.getBlockPos()),20,100);
    }
}
