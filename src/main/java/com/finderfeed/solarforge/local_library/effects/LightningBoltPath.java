package com.finderfeed.solarforge.local_library.effects;

import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Iterator;

public class LightningBoltPath implements Iterable<Vec3>{

    private Vec3 start;
    private Vec3 end;
    private int breakCount;
    private double maxOffset = 1;
    private Vec3[] positions;

    /**
     *
     * @param initPos  init pos for lightning
     * @param endPos    end pos for lightning
     * @param dots total dots (including start and end)
     */
    private LightningBoltPath(Vec3 initPos,Vec3 endPos,int dots){
        this.start = initPos;
        this.end = endPos;
        this.breakCount = dots;
        positions = new Vec3[dots];
        positions[0] = initPos;
        positions[dots-1] = endPos;
        calculate();
    }

    public void setMaxOffset(double maxOffset) {
        this.maxOffset = maxOffset;
        this.calculate();
    }

    public void calculate(){
        Vec3 vectorBetween = end.subtract(start);
        for (int i = 1;i < breakCount-1;i++){
            double xOff = FDMathHelper.randomPlusMinus() * (FDMathHelper.RANDOM.nextDouble() * maxOffset);
            double yOff = FDMathHelper.randomPlusMinus() * (FDMathHelper.RANDOM.nextDouble() * maxOffset);
            double zOff = FDMathHelper.randomPlusMinus() * (FDMathHelper.RANDOM.nextDouble() * maxOffset);
            double m = (float)i / breakCount;
            Vec3 posOnLine = start.add(vectorBetween.multiply(m,m,m));
            positions[i] = posOnLine.add(xOff,yOff,zOff);
        }
    }

    public Vec3 getPos(int i){
        return positions[i];
    }



    public static LightningBoltPath create(Vec3 initPos,Vec3 endPos,int dots){
        if (dots < 2){
            throw new IllegalArgumentException("Dots count must be larger than 2!");
        }
        return new LightningBoltPath(initPos,endPos,dots);
    }

    @Nonnull
    @Override
    public Iterator<Vec3> iterator() {
        return Arrays.stream(positions).iterator();
    }
}
