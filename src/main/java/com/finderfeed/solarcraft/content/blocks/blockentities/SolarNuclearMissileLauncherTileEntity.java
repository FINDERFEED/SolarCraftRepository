package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarcraft.content.entities.projectiles.OrbitalExplosionProjectile;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.entities.SolarcraftEntityTypes;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SolarNuclearMissileLauncherTileEntity extends AbstractRunicEnergyContainer {

    public static final int MAX_RADIUS = 300;
    public static final int MAX_DEPTH = 125;
    public static final int RE_PER_ONE_RING = 300;

    public static final int RE_LIMIT = (MAX_RADIUS + MAX_DEPTH)*RE_PER_ONE_RING;

    private MissileData data;
    private int launchTicker;

    public SolarNuclearMissileLauncherTileEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.NUCLEAR_MISSILE_LAUNCHER.get(), p_155229_, p_155230_);
    }


    public static void tick(SolarNuclearMissileLauncherTileEntity tile){
        Level level = tile.level;
        if (level == null) return;

        if (!level.isClientSide){
            if (tile.getMissileData() != null){
                tile.processMissile();
            }else{
                tile.launchTicker = 0;
                tile.data = null;
            }
        }
    }

    private void processMissile(){
        if (!this.hasEnoughRunicEnergy(this.getMissileData().cost, 1)){
            this.requestRunicEnergy(this.getMissileData().cost, 1);
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
        }else{
            Helpers.updateTile(this);
            launchTicker--;
        }
    }

    public int getLaunchTicker() {
        return launchTicker;
    }


    public void setMissileData(MissileData data) {
        this.data = data;
        if (data != null){
            this.launchTicker = data.radius*20;
        }else{
            this.launchTicker = 0;
        }
        Helpers.updateTile(this);
    }

    public MissileData getMissileData() {
        return data;
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
            this.cost.set(RunicEnergy.Type.TERA,cost);
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
            return FDMathHelper.isBetweenValues(xDest,-Level.MAX_LEVEL_SIZE,Level.MAX_LEVEL_SIZE) &&
                    FDMathHelper.isBetweenValues(zDest,-Level.MAX_LEVEL_SIZE,Level.MAX_LEVEL_SIZE) &&
                    FDMathHelper.isBetweenValues(radius,0,300) &&
                    FDMathHelper.isBetweenValues(depth,0,125);
        }
    }
}
