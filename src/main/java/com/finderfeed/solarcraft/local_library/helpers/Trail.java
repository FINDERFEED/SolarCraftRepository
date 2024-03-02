package com.finderfeed.solarcraft.local_library.helpers;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class Trail {


    private Vec3[] trailPoints;
    private Vec3[] previousState;

    public Trail(Vec3 initPos,Vec3 defSpeed, int count){
        this.reinit(initPos,defSpeed,count);
    }
    public Trail(Entity e,float speedMult, int count){
        this(e.position().add(0,e.getBbHeight()/2,0),e.getDeltaMovement().multiply(speedMult,speedMult,speedMult),count);
    }

    public void tick(Vec3 pos){
        for (int i = 0; i < trailPoints.length;i++){
            this.previousState[i] = trailPoints[i];
        }
        for (int i = trailPoints.length-1; i > 0;i--){
            trailPoints[i] = trailPoints[i-1];
        }
        trailPoints[0] = pos;
    }

    public void tick(Entity e){
        this.tick(e.position().add(0,e.getBbHeight()/2,0));
    }

    public void reinit(Vec3 pos,Vec3 defSpeed,int count){
        this.previousState = new Vec3[count];
        this.trailPoints = new Vec3[count];
        Vec3 toAdd = defSpeed.reverse();
        for (int i = 0; i < count;i++){
            Vec3 p = new Vec3(pos.x,pos.y,pos.z);
            p = p.add(toAdd.multiply(i,i,i));
            trailPoints[i] = p;
            previousState[i] = p;
        }
    }

    public Vec3[] getTrailPoints(){
        return trailPoints;
    }

    public Vec3[] getTrailPointsForRendering(float pticks){
        Vec3[] tr = new Vec3[trailPoints.length];
        tr[0] = Vec3.ZERO;
        Vec3 initPoint = trailPoints[0];
        Vec3 pinitPoint = trailPoints[0];
        for (int i = 1; i < trailPoints.length;i++){
            Vec3 point = trailPoints[i].subtract(initPoint);
            Vec3 ppoint = previousState[i].subtract(pinitPoint);
            Vec3 b = ppoint.subtract(point);
            point = point.add(b.multiply(pticks,pticks,pticks));

            tr[i] = point;
        }
        return tr;
    }
}
