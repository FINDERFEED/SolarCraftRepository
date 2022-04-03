package com.finderfeed.solarforge.client.rendering;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;

public class CoreShaders {

    public static ShaderInstance AOE_GUN_PROJECTILE_SHADER = null;
    public static RenderStateShard.ShaderStateShard AOE_GUN_PROJECTILE_STATE_SHARD = new RenderStateShard.ShaderStateShard(() -> AOE_GUN_PROJECTILE_SHADER);


    public static ShaderInstance RUNIC_ENERGY_FLOW_SHADER = null;
    public static RenderStateShard.ShaderStateShard RUNIC_ENERGY_FLOW_STATE_SHARD = new RenderStateShard.ShaderStateShard(() -> RUNIC_ENERGY_FLOW_SHADER);
}
