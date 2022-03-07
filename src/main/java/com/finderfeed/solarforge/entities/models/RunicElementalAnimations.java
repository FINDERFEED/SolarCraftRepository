package com.finderfeed.solarforge.entities.models;

import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.local_library.other.EaseInOut;
import com.finderfeed.solarforge.local_library.other.InterpolatedValue;
import net.minecraft.client.Minecraft;

public class RunicElementalAnimations {


    public static final RunicElementalModelAnimation IDLE = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        float time = RenderingTools.getTime(boss.level, Minecraft.getInstance().getDeltaFrameTime());
        model.body.y = model.mbody.getInitY() + 0.3f*(float)Math.sin(time/6);
        model.head.y = model.mhead.getInitY() + 0.4f*(float)Math.sin(time/6);

        model.legsrow1.yRot = model.mlegsrow1.getInitRotY() + (float)Math.toRadians(time)*2;
        model.legsrow12.yRot = model.mlegsrow12.getInitRotY() + (float)Math.toRadians(time)*2;
        model.legsrow12.y = model.mlegsrow12.getInitY() +0.25f*(float)Math.sin(time/6);
        model.legsrow1.y = model.mlegsrow1.getInitY() -0.25f*(float)Math.sin(time/6);

        model.legsrow2.yRot = model.mlegsrow2.getInitRotY() + (float)Math.toRadians(time)*2;
        model.legsrow2.y = model.mlegsrow2.getInitY() + 0.2f*(float)Math.sin(time/6);

        model.legsrow3.yRot = model.mlegsrow3.getInitRotY() + (float)Math.toRadians(time)*2;
        model.legsrow3.y = model.mlegsrow3.getInitY() - 0.2f*(float)Math.sin(time/6);

