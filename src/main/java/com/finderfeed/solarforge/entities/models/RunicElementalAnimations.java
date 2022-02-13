package com.finderfeed.solarforge.entities.models;

import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.local_library.other.EaseInOut;
import com.finderfeed.solarforge.local_library.other.InterpolatedValue;
import net.minecraft.client.Minecraft;

public class RunicElementalAnimations {

    public static final RunicElementalModelAnimation IDLE = (boss,model)->{
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

    public static final RunicElementalModelAnimation SWING_HANDS_UP = (boss,model)->{
        InterpolatedValue value = boss.getOrCreateAnimationValue("swingHandsUp",new EaseInOut(0,1,25,2));
        double val = value.getValue();
        model.righthand.xRot = model.mrighthand.getInitRotX() - (float)Math.toRadians(val*180);
        model.lefthand.xRot = model.mlefthand.getInitRotX() - (float)Math.toRadians(val*180);
        model.lefthand.zRot = model.mlefthand.getInitRotZ() - (float)Math.toRadians(val*10);
        model.righthand.zRot = model.mrighthand.getInitRotZ() + (float)Math.toRadians(val*10);
    };

}
