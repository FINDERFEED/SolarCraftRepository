package com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.corruption_wisp;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CorruptionWisp extends FlyingMob implements PowerableMob {

    private Vec3 flyAroundPos;

    public CorruptionWisp(EntityType<? extends FlyingMob> type, Level world) {
        super(type, world);
    }


    @Override
    public void tick() {
        super.tick();
        if (flyAroundPos == null) flyAroundPos = position();
        Quaternion quaternion = Vector3f.YN.rotation(level.getGameTime()/200f);
        Vec3 v = this.position().subtract(flyAroundPos);
        Vector3f vector3f = new Vector3f((float)v.x,(float)v.y,0);
        vector3f.transform(quaternion);
        this.setPos(flyAroundPos.add(vector3f.x(),vector3f.y(),vector3f.z()));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D);
    }


    @Override
    public boolean isPowered() {

        return true;
    }
}
