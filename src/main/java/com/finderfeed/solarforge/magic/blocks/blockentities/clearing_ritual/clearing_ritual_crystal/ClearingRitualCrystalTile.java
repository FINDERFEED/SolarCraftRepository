package com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.clearing_ritual_crystal;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.client.particles.ParticleTypesRegistry;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ClearingRitualCrystalTile extends BlockEntity {

    private boolean corrupted;
    private int corruptionTicks;
    private boolean overloaded;
    private int overloadTicker = 0;
    private int ticker = 0;
    private RunicEnergy.Type type = RunicEnergy.Type.ARDO;

    public ClearingRitualCrystalTile( BlockPos pos, BlockState state) {
        super(TileEntitiesRegistry.CLEARING_RITUAL_CRYSTAL.get(), pos, state);
    }

    //don't ask me
    public static void superDuperTickYouCanNeverImagineHowSuperItIs(Level world,BlockState state,BlockPos pos,ClearingRitualCrystalTile tile){
        if (tile.overloaded){
            Vec3 c = Helpers.getBlockCenter(pos);
            if (tile.overloadTicker++ > 80) {

                if (!world.isClientSide) {
                    tile.overloaded = false;
                    tile.overloadTicker = 0;
                    world.explode(null, c.x, c.y, c.z, 7f, Explosion.BlockInteraction.DESTROY);
                    world.destroyBlock(pos, true);
                }
            }else{
                if (world.isClientSide){
                    Vec3 rnd = Helpers.randomVector().multiply(0.05,0.05,0.05);
                    ClientHelpers.ParticleAnimationHelper.createParticle(ParticleTypesRegistry.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                            c.x,c.y,c.z,rnd.x,rnd.y,rnd.z,()->255,()->255,()->world.random.nextInt(40),0.5f);
                }
            }
        }
        if (tile.corrupted){
            if (!world.isClientSide){

            }else{

            }
        }else{
            tile.corruptionTicks = 0;
        }
        tile.ticker++;
    }

    public int getTicker() {
        return ticker;
    }

    public void overload(){
        this.overloaded = true;
        Helpers.updateTile(this);
    }

    public boolean isCorrupted() {
        return corrupted;
    }

    public void setCorrupted(boolean corrupted) {
        this.corrupted = corrupted;
        Helpers.updateTile(this);
    }

    public void setREType(RunicEnergy.Type type) {
        this.type = type;
    }

    public RunicEnergy.Type getREType() {
        return type;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return Helpers.createTilePacket(this,tag);
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
        tag.putBoolean("overloaded",overloaded);
        tag.putString("retype",type.id);
    }

    @Override
    public void load(CompoundTag tag) {
        this.overloaded = tag.getBoolean("overloaded");
        if (tag.contains("retype")){
            this.type = RunicEnergy.Type.byId(tag.getString("retype"));
        }
        super.load(tag);
    }
}
