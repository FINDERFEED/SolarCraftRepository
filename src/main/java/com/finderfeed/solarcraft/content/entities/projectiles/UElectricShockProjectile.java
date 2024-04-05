package com.finderfeed.solarcraft.content.entities.projectiles;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.lightning_particle.LightningParticleOptions;
import com.finderfeed.solarcraft.content.entities.uldera_crystal.UlderaCrystalBuddy;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class UElectricShockProjectile extends OwnedProjectile {

    private float damage;
    public UElectricShockProjectile(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    public UElectricShockProjectile(EntityType<? extends AbstractHurtingProjectile> x, double y, double z, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_) {
        super(x, y, z, p_36820_, p_36821_, p_36822_, p_36823_, p_36824_);
    }

    public UElectricShockProjectile(EntityType<? extends AbstractHurtingProjectile> p_36826_, LivingEntity owner, double p_36828_, double p_36829_, double p_36830_, Level p_36831_) {
        super(p_36826_, owner, p_36828_, p_36829_, p_36830_, p_36831_);
    }


    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide){
            this.clientTick();
        } else {
            this.detectEntityHit();
        }
    }

    private void clientTick(){
        BallParticleOptions options = new BallParticleOptions.Builder().setRGB(90,0,186).setPhysics(false)
                .setShouldShrink(true).setLifetime(30).setSize(0.5f).build();
        for (int i = 0; i < 3;i++){
            Vec3 pos = this.position().add(0, this.getBbHeight() / 2, 0).add(
                    level.random.nextFloat() - 0.5f,
                    level.random.nextFloat() - 0.5f,
                    level.random.nextFloat() - 0.5f
            );
            level.addParticle(options,pos.x,pos.y,pos.z,0,0,0);
        }
        if (level.getGameTime() % 3 == 0){
            LightningParticleOptions loptions = new LightningParticleOptions(1f,90,0,186,-1,30,false);
            Vec3 pos = this.position().add(0, this.getBbHeight() / 2, 0).add(
                    level.random.nextFloat() - 0.5f,
                    level.random.nextFloat() - 0.5f,
                    level.random.nextFloat() - 0.5f
            );
            level.addParticle(loptions,pos.x,pos.y,pos.z,0,0,0);
        }
    }


    private void detectEntityHit(){
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,this.getBoundingBox().inflate(0.25));
        for (LivingEntity entity : entities){
            if (entity.getBoundingBox().intersects(this.getBoundingBox())){
                this.onEntityHit(entity);
                return;
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        if (!level.isClientSide){
            this.kill();
        }
        super.onHitBlock(p_37258_);
    }

    private void onEntityHit(LivingEntity entity){
        if (entity instanceof Player player){
            if (player.isCreative() || player.isSpectator()){
                return;
            }
        }

        LivingEntity owner = null;
        if (entity != (owner = this.getLivingEntityOwner()) && !(entity instanceof UlderaCrystalBuddy)){
            if (owner != null){
                entity.hurt(SCDamageSources.livingAllResistanceIgnore(owner),this.damage);
            } else {
                entity.hurt(level.damageSources().magic(),this.damage);
            }
            if (entity instanceof Player player){
                this.lockFood(player);
            }
            this.kill();
        }
    }

    private void lockFood(Player player){
        Inventory inventory;
        for (int i = 0; i < (inventory = player.getInventory()).getContainerSize();i++){
            ItemStack item = inventory.getItem(i);
            if (item.isEdible()){
                player.getCooldowns().addCooldown(item.getItem(),200);
            }
        }
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putFloat("damage",this.damage);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.damage = tag.getFloat("damage");
        super.load(tag);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }
}
