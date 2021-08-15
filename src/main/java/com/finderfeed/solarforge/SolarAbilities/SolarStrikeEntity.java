package com.finderfeed.solarforge.SolarAbilities;


import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.config.SolarcraftConfig;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import net.minecraft.world.level.block.Blocks;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;

import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import net.minecraft.world.level.Level;

import net.minecraftforge.common.util.Constants;

import net.minecraftforge.fmllegacy.network.NetworkHooks;

import java.util.List;


public class SolarStrikeEntity extends PathfinderMob {



    public static EntityDataAccessor<Integer> LIFE = SynchedEntityData.defineId(SolarStrikeEntity.class, EntityDataSerializers.INT);
    public int LIFE_TICKS = 0;
    public SolarStrikeEntity(EntityType<? extends PathfinderMob> p_i48581_1_, Level p_i48581_2_) {
        super(SolarForge.SOLAR_STRIKE_ENTITY_REG.get(), p_i48581_2_);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return createLivingAttributes().add(Attributes.FOLLOW_RANGE);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);

    }

    @Override
    public CompoundTag serializeNBT() {
        return super.serializeNBT();

    }
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("life",LIFE_TICKS);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        LIFE_TICKS = compound.getInt("life");
    }
    public int getLifeTicks(){
        return this.entityData.get(LIFE);
    }


    @Override
    public void tick(){


        doParticles();
        if (!this.level.isClientSide ){
            if (this.entityData.get(LIFE) == 1){
                this.level.playSound(null,this.getOnPos().offset(0,5,0),SolarForge.SOLAR_STRIKE_BUILD_SOUND.get(),SoundSource.AMBIENT,10,1F);
            }
            LIFE_TICKS++;
            this.entityData.set(LIFE,LIFE_TICKS);

            if (this.entityData.get(LIFE) == 60) {
                doSolarStrikeExplosion(this.getOnPos().offset(0,1,0));
                //this.level.explode(null,DamageSource.DRAGON_BREATH,null, this.position().x, this.position().y, this.position().z, 10, false, Explosion.Mode.BREAK);
                List<Entity> list = this.level.getEntities(this,new AABB(-30,-30,-30,30,30,30).move(this.getOnPos()),x -> !(x instanceof Player));
                for (int i = 0; i<list.size();i++){
                    list.get(i).hurt(DamageSource.MAGIC, SolarcraftConfig.SOLAR_STRIKE_DAMAGE.get().floatValue());
                }
                this.level.playSound(null,this.getOnPos().offset(0,5,0),SolarForge.SOLAR_STRIKE_SOUND.get(),SoundSource.AMBIENT,10,0.4F);

                this.remove(RemovalReason.KILLED);
            }

        }




    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LIFE,0);
    }


    public void doSolarStrikeExplosion(BlockPos pos) {
        //8
        //3 + Math.ceil(randomRadius) * 3
        double randomRadius = this.level.random.nextFloat() * 2 + 10;
        double randomHeight = this.level.random.nextFloat() * 3 + Math.ceil(randomRadius) * 3;

        for (int i = (int) -Math.ceil(randomRadius); i <= (int) Math.ceil(randomRadius); i++) {
            for (int g = (int) -Math.ceil(randomRadius); g <= (int) Math.ceil(randomRadius); g++) {
                for (int k = (int) -Math.ceil(randomHeight); k <= (int) Math.ceil(randomHeight); k++) {
                    if (checkTochkaVEllipse(i, k, g, randomRadius, randomHeight, randomRadius)) {
                        Vec3 vec = new Vec3(i,0,g);

                        if ((this.level.random.nextDouble()*0.5 + 1)*vec.length() < randomRadius) {

                            if (this.level.getBlockState(pos.offset((int) Math.floor(i), (int) Math.floor(k), (int) Math.floor(g))).getDestroySpeed(this.level,pos.offset((int) Math.floor(i), (int) Math.floor(k), (int) Math.floor(g))) >= 0
                                    && this.level.getBlockState(pos.offset((int) Math.floor(i), (int) Math.floor(k), (int) Math.floor(g))).getDestroySpeed(this.level,pos.offset((int) Math.floor(i), (int) Math.floor(k), (int) Math.floor(g))) <= 100) {
                                this.level.setBlock(pos.offset((int) Math.floor(i), (int) Math.floor(k), (int) Math.floor(g)), Blocks.AIR.defaultBlockState(), Constants.BlockFlags.DEFAULT);
                            }


                        }

                    }
                }
            }

        }//pos.offset((int) Math.floor(i), (int) Math.floor(k), (int) Math.floor(g))
    }


    public void doParticles(){
        if (this.level.isClientSide && this.entityData.get(LIFE) == 59){

            for (int i = 0;i <48;i++){

                float length = 34;
                double offsetx = length * Math.cos(Math.toRadians(i*7.5));
                double offsetz = length * Math.sin(Math.toRadians(i*7.5));
                this.level.addParticle(ParticlesList.SOLAR_STRIKE_PARTICLE.get(),this.position().x +offsetx,this.position().y,this.position().z +offsetz,0,0.05,0);



            }
            for (int i = 0;i <24;i++){
                for (int g = 0; g < 10;g++){
                    float length = 34;
                    double offsetx = this.level.random.nextFloat()*length * Math.cos(Math.toRadians(i*15));
                    double offsetz = this.level.random.nextFloat()*length * Math.sin(Math.toRadians(i*15));
                    this.level.addParticle(ParticlesList.SOLAR_STRIKE_PARTICLE.get(),this.position().x +offsetx,this.position().y,this.position().z +offsetz,0,0.05,0);

                }

            }
            for (int h = 0;h <25;h++){

                for (int i = 0; i < 6; i++) {
                    for (int g = 0; g < 1; g++) {
                        float length = 3;
                        double offsetx = this.level.random.nextFloat() * length * Math.cos(Math.toRadians(i * 60));
                        double offsetz = this.level.random.nextFloat() * length * Math.sin(Math.toRadians(i * 60));
                        double offsety = this.level.random.nextFloat() * length + h*8;
                        this.level.addParticle(ParticlesList.SOLAR_STRIKE_PARTICLE.get(), this.position().x + offsetx, this.position().y +offsety, this.position().z + offsetz, 0, 0.05, 0);

                    }

                }
            }

        }
    }


    public static boolean checkTochkaVEllipse(double xtochka,double ytochka,double ztochka,double xrad,double yrad,double zrad){
        double first;
        double second;
        double third;

        if (xrad != 0) {
            first = (xtochka * xtochka) / (xrad * xrad);
        }else{
            first = 0;
        }
        if (yrad != 0) {
            second = (ytochka*ytochka)/(yrad*yrad);
        }else{
            second = 0;
        }
        if (zrad != 0) {
            third = (ztochka*ztochka)/(zrad*zrad);
        }else{
            third = 0;
        }


        return first + second + third <= 1;
    }

}
/*
int radius = 21-h;

            BlockPos startposverh = pos.offset(radius,-h,0);
            BlockPos startposniz = pos.offset(radius,-h,0);
             for(int u = radius;u > 0;u--){
                 for (int b =radius - u;b >= 0;b-- ){
                     this.level.removeBlock(startposverh.offset(-u,0,b),false);
                     this.level.removeBlock(startposverh.offset(-u,0,-b),false);

                     this.level.removeBlock(startposverh.offset(-u,0,b),false);
                     this.level.removeBlock(startposverh.offset(-u,0,-b),false);
                 }

             }
 */