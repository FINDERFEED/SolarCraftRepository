package com.finderfeed.solarforge.client.rendering;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;

public class Shaders {

    public static ShaderInstance AOE_GUN_PROJECTILE_SHADER = null;
    public static RenderStateShard.ShaderStateShard AOE_GUN_PROJECTILE_STATE_SHARD = new RenderStateShard.ShaderStateShard(() -> AOE_GUN_PROJECTILE_SHADER);
}
