package com.finderfeed.solarforge.local_library.other;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.phys.Vec3;

public class MemorizedModelPart {

    private final ModelPart part;
    private final float initX;
    private final float initY;
    private final float initZ;
    private final float initRotX;
    private final float initRotY;
    private final float initRotZ;

    public MemorizedModelPart(ModelPart part){
        this.part = part;
        this.initX = part.x;
        this.initY = part.y;
        this.initZ = part.z;
        this.initRotX = part.xRot;
        this.initRotY = part.yRot;
        this.initRotZ = part.zRot;
    }

    public ModelPart getPart() {
        return part;
    }

    public float getInitRotX() {
        return initRotX;
    }

    public float getInitRotY() {
        return initRotY;
    }

    public float getInitRotZ() {
        return initRotZ;
    }

    public float getInitX() {
        return initX;
    }

    public float getInitY() {
        return initY;
    }

    public float getInitZ() {
        return initZ;
    }
}
