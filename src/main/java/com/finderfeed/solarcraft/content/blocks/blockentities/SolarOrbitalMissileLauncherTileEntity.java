package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarcraft.config.SolarcraftConfig;
import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarcraft.content.entities.projectiles.OrbitalExplosionProjectile;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.entities.SolarcraftEntityTypes;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SolarOrbitalMissileLauncherTileEntity extends AbstractRunicEnergyContainer {

    public static final int MAX_RADIUS = 300;
    public static final int MAX_DEPTH = 125;
    public static final int RE_PER_ONE_RING = 300;

    public static final int RE_LIMIT = (MAX_RADIUS + MAX_DEPTH)*RE_PER_ONE_RING;

    private MissileData data;
    private int launchTicker;

    public SolarOrbitalMissileLauncherTileEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.ORBITAL_MISSILE_LAUNCHER.get(), p_155229_, p_155230_);
    }


    public static void tick(SolarOrbitalMissileLauncherTileEntity tile){
        Level level = tile.level;
        if (level == null) return;

        if (!level.isClientSide){
            if (tile.getMissileData() != null){
                tile.processMissile();
            }else{
                tile.launchTicker = 0;
                tile.data = null;
            }
        }else{
            tile.spawnParticles();
        }
    }


    private void spawnParticles(){
        if (this.getMissileData() == null) return;


        for (int i = 0; i < 2;i++){
            Vec3 pos = new Vec3(
              level.random.nextFloat(),
              level.random.nextFloat(),
              level.random.nextFloat()
            ).add(Helpers.posToVec(this.getBlockPos())).add(0,1,0);
            ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    pos.x,pos.y,pos.z,0,0,0,
                    level.random.nextInt(40)+215,
                    level.random.nextInt(40)+215,
                    level.random.nextInt(40)
                    ,0.25f);
        }


        float time = level.getGameTime();
        double y = Math.abs(time % 180 - 90) / 20f;
        double radius = 2.3 + Math.exp(-y/2) * (-0.5*y/2 + 1);
        Vec3 center = Helpers.getBlockCenter(this.getBlockPos()).add(0,-2.5 + y,0);
        int count = 4;
        for (int i = 0; i < count; i++){
            double angle = Math.PI * 2f / count;
            Vec3 pos = new Vec3(
              Math.sin(angle * i + time) * radius,
              0,
              Math.cos(angle * i + time) * radius
            ).add(center);
            ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    pos.x,pos.y,pos.z,0,0,0,
                    level.random.nextInt(40)+215,
                    level.random.nextInt(40)+215,
                    level.random.nextInt(40)
                    ,0.25f);

        }
    }
///execute in minecraft:overworld run tp @s -948448.94 73.94 30264.19 -802.29 52.07
    private void processMissile(){

        if (!SolarcraftConfig.IS_ORBITAL_MISSILE_LAUNCHER_ALLOWED.get()){
            return;
        }

        if (!this.hasEnoughRunicEnergy(this.getMissileData().cost, 1)){
            this.requestRunicEnergy(this.getMissileData().cost, 1);
            return;
        }

        if (!Multiblocks.ORBITAL_MISSILE_LAUNCHER.check(level,worldPosition,true)){
            this.setMissileData(null);
            return;
        }
        if (launchTicker <= 0){
            MissileData data = this.getMissileData();
            OrbitalExplosionProjectile projectile = new OrbitalExplosionProjectile(SolarcraftEntityTypes.ORBITAL_EXPLOSION_PROJECTILE.get(),level);
            projectile.setPos(Helpers.getBlockCenter(this.getBlockPos().above()));
            projectile.setDestination(data.xDest(),data.zDest());
            projectile.setExplosionDepth(data.depth());
            projectile.setExplosionRadius(data.radius());
            level.addFreshEntity(projectile);
            launchTicker = 0;
            setMissileData(null);
            Helpers.updateTile(this);
            Helpers.loadChunkAtPos((ServerLevel) level,getBlockPos(),false,true);
            ChunkPos pos = new ChunkPos(getBlockPos());
            Helpers.loadChunkAtPos((ServerLevel) level,new BlockPos(pos.getMinBlockX(),0,pos.getMinBlockZ()),true,true);
        }else{
            if (--launchTicker % 20 == 0){

                Helpers.updateTile(this);
            }
        }
    }

    public int getLaunchTicker() {
        return launchTicker;
    }


    public void setMissileData(MissileData data) {
        this.data = data;

        if (data != null){
            this.launchTicker = Math.max(data.radius,data.depth)*20;
            this.launchTicker = 100;
            if (level instanceof ServerLevel sLevel) {
                Helpers.loadChunkAtPos(sLevel, getBlockPos(), true, true);
            }
        }else{
            this.launchTicker = 0;
        }
        Helpers.updateTile(this);
    }

    public MissileData getMissileData() {
        return data;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        ClientboundBlockEntityDataPacket d = super.getUpdatePacket();
        saveAdditional(d.getTag());
        return d;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (data != null){
            data.save(tag);
        }
        tag.putInt("launchTicker",this.launchTicker);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.setMissileData(MissileData.load(tag));
        this.launchTicker = tag.getInt("launchTicker");
    }


    @Override
    public float getREPerTickInput() {
        return 30;
    }

    @Override
    public float getRunicEnergyLimit() {
        return RE_LIMIT;
    }

    @Override
    public int getSeekCooldown() {
        return 40;
    }

    @Override
    public double getMaxRange() {
        return 20;
    }

    @Override
    public boolean shouldFunction() {
        return true;
    }

    @Override
    public boolean saveAndLoadEverything() {
        return true;
    }

    public static class MissileData{

        private RunicEnergyCost cost;

        private int xDest;
        private int zDest;
        private int radius;
        private int depth;


        public MissileData(int xDest, int zDest, int radius, int depth){
            this.xDest = xDest;
            this.zDest = zDest;
            this.radius = radius;
            this.depth = depth;
            this.cost = new RunicEnergyCost();
            float cost = (radius + depth)*RE_PER_ONE_RING;
            this.cost.set(RunicEnergy.Type.KELDA,cost);
        }

        public void save(CompoundTag tag){
            CompoundTag t = new CompoundTag();
            t.putInt("xDest",xDest);
            t.putInt("zDest",zDest);
            t.putInt("radius",radius);
            t.putInt("depth",depth);
            tag.put("missileData",t);
        }

        @Nullable
        public static MissileData load(CompoundTag tag){
            if (tag.contains("missileData")){
                CompoundTag t = tag.getCompound("missileData");
                return new MissileData(
                  t.getInt("xDest"),
                  t.getInt("zDest"),
                  t.getInt("radius"),
                  t.getInt("depth")
                );
            }else{
                return null;
            }
        }

        public int depth() {
            return depth;
        }

        public int radius() {
            return radius;
        }

        public int xDest() {
            return xDest;
        }

        public int zDest() {
            return zDest;
        }

        public boolean isValid(){
            if (radius == 0 || depth == 0){
                return false;
            }
            return FDMathHelper.isBetweenValues(xDest,-Level.MAX_LEVEL_SIZE,Level.MAX_LEVEL_SIZE) &&
                    FDMathHelper.isBetweenValues(zDest,-Level.MAX_LEVEL_SIZE,Level.MAX_LEVEL_SIZE) &&
                    FDMathHelper.isBetweenValues(radius,0,300) &&
                    FDMathHelper.isBetweenValues(depth,0,125);
        }
    }
}
