package com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.corruption_wisp.CorruptionWisp;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile.ClearingRitualMainTile;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundSource;
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
    private int wispsCount = 0;
    private int wispSpawnTicks = 0;

    private RunicEnergy.Type type = null;

    public ClearingRitualCrystalTile( BlockPos pos, BlockState state) {
        super(SolarcraftTileEntityTypes.CLEARING_RITUAL_CRYSTAL.get(), pos, state);
    }

    //don't ask me
    public static void superDuperTickYouCanNeverImagineHowSuperItIs(Level world,BlockState state,BlockPos pos,ClearingRitualCrystalTile tile){
        if (!world.isClientSide && tile.getREType() == null){
            RunicEnergy.Type[] types = RunicEnergy.Type.getAll();
            tile.setREType(types[world.getRandom().nextInt(types.length)]);
        }
        tile.handleOverloading();
        tile.handleCorruption();
        tile.ticker++;
    }

    private void handleOverloading(){
        if (this.overloaded){
            Vec3 c = Helpers.getBlockCenter(worldPosition);
            if (this.overloadTicker++ > 100) {
                if (!level.isClientSide) {
                    this.overloaded = false;
                    this.overloadTicker = 0;
                    this.explode(true);
                }
            }else{
                if (level.isClientSide){
                    Vec3 rnd = Helpers.randomVector().multiply(0.05,0.05,0.05);
                    ClientHelpers.Particles.createParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                            c.x,c.y,c.z,rnd.x,rnd.y,rnd.z,()->255,()->255,()->level.random.nextInt(40),0.5f);
                }
            }
        }
    }

    private void handleCorruption(){
        if (this.isCorrupted()) {
            Vec3 c = Helpers.getBlockCenter(worldPosition);

            if (!level.isClientSide) {
                if (corruptionTicks > 600){
                    for (CorruptionWisp wisp : level.getEntitiesOfClass(CorruptionWisp.class,Helpers.createAABBWithRadius(c,3,3))) {
                        wisp.kill();
                    }
                    Helpers.collectTilesInChunks(SolarcraftTileEntityTypes.CLEARING_RITUAL_MAIN_BLOCK.get(),level,this.getBlockPos(),2)
                            .forEach(ClearingRitualMainTile::notifyCrystalExploded);
                    this.explode(false);
                    return;
                }
                if (this.wispsCount < 3) {
                    if (this.wispSpawnTicks == 0) {
                        CorruptionWisp wisp = new CorruptionWisp(SCEntityTypes.CORRUPTION_WISP.get(), level);
                        Vec3 p = c.add(2, 0.75 - wispsCount*0.75, 0);
                        wisp.setPos(p);
                        wisp.setFlyAroundPos(c);
                        wisp.setBoundCrystalPos(worldPosition);
                        level.addFreshEntity(wisp);
                        this.wispsCount++;
                        this.wispSpawnTicks = 200;
                    }
                    this.wispSpawnTicks = FDMathHelper.clamp(0, this.wispSpawnTicks - 1, 200);
                }
            }
            this.corruptionTicks++;
        }else{
            this.corruptionTicks = 0;
            this.wispsCount = 0;
            this.wispSpawnTicks = 0;
        }
    }

    private void explode(boolean throwItem){
        Vec3 c = Helpers.getBlockCenter(worldPosition);
        level.explode(null, c.x, c.y, c.z, 7f, Level.ExplosionInteraction.BLOCK);
        level.destroyBlock(worldPosition, throwItem);
    }

    public int getTicker() {
        return ticker;
    }

    public void overload(){
        this.overloaded = true;
        Vec3 center = Helpers.getBlockCenter(worldPosition);
        level.playSound(null,center.x,center.y,center.z, SolarcraftSounds.CLEARING_CRYSTAL_OVERLOAD.get(), SoundSource.BLOCKS,5,1);
        Helpers.updateTile(this);
    }

    public boolean isCorrupted() {
        return corrupted;
    }

    public void setCorruptionTicks(int corruptionTicks) {
        this.corruptionTicks = corruptionTicks;
    }

    public void setCorrupted(boolean corrupted) {
        this.corrupted = corrupted;
        Helpers.updateTile(this);
    }

    public void wispKiled(){
        this.wispsCount = FDMathHelper.clamp(0,wispsCount-1,3);
        if (wispsCount == 0){
            setCorrupted(false);
        }
    }

    public void setREType(RunicEnergy.Type type) {
        this.type = type;
        Helpers.updateTile(this);
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
        tag.putBoolean("corrupted",corrupted);
        if (type != null) {
            tag.putString("retype", type.id);
        }
        tag.putInt("wisps",wispsCount);
        tag.putInt("wispTicks",wispSpawnTicks);
    }

    @Override
    public void load(CompoundTag tag) {
        this.overloaded = tag.getBoolean("overloaded");
        this.corrupted = tag.getBoolean("corrupted");
        this.wispsCount = tag.getInt("wisps");
        this.wispSpawnTicks = tag.getInt("wispTicks");
        if (tag.contains("retype")){
            this.type = RunicEnergy.Type.byId(tag.getString("retype"));
        }
        super.load(tag);
    }
}