        model.righthand.y = model.mrighthand.getInitY() + 0.15f*(float)Math.sin(time/6);
        model.lefthand.y = model.mlefthand.getInitY() + 0.15f*(float)Math.sin(time/6);
    };

    public static final RunicElementalModelAnimation IDLE_LEGS_ONLY = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        float time = RenderingTools.getTime(boss.level, Minecraft.getInstance().getDeltaFrameTime());
        model.legsrow1.yRot = model.mlegsrow1.getInitRotY() + (float)Math.toRadians(time)*2;
        model.legsrow12.yRot = model.mlegsrow12.getInitRotY() + (float)Math.toRadians(time)*2;
        model.legsrow12.y = model.mlegsrow12.getInitY() +0.25f*(float)Math.sin(time/6);
        model.legsrow1.y = model.mlegsrow1.getInitY() -0.25f*(float)Math.sin(time/6);

        model.legsrow2.yRot = model.mlegsrow2.getInitRotY() + (float)Math.toRadians(time)*2;
        model.legsrow2.y = model.mlegsrow2.getInitY() + 0.2f*(float)Math.sin(time/6);

        model.legsrow3.yRot = model.mlegsrow3.getInitRotY() + (float)Math.toRadians(time)*2;
        model.legsrow3.y = model.mlegsrow3.getInitY() - 0.2f*(float)Math.sin(time/6);

    };

    public static final RunicElementalModelAnimation SWING_HANDS_UP = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        InterpolatedValue value = boss.getOrCreateAnimationValue("swingHandsUp",new EaseInOut(0,1,15,2));
        double val = value.getValue();
        model.righthand.xRot = model.mrighthand.getInitRotX() - (float)Math.toRadians(val*180);
        model.lefthand.xRot = model.mlefthand.getInitRotX() - (float)Math.toRadians(val*180);
        model.lefthand.zRot = model.mlefthand.getInitRotZ() - (float)Math.toRadians(val*10);
        model.righthand.zRot = model.mrighthand.getInitRotZ() + (float)Math.toRadians(val*10);
    };

    public static final RunicElementalModelAnimation SWING_HANDS_UP_FASTER = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        InterpolatedValue value = boss.getOrCreateAnimationValue("swingHandsUp",new EaseInOut(0,1,8,2));
        double val = value.getValue();
        model.righthand.xRot = model.mrighthand.getInitRotX() - (float)Math.toRadians(val*180);
        model.lefthand.xRot = model.mlefthand.getInitRotX() - (float)Math.toRadians(val*180);
        model.lefthand.zRot = model.mlefthand.getInitRotZ() - (float)Math.toRadians(val*10);
        model.righthand.zRot = model.mrighthand.getInitRotZ() + (float)Math.toRadians(val*10);
    };

    public static final RunicElementalModelAnimation CAST_ELEMENT = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        float time = RenderingTools.getTime(boss.level, Minecraft.getInstance().getDeltaFrameTime());
        double v = 10*Math.cos(time/5);
        model.righthand.xRot = model.mrighthand.getInitRotX() - (float)Math.toRadians(180);
        model.lefthand.xRot = model.mlefthand.getInitRotX() - (float)Math.toRadians(180);

        model.lefthand.zRot = model.mlefthand.getInitRotZ() - (float)Math.toRadians(10 + v);
        model.righthand.zRot = model.mrighthand.getInitRotZ() + (float)Math.toRadians(10 + v);
        model.lefthand.yRot = model.mlefthand.getInitRotY() - (float)Math.toRadians(v*3);
        model.righthand.yRot = model.mrighthand.getInitRotY() + (float)Math.toRadians(v*3);
    };

    public static final RunicElementalModelAnimation SWING_HANDS_DOWN = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        InterpolatedValue value = boss.getOrCreateAnimationValue("swingHandsDown",new EaseInOut(0,1,15,2));
        double val = value.getValue();
        model.righthand.xRot = model.mrighthand.getInitRotX() -  (float)Math.toRadians(180 - val*180);
        model.lefthand.xRot = model.mlefthand.getInitRotX()-  (float)Math.toRadians(180 - val*180);
        model.righthand.zRot = model.mrighthand.getInitRotZ() + (float)Math.toRadians(10-val*10);
        model.lefthand.zRot = model.mlefthand.getInitRotZ() - (float)Math.toRadians(10-val*10);
    };

    public static final RunicElementalModelAnimation DIRECT_ATTACK = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        model.righthand.z = model.mrighthand.getInitZ() - 2f;
        model.lefthand.z = model.mlefthand.getInitZ() - 2f;
        model.righthand.yRot =   (float)Math.toRadians(-20 + netHeadYaw);
        model.lefthand.yRot =  (float)Math.toRadians(20 + netHeadYaw);
        model.righthand.xRot = (float)Math.toRadians(headPitch-90);
        model.lefthand.xRot = (float)Math.toRadians(headPitch-90);
    };

    public static final RunicElementalModelAnimation PREPARE_DIRECT_ATTACK = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        InterpolatedValue val = boss.getOrCreateAnimationValue("prepareDirectAttack",new EaseInOut(0,1,15,2));
        double v = val.getValue();
        model.righthand.z = model.mrighthand.getInitZ() - 2f*(float)v;
        model.lefthand.z = model.mlefthand.getInitZ() - 2f*(float)v;
        model.righthand.xRot = model.mrighthand.getInitRotX() + (float)((Math.toRadians(headPitch - 90) - model.mrighthand.getInitRotX())*v) ;
        model.lefthand.xRot = model.mlefthand.getInitRotX() + (float)((Math.toRadians(headPitch - 90) - model.mlefthand.getInitRotX())*v) ;
        model.righthand.yRot = model.mrighthand.getInitRotY() - (float)Math.toRadians(20*v);
        model.lefthand.yRot = model.mlefthand.getInitRotY() + (float)Math.toRadians(20*v);
    };

    public static final RunicElementalModelAnimation PUT_DOWN_DIRECT_ATTACK = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        InterpolatedValue val = boss.getOrCreateAnimationValue("putDownDirectAttack",new EaseInOut(0,1,16,2));
        double v = 1-val.getValue();
        model.righthand.z = model.mrighthand.getInitZ() - 2f*(float)v;
        model.lefthand.z = model.mlefthand.getInitZ() - 2f*(float)v;
        model.righthand.xRot = model.mrighthand.getInitRotX() + (float)((Math.toRadians(headPitch - 90) - model.mrighthand.getInitRotX())*v) ;
        model.lefthand.xRot = model.mlefthand.getInitRotX() + (float)((Math.toRadians(headPitch - 90) - model.mlefthand.getInitRotX())*v) ;
        model.righthand.yRot = model.mrighthand.getInitRotY() - (float)Math.toRadians(20*v);
        model.lefthand.yRot = model.mlefthand.getInitRotY() + (float)Math.toRadians(20*v);
    };

    public static final RunicElementalModelAnimation RESET_EVERYTHING = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        model.mrighthand.reset();
        model.mlefthand.reset();
        model.mlegsrow1.reset();
        model.mlegsrow2.reset();
        model.mlegsrow3.reset();
        model.mbody.reset();
        model.mhead.reset();
    };
    public static final RunicElementalModelAnimation FLY_UP = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        InterpolatedValue val = boss.getOrCreateAnimationValue("flyUp",new EaseInOut(0,1,40,4));
        double value = val.getValue();
        model.head.yRot = (float)Math.toRadians(netHeadYaw);
        model.head.xRot = (float)Math.toRadians(headPitch);
        model.body.yRot = model.mbody.getInitRotY() + (float)Math.toRadians(720*value);
        model.legsrow12.yRot = model.mlegsrow12.getInitRotY() + (float)Math.toRadians(720*value);
        model.legsrow2.yRot = model.mlegsrow2.getInitRotY() + (float)Math.toRadians(720*value);
        model.legsrow3.yRot = model.mlegsrow3.getInitRotY() + (float)Math.toRadians(720*value);
        if (value <= 0.5) {
            model.lefthand.xRot = model.mlefthand.getInitRotX() + (float) Math.toRadians(75 * FinderfeedMathHelper.clamp(0, value * 2, 1));
            model.righthand.xRot = model.mrighthand.getInitRotX() - (float) Math.toRadians(75 * FinderfeedMathHelper.clamp(0, value * 2, 1));
        }else{
            model.lefthand.xRot = model.mlefthand.getInitRotX() + (float) Math.toRadians(75 * (1-FinderfeedMathHelper.clamp(0, value * 2 - 1, 1)));
            model.righthand.xRot = model.mrighthand.getInitRotX() - (float) Math.toRadians(75 * (1-FinderfeedMathHelper.clamp(0, value * 2 - 1, 1)));
        }
        model.righthand.yRot = model.mrighthand.getInitRotY() + (float)Math.toRadians(720*value);
        model.lefthand.yRot = model.mlefthand.getInitRotY() + (float)Math.toRadians(720*value);
        double[] xy = FinderfeedMathHelper.rotatePointDegrees(9,0,720*value);
        model.righthand.x = + model.mrighthand.getInitX() + 9 - (float)xy[0];
        model.righthand.z = + model.mrighthand.getInitZ() + (float)xy[1];

        model.lefthand.x = + model.mlefthand.getInitX() - 9 + (float)xy[0];
        model.lefthand.z = + model.mlefthand.getInitZ() - (float)xy[1];
    };

    public static final RunicElementalModelAnimation SWING_HANDS = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        float time = RenderingTools.getTime(boss.level, Minecraft.getInstance().getDeltaFrameTime());
        double v = Math.toRadians(25 + 20 * Math.sin(time/5));
        double f = Math.toRadians(20 * Math.sin((time + Math.PI)/5));
        model.righthand.zRot = model.mrighthand.getInitRotZ() + (float) v;
        model.lefthand.zRot = model.mlefthand.getInitRotZ() - (float) v;
        model.righthand.xRot = model.mrighthand.getInitRotX() + (float) f;
        model.lefthand.xRot = model.mlefthand.getInitRotX() + (float) f;
    };

    public static final RunicElementalModelAnimation CAST = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        float time = RenderingTools.getTime(boss.level, Minecraft.getInstance().getDeltaFrameTime());
        double v = 10*Math.cos(time/2.5);
        model.righthand.xRot = model.mrighthand.getInitRotX() - (float)Math.toRadians(180);
        model.lefthand.xRot = model.mlefthand.getInitRotX() - (float)Math.toRadians(180);

        model.lefthand.zRot = model.mlefthand.getInitRotZ() - (float)Math.toRadians(5-v);
        model.righthand.zRot = model.mrighthand.getInitRotZ() + (float)Math.toRadians(5-v);
    };

    public static final RunicElementalModelAnimation PREPARE_SWING_HAMMER = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        float value = (float)boss.getOrCreateAnimationValue("prepare_swing_hammer",new EaseInOut(0,1,20,3)).getValue();
        float angle = value*-80;
        model.head.xRot = model.mhead.getInitRotX() + (float)Math.toRadians(20*value);
        model.body.xRot = model.mbody.getInitRotX() + (float)Math.toRadians(10*value);

        model.body.yRot = model.mbody.getInitRotY() - (float)Math.toRadians(-60*value);

        model.head.z = model.mhead.getInitZ() - 2f*value;

        double[] xz = FinderfeedMathHelper.rotatePointDegrees(9,0,angle);
        double[] xzf = FinderfeedMathHelper.rotatePointDegrees(0,-5*value,angle);
        model.righthand.z = model.mrighthand.getInitZ() - (float)xz[1] - (float)xzf[1];
        model.righthand.x = model.mrighthand.getInitX() + 9 -(float)xz[0] - (float)xzf[0];

        double[] xz2 = FinderfeedMathHelper.rotatePointDegrees(-9,0,angle);
        double[] xzf2 = FinderfeedMathHelper.rotatePointDegrees(0,5*value,angle);
        model.lefthand.z = model.mlefthand.getInitZ() - (float)xz2[1] - (float)xzf2[1];
        model.lefthand.x = model.mlefthand.getInitX() - 9 - (float)xz2[0] - (float)xzf2[0];

        model.righthand.xRot = model.mrighthand.getInitRotX() - (float)Math.toRadians(80*value);
        model.righthand.yRot = model.mrighthand.getInitRotY() - (float)Math.toRadians((-80 + 30)*value);

        model.lefthand.xRot = model.mlefthand.getInitRotX() - (float)Math.toRadians(80*value);
        model.lefthand.yRot = model.mlefthand.getInitRotY() - (float)Math.toRadians((-80 - 30)*value);

    };

    public static final RunicElementalModelAnimation END_SWING_HAMMER = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        float value = 1-(float)boss.getOrCreateAnimationValue("end_swing_hammer",new EaseInOut(0,1,20,3)).getValue();

        model.head.xRot = model.mhead.getInitRotX() + (float)Math.toRadians(20*value);
        model.body.xRot = model.mbody.getInitRotX() + (float)Math.toRadians(10*value);
        float angle = value*80;
        model.head.z = model.mhead.getInitZ() - 2f*value;

        double[] xz = FinderfeedMathHelper.rotatePointDegrees(9,0,angle);
        double[] xzf = FinderfeedMathHelper.rotatePointDegrees(0,5*value,angle);
        model.righthand.z = model.mrighthand.getInitZ() - (float)xz[1] - (float)xzf[1];
        model.righthand.x = model.mrighthand.getInitX() + 9 -(float)xz[0] - (float)xzf[0];

        double[] xz2 = FinderfeedMathHelper.rotatePointDegrees(-9,0,angle);
        double[] xzf2 = FinderfeedMathHelper.rotatePointDegrees(0,-5*value,angle);
        model.lefthand.z = model.mlefthand.getInitZ() - (float)xz2[1] - (float)xzf2[1];
        model.lefthand.x = model.mlefthand.getInitX() - 9 - (float)xz2[0] - (float)xzf2[0];

        model.righthand.xRot = model.mrighthand.getInitRotX() - (float)Math.toRadians(angle);
        model.righthand.yRot = model.mrighthand.getInitRotY() - (float)Math.toRadians(angle + 30*value);

        model.lefthand.xRot = model.mlefthand.getInitRotX() - (float)Math.toRadians(angle);
        model.lefthand.yRot = model.mlefthand.getInitRotY() - (float)Math.toRadians(angle - 30*value);

        model.body.yRot = model.mbody.getInitRotY() - (float)Math.toRadians(value*60);


    };

    public static final RunicElementalModelAnimation SWING_HAMMER = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,ticker)->{
        float value = (float)boss.getOrCreateAnimationValue("swing_hammer",new EaseInOut(0,2,10,3)).getValue()-1;

        model.head.xRot = model.mhead.getInitRotX() + (float)Math.toRadians(20);
        model.body.xRot = model.mbody.getInitRotX() + (float)Math.toRadians(10);
        float angle = value*80;
        model.head.z = model.mhead.getInitZ() - 2f;

        double[] xz = FinderfeedMathHelper.rotatePointDegrees(9,0,angle);
        double[] xzf = FinderfeedMathHelper.rotatePointDegrees(0,5*value,angle);
        model.righthand.z = model.mrighthand.getInitZ() - (float)xz[1] - (float)xzf[1];
        model.righthand.x = model.mrighthand.getInitX() + 9 -(float)xz[0] - (float)xzf[0];

        double[] xz2 = FinderfeedMathHelper.rotatePointDegrees(-9,0,angle);
        double[] xzf2 = FinderfeedMathHelper.rotatePointDegrees(0,-5*value,angle);
        model.lefthand.z = model.mlefthand.getInitZ() - (float)xz2[1] - (float)xzf2[1];
        model.lefthand.x = model.mlefthand.getInitX() - 9 - (float)xz2[0] - (float)xzf2[0];

        model.righthand.xRot = model.mrighthand.getInitRotX() - (float)Math.toRadians(80);
        model.righthand.yRot = model.mrighthand.getInitRotY() - (float)Math.toRadians(angle + 30);

        model.lefthand.xRot = model.mlefthand.getInitRotX() - (float)Math.toRadians(80);
        model.lefthand.yRot = model.mlefthand.getInitRotY() - (float)Math.toRadians(angle - 30);

        model.body.yRot = model.mbody.getInitRotY() - (float)Math.toRadians(value*60);


    };


    //ready to use animations
    public static final RunicElementalModelAnimation REFRACTION_CRYSTALS = RunicElementalAnimations.FLY_UP::animate;

    public static final RunicElementalModelAnimation MAGIC_MISSILES = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick)->{
        if (tick <= 15){
            RunicElementalAnimations.PREPARE_DIRECT_ATTACK.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }else if (tick <= 205){
            RunicElementalAnimations.DIRECT_ATTACK.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }else{
            RunicElementalAnimations.PUT_DOWN_DIRECT_ATTACK.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }
    };
    public static final RunicElementalModelAnimation SUNSTRIKES = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick)->{
        if (tick <= 15){
            RunicElementalAnimations.SWING_HANDS_UP.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }else if (tick > 115){
            RunicElementalAnimations.SWING_HANDS_DOWN.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }else{
            RunicElementalAnimations.CAST_ELEMENT.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }
    };

    public static final RunicElementalModelAnimation EARTHQUAKES = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick)->{
        if (tick <= 15){
            RunicElementalAnimations.SWING_HANDS_UP.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }else if (tick > 105){
            RunicElementalAnimations.SWING_HANDS_DOWN.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }else{
            RunicElementalAnimations.CAST.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }
    };
    public static final RunicElementalModelAnimation VARTH_DADER = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick)-> {
        if (tick <= 15){
            RunicElementalAnimations.PREPARE_DIRECT_ATTACK.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }else if (tick >= 106){
            RunicElementalAnimations.PUT_DOWN_DIRECT_ATTACK.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }else{
            RunicElementalAnimations.DIRECT_ATTACK.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }
    };

    public static final RunicElementalModelAnimation THROW_SUMMONING_ROCKETS = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick)-> {
        if (tick <= 8){
            RunicElementalAnimations.SWING_HANDS_UP_FASTER.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }else{
            RunicElementalAnimations.SWING_HANDS_DOWN.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }
    };

    public static final RunicElementalModelAnimation HAMMER_ATTACK = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick)-> {
        int realTick = tick % 52;
        if (realTick > 2 && realTick < 21){
            RunicElementalAnimations.PREPARE_SWING_HAMMER.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }else if (realTick > 2 && realTick < 31){
            RunicElementalAnimations.SWING_HAMMER.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }else if (realTick > 2 && realTick < 51){
            RunicElementalAnimations.END_SWING_HAMMER.animate(boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,tick);
        }
    };

    public static final RunicElementalModelAnimation[] ANIMATIONS_IN_ORDER = {MAGIC_MISSILES, REFRACTION_CRYSTALS,SUNSTRIKES,EARTHQUAKES,VARTH_DADER,THROW_SUMMONING_ROCKETS,HAMMER_ATTACK};


}
