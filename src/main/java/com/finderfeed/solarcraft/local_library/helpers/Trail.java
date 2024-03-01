package com.finderfeed.solarcraft.local_library.helpers;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class Trail {


    private Vec3[] trailPoints;
    private Vec3[] speeds;

    public Trail(Vec3 initPos,Vec3 defSpeed, int count){
        this.reinit(initPos,defSpeed,count);
    }
    public Trail(Entity e, int count){
        this(e.position().add(0,e.getBbHeight()/2,0),e.getDeltaMovement(),count);
    }

    public void tick(Vec3 pos,Vec3 speed){
        for (int i = trailPoints.length-1; i > 0;i--){
            trailPoints[i] = trailPoints[i-1];
        }
        trailPoints[0] = pos;
        for (int i = speeds.length-1; i > 0;i--){
            speeds[i] = speeds[i-1];
        }
        speeds[0] = speed;
    }

    public void tick(Entity e){
        this.tick(e.position().add(0,e.getBbHeight()/2,0),e.getDeltaMovement());
    }

    public void reinit(Vec3 pos,Vec3 defSpeed,int count){
        this.trailPoints = new Vec3[count];
        for (int i = 0; i < count;i++){
            trailPoints[i] = new Vec3(pos.x,pos.y,pos.z);
        }
        this.speeds = new Vec3[count];
        for (int i = 0; i < count;i++){
            speeds[i] = new Vec3(defSpeed.x,defSpeed.y,defSpeed.z);
        }
    }

    public Vec3[] getTrailPoints(){
        return trailPoints;
    }
    public Vec3[] getSpeeds(){
        return speeds;
    }

    public Vec3[] getTrailPointsForRendering(){
        Vec3[] tr = new Vec3[trailPoints.length];
        tr[0] = Vec3.ZERO;
        for (int i = 1; i < trailPoints.length;i++){
            tr[i] = trailPoints[i].subtract(trailPoints[0]);
        }
        return tr;
    }
}
