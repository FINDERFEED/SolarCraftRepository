package com.finderfeed.solarcraft.local_library.helpers.mafs;

import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import net.minecraft.world.phys.Vec3;


/**
 * Based on - https://www.youtube.com/watch?v=jvPPXbo87ds
 */
public class CatmullRomSpline {


    //1/3 - conversion to tangent points, 1/2 - scale
    private static final float MULT = 1/3f * 1/2f;
    private SplinePoint[] points;

    public CatmullRomSpline(Vec3... points){
        this.points = new SplinePoint[points.length];
        this.initializePoints(points);
    }

    /**
     * @param progress A value between 0.0 to 1.0
     * @return a point on a spline
     */
    public Vec3 getSplinePoint(float progress){
        return this.getLocalSplinePoint(progress * (points.length - 1));
    }

    public Vec3 pointBetweenPoints(int pIndex,float progress){
        return this.getLocalSplinePoint(pIndex + progress);
    }

    /**
     * @param progress value from 0.0 to number of points - 1
     * @return point on a spline
     */
    public Vec3 getLocalSplinePoint(float progress){
        int point = (int) Math.floor(progress);
        float localProgress = progress - point;
        SplinePoint c = this.points[point];
        SplinePoint next = this.points[point + 1];
        Vec3 p1 = FDMathHelper.lerpv3(c.position,c.neighbor2,localProgress);
        Vec3 p2 = FDMathHelper.lerpv3(c.neighbor2,next.neighbor1,localProgress);
        Vec3 p3 = FDMathHelper.lerpv3(next.neighbor1,next.position,localProgress);

        Vec3 p4 = FDMathHelper.lerpv3(p1,p2,localProgress);
        Vec3 p5 = FDMathHelper.lerpv3(p2,p3,localProgress);

        Vec3 finale = FDMathHelper.lerpv3(p4,p5,localProgress);

        return finale;
    }



    private void initializePoints(Vec3... points){
        for (int i = 1; i < points.length - 1;i++){
            Vec3 prev = points[i - 1];
            Vec3 current = points[i];
            Vec3 next = points[i + 1];
            Vec3 speed = next.subtract(prev).multiply(MULT,MULT,MULT);
            SplinePoint point = new SplinePoint(current,current.add(speed.reverse()),current.add(speed));
            this.points[i] = point;
        }
        Vec3 init = points[0];
        Vec3 ainit = points[1];
        Vec3 end = points[points.length - 1];
        Vec3 bend = points[points.length - 2];

        Vec3 isp = ainit.subtract(init).multiply(2*MULT,2*MULT,2*MULT);
        Vec3 esp = bend.subtract(end).multiply(2*MULT,2*MULT,2*MULT);
        SplinePoint initPoint = new SplinePoint(init,init.add(isp.reverse()),init.add(isp));
        SplinePoint endPoint = new SplinePoint(end,end.add(esp),end.add(esp.reverse()));
        this.points[0] = initPoint;
        this.points[points.length - 1] = endPoint;
    }


    public SplinePoint[] getPoints() {
        return points;
    }

    public int getPointCount(){
        return points.length;
    }

    /**
     *
     * @param position - position of the main point
     * @param neighbor1 - position behind the point
     * @param neighbor2 - position after the point
     */
    private record SplinePoint(Vec3 position,Vec3 neighbor1,Vec3 neighbor2){

    }

}
