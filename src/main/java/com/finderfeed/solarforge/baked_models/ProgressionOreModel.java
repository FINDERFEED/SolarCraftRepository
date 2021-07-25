package com.finderfeed.solarforge.baked_models;



import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.misc_things.IProgressionBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;


public class ProgressionOreModel implements BakedModel {

    public BakedModel model;

    public ProgressionOreModel(BakedModel model){
        this.model = model;
    }

    @Override
    public BakedModel getBakedModel() {
        return model;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {

        if (state != null && (Minecraft.getInstance().player != null) ) {
            if ((state.getBlock() instanceof IProgressionBlock) && !Helpers.hasPlayerUnlocked(((IProgressionBlock) state.getBlock()).getRequiredProgression(), Minecraft.getInstance().player)) {
                BlockState lockedState = ((IProgressionBlock) state.getBlock()).getLockedBlock().defaultBlockState();

                return Minecraft.getInstance().getBlockRenderer().getBlockModel(lockedState)
                        .getQuads(lockedState,side,rand,extraData);
            }
        }
        return model.getQuads(state,side,rand,extraData);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {

        return getQuads(state,side,rand,EmptyModelData.INSTANCE);
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
        return getParticleTexture(EmptyModelData.INSTANCE);
    }


    @Override
    public TextureAtlasSprite getParticleTexture(@Nonnull IModelData data) {

        return model.getParticleTexture(data);
    }

    @Override
    public ItemOverrides getOverrides() {
        return model.getOverrides();
    }
}
//    @Override
//    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData)
//    {
//        if (state != null && (Minecraft.getInstance().player != null) ) {
//            if ((state.getBlock() instanceof IProgressionBlock) && !Helpers.hasPlayerUnlocked(Achievements.CRAFT_SOLAR_LENS, Minecraft.getInstance().player)) {
//                BlockState lockedState = ((IProgressionBlock) state.getBlock()).getLockedBlock().defaultBlockState();
//
//                return Minecraft.getInstance().getBlockRenderer().getBlockModel(lockedState)
//                        .getQuads(lockedState,side,rand,extraData);
//            }
//        }
//
//        return IForgeBakedModel.super.getQuads(state,side,rand,extraData);
//    }