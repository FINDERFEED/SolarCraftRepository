package com.finderfeed.solarcraft.client.baked_models;


import com.finderfeed.solarcraft.content.blocks.primitive.ProgressionBlock;
import com.finderfeed.solarcraft.helpers.Helpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nonnull;
import java.util.List;


public class ProgressionOreModel implements BakedModel {

    public BakedModel model;
    public ProgressionOreModel(BakedModel model){
        this.model = model;
    }
    @Override
    public List<BakedQuad> getQuads(@org.jetbrains.annotations.Nullable BlockState state, @org.jetbrains.annotations.Nullable Direction direction, RandomSource src) {
        if (state != null && (Minecraft.getInstance().player != null) ) {
            if ((state.getBlock() instanceof ProgressionBlock block) && !Helpers.hasPlayerCompletedProgression((block).getRequiredProgression(), Minecraft.getInstance().player)) {
                BlockState lockedState = block.getLockedBlock().defaultBlockState();

                return Minecraft.getInstance().getBlockRenderer().getBlockModel(lockedState)
                        .getQuads(lockedState,direction,src, ModelData.EMPTY,null);
            }
        }
        List<BakedQuad> quads = model.getQuads(state,direction,src, ModelData.EMPTY,null);
        return quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return true;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return getParticleIcon(ModelData.EMPTY);
    }


    @Override
    public TextureAtlasSprite getParticleIcon(@Nonnull ModelData data) {
        return model.getParticleIcon(data);
    }

    @Override
    public ItemOverrides getOverrides() {
        return model.getOverrides();
    }
}
