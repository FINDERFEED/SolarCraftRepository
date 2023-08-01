package com.finderfeed.solarcraft.registries.animations;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.Animation;
import com.finderfeed.solarcraft.local_library.other.delayed_getter.DelayedObjectGetter;
import com.finderfeed.solarcraft.local_library.other.delayed_getter.DelayedObjectGetterManager;
import net.minecraft.resources.ResourceLocation;

public class SCAnimations {

    public static final DelayedObjectGetterManager<Animation> ANIMATIONS = new DelayedObjectGetterManager<>();

    public static final DelayedObjectGetter<Animation> ULDERA_CRYSTAL_IDLE = ANIMATIONS.define(new ResourceLocation(SolarCraft.MOD_ID,"uldera_crystal_idle"));
    public static final DelayedObjectGetter<Animation> ULDERA_CRYSTAL_ATTACK_1 = ANIMATIONS.define(new ResourceLocation(SolarCraft.MOD_ID,"uldera_crystal_attack_1"));
    public static final DelayedObjectGetter<Animation> ULDERA_CRYSTAL_ATTACK_1_POSE = ANIMATIONS.define(new ResourceLocation(SolarCraft.MOD_ID,"uldera_crystal_attack_1_pose"));
    public static final DelayedObjectGetter<Animation> ULDERA_CRYSTAL_INFLATE_POSE = ANIMATIONS.define(new ResourceLocation(SolarCraft.MOD_ID,"uldera_crystal_charge_up_pose"));
    public static final DelayedObjectGetter<Animation> ULDERA_CRYSTAL_SHAKE= ANIMATIONS.define(new ResourceLocation(SolarCraft.MOD_ID,"uldera_crystal_shake"));
}
