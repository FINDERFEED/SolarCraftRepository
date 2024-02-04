package com.finderfeed.solarcraft.content.entities;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.SendShapeParticlesPacket;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.instances.GroundLingeringCircleParticleShape;
import com.finderfeed.solarcraft.content.entities.projectiles.UElectricShockProjectile;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ElectricRainEntity extends AttackEffectEntity {
    private double radius;

    private int frequency;
    private float damage;

    public ElectricRainEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }


    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide){
            if (this.getLivingTicks() % 5 == 0){
                this.sendParticles();
            }
            if (this.getLivingTicks() % frequency == 0){
                this.spawnElectricProjectile();
            }
        }
    }

    private void spawnElectricProjectile(){
        float rot = (float)(Math.PI * 2 * random.nextDouble());
        Vec3 p = new Vec3(radius * random.nextFloat(),0,0).yRot(rot).add(this.position().add(0,20,0));
        UElectricShockProjectile projectile = new UElectricShockProjectile(SCEntityTypes.Projectiles.ELECTRIC_SHOCK_PROJECTILE.get(),level);
        projectile.setOwner(this.getOwner());
        projectile.setPos(p);
        projectile.setDamage(damage);
        projectile.setDeltaMovement(0,-0.6,0);
        level.addFreshEntity(projectile);
    }


    private void sendParticles(){
        FDPacketUtil.sendToTrackingEntity(this,new SendShapeParticlesPacket(
                new GroundLingeringCircleParticleShape(radius,(int)(radius * 7)),
                BallParticleOptions.Builder.begin()
                        .setLifetime(60)
                        .setSize(0.15f)
                        .setRGB(180,60,255)
                        .setShouldShrink(true)
                        .setPhysics(false)
                        .build(),
                this.position().x,
                this.position().y,
                this.position().z,
                0,random.nextFloat() * 0.1,0
        ));
    }


    @Override
    public boolean save(CompoundTag tag) {
        tag.putInt("frequency",this.frequency);
        tag.putDouble("radius",this.radius);
        tag.putFloat("damage",this.damage);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.damage = tag.getFloat("damage");
        this.radius = tag.getDouble("radius");
        this.frequency = tag.getInt("frequency");
        super.load(tag);
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }


    public void setRadius(double radius) {
        this.radius = radius;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
