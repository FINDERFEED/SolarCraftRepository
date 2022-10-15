package com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.corruption_wisp;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.ClearingRitualCrystalTile;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CorruptionWisp extends FlyingMob implements PowerableMob {

    private Vec3 flyAroundPos;
    private Vec3 targetPoint;
    private BlockPos boundCrystalPos;
    public CorruptionWisp(EntityType<? extends FlyingMob> type, Level world) {
        super(type, world);
    }


    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide) {
            ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    position().x,position().y + 0.175f,position().z,0,0,0,()->150 + level.random.nextInt(50),()->0,()->177,0.25f);
            return;
        }
        if (flyAroundPos == null) flyAroundPos = position();
        if (targetPoint == null) targetPoint = new Vec3(position().x,position().y,position().z);
        if (isNearTargetPoint()){
            Vec3 v = targetPoint.subtract(flyAroundPos).yRot((float)Math.toRadians(10));
            this.targetPoint = flyAroundPos.add(v);
        }
        Vec3 moveVec = targetPoint.subtract(position()).normalize();
        this.setDeltaMovement(moveVec.multiply(0.1,0.1,0.1));

    }

    @Override
    public void remove(RemovalReason reason) {
        if (!level.isClientSide && boundCrystalPos != null){
            if (level.getBlockEntity(boundCrystalPos) instanceof ClearingRitualCrystalTile tile){
                tile.wispKiled();
            }
        }
        super.remove(reason);
    }

    public boolean isNearTargetPoint(){
        return position().subtract(targetPoint).length() < 0.1;
    }

    public void setBoundCrystalPos(BlockPos boundCrystalPos) {
        this.boundCrystalPos = boundCrystalPos;
    }

    public void setFlyAroundPos(Vec3 flyAroundPos) {
        this.flyAroundPos = flyAroundPos;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D);
    }


    @Override
    public boolean save(CompoundTag tag) {
        CompoundNBTHelper.writeVec3("anchor",flyAroundPos,tag);
        if (boundCrystalPos != null) {
            CompoundNBTHelper.writeBlockPos("crystalPos", this.boundCrystalPos, tag);
        }
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.boundCrystalPos = CompoundNBTHelper.getBlockPos("crystalPos",tag);
        this.flyAroundPos = CompoundNBTHelper.getVec3("anchor",tag);
        super.load(tag);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isAffectedByPotions() {
        return false;
    }

    @Override
    public boolean isPowered() {
        return true;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SolarcraftSounds.CORRUPTION_WISP_HIT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource src) {
        return SolarcraftSounds.CORRUPTION_WISP_HIT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {

    }
}

