package com.finderfeed.solarcraft.local_library.client.delayed_renderer;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DelayedBufferSource extends MultiBufferSource.BufferSource {
    public DelayedBufferSource() {
        super(new BufferBuilder(1024), new HashMap<>());
    }

    @Override
    public VertexConsumer getBuffer(RenderType renderType) {
        if (!fixedBuffers.containsKey(renderType)){
            fixedBuffers.put(renderType,new BufferBuilder(1024));
        }
        return super.getBuffer(renderType);
    }

    @Override
    public void endBatch() {
        super.endBatch();
    }
}
