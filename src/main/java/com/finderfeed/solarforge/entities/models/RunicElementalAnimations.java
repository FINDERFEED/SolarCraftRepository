package com.finderfeed.solarforge.entities.models;

import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.local_library.other.EaseInOut;
import com.finderfeed.solarforge.local_library.other.InterpolatedValue;
import net.minecraft.client.Minecraft;

public class RunicElementalAnimations {

    public static final RunicElementalModelAnimation IDLE = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)->{
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

    public static final RunicElementalModelAnimation IDLE_LEGS_ONLY = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)->{
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

    public static final RunicElementalModelAnimation SWING_HANDS_UP = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)->{
        InterpolatedValue value = boss.getOrCreateAnimationValue("swingHandsUp",new EaseInOut(0,1,15,2));
        double val = value.getValue();
        model.righthand.xRot = model.mrighthand.getInitRotX() - (float)Math.toRadians(val*180);
        model.lefthand.xRot = model.mlefthand.getInitRotX() - (float)Math.toRadians(val*180);
        model.lefthand.zRot = model.mlefthand.getInitRotZ() - (float)Math.toRadians(val*10);
        model.righthand.zRot = model.mrighthand.getInitRotZ() + (float)Math.toRadians(val*10);
    };

    public static final RunicElementalModelAnimation CAST_ELEMENT = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)->{
        float time = RenderingTools.getTime(boss.level, Minecraft.getInstance().getDeltaFrameTime());
        double v = 10*Math.cos(time/5);
        model.righthand.xRot = model.mrighthand.getInitRotX() - (float)Math.toRadians(180);
        model.lefthand.xRot = model.mlefthand.getInitRotX() - (float)Math.toRadians(180);

        model.lefthand.zRot = model.mlefthand.getInitRotZ() - (float)Math.toRadians(10 + v);
        model.righthand.zRot = model.mrighthand.getInitRotZ() + (float)Math.toRadians(10 + v);
        model.lefthand.yRot = model.mlefthand.getInitRotY() - (float)Math.toRadians(v*3);
        model.righthand.yRot = model.mrighthand.getInitRotY() + (float)Math.toRadians(v*3);
    };

    public static final RunicElementalModelAnimation SWING_HANDS_DOWN = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)->{
        InterpolatedValue value = boss.getOrCreateAnimationValue("swingHandsDown",new EaseInOut(0,1,15,2));
        double val = value.getValue();
        model.righthand.xRot = model.mrighthand.getInitRotX() -  (float)Math.toRadians(180 - val*180);
        model.lefthand.xRot = model.mlefthand.getInitRotX()-  (float)Math.toRadians(180 - val*180);
        model.righthand.zRot = model.mrighthand.getInitRotZ() + (float)Math.toRadians(10-val*10);
        model.lefthand.zRot = model.mlefthand.getInitRotZ() - (float)Math.toRadians(10-val*10);
    };

    public static final RunicElementalModelAnimation DIRECT_ATTACK = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)->{
        model.righthand.z = model.mrighthand.getInitZ() - 2f;
        model.lefthand.z = model.mlefthand.getInitZ() - 2f;
        model.righthand.yRot =   (float)Math.toRadians(-20 + netHeadYaw);
        model.lefthand.yRot =  (float)Math.toRadians(20 + netHeadYaw);
        model.righthand.xRot = (float)Math.toRadians(headPitch-90);
        model.lefthand.xRot = (float)Math.toRadians(headPitch-90);
    };

    public static final RunicElementalModelAnimation RESET_EVERYTHING = (boss,model,limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)->{
        model.mrighthand.reset();
        model.mlefthand.reset();
        model.mlegsrow1.reset();
        model.mlegsrow2.reset();
        model.mlegsrow3.reset();
        model.mbody.reset();
        model.mhead.reset();
    };
}
