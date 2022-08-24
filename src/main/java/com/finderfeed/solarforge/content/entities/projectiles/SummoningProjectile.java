package com.finderfeed.solarforge.content.entities.projectiles;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.registries.data_serializers.FDEntityDataSerializers;
import com.finderfeed.solarforge.registries.entities.SolarcraftEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class SummoningProjectile extends NormalProjectile {

    public static final EntityDataAccessor<Vec3> COLOR = SynchedEntityData.defineId(SummoningProjectile.class,(EntityDataSerializer<Vec3>)FDEntityDataSerializers.VEC3.get().getSerializer());

    private EntityType<? extends LivingEntity> summoningEntityType = EntityType.SHEEP;
    private float r = 0f;
    private float g = 0f;
    private float b = 0f;
    private double fallSpeedDecrement = 0.01f;


    public SummoningProjectile(EntityType<? extends NormalProjectile> type, Level world) {
        super(type, world);
    }

    public SummoningProjectile(Level world,EntityType<? extends LivingEntity> entityToSummon,float rPCol,float gPCol,float bPCol){
        super(SolarcraftEntityTypes.SUMMONING_PROJECTILE.get(),world);
        this.summoningEntityType = entityToSummon;
        this.r = rPCol;
        this.g = gPCol;
        this.b = bPCol;
        this.entityData.set(COLOR,new Vec3(r,g,b));
    }

    @Override
    public void tick() {
        if (!level.isClientSide){
            this.setDeltaMovement(this.getDeltaMovement().add(0,-fallSpeedDecrement,0));
            this.entityData.set(COLOR,new Vec3(r,g,b));
        }else{
            for (int i = 0; i < 3;i++) {
                double rx = level.random.nextDouble()-0.5d;
                double ry = level.random.nextDouble()-0.5d;
                double rz = level.random.nextDouble()-0.5d;
                Vec3 v = this.position().add(rx,ry,rz);
                Vec3 color = this.entityData.get(COLOR);
                ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                        v.x,v.y,v.z,0,0,0,(int)(color.x*255),(int)(color.y*255),(int)(color.z*255),0.5f);
            }
        }
        super.tick();
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putString("typeRegID",summoningEntityType.getRegistryName().toString());
        tag.putFloat("rcolor",r);
        tag.putFloat("gcolor",g);
        tag.putFloat("bcolor",b);
        tag.putDouble("fallSpeed", fallSpeedDecrement);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        EntityType<? extends LivingEntity> type = (EntityType<? extends LivingEntity>) ForgeRegistries.ENTITIES.getValue(new ResourceLocation(tag.getString("typeRegID")));
        if (type != null){
            this.summoningEntityType = type;
        }else{
            this.summoningEntityType = EntityType.SHEEP;
        }
        r = tag.getFloat("rcolor");
        g = tag.getFloat("gcolor");
        b = tag.getFloat("bcolor");
        this.entityData.set(COLOR,new Vec3(r,g,b));
        fallSpeedDecrement = tag.getDouble("fallSpeed");
    }

    @Override
    protected void onHitBlock(BlockHitResult res) {
        if (!level.isClientSide){
            LivingEntity entity = summoningEntityType.create(level);
            if (entity != null) {
                Vec3 loc = res.getLocation();
                entity.setPos(loc.add(0, 0.5, 0));
                level.addFreshEntity(entity);
            }
        }
        this.remove(RemovalReason.DISCARDED);
        super.onHitBlock(res);
    }



    public void setFallSpeedDecrement(double fallSpeedDecrement) {
        this.fallSpeedDecrement = fallSpeedDecrement;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(COLOR,Vec3.ZERO);
    }


    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
