package com.finderfeed.solarforge.model_loaders;

import com.finderfeed.solarforge.baked_models.ProgressionOreModel;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class SolarforgeModelLoader implements IModelLoader<ProgressionOreGeometry> {

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public ProgressionOreGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
            BlockModel model = deserializationContext.deserialize(JSONUtils.getAsJsonObject(modelContents,"orig_model"), BlockModel.class);
        return new ProgressionOreGeometry(model);
    }
}

class ProgressionOreGeometry implements IModelGeometry<ProgressionOreGeometry>{

    public BlockModel model;

    public ProgressionOreGeometry(BlockModel model){
        this.model = model;
    }

    @Override
    public IBakedModel bake(IModelConfiguration owner,
                            ModelBakery bakery,
                            Function<RenderMaterial, TextureAtlasSprite> spriteGetter,
                            IModelTransform modelTransform,
                            ItemOverrideList overrides,
                            ResourceLocation modelLocation) {


        IBakedModel model = this.model.bake(bakery,this.model,spriteGetter,modelTransform,modelLocation,false);
        return new ProgressionOreModel(model);
    }

    @Override
    public Collection<RenderMaterial> getTextures(IModelConfiguration owner,
                                                  Function<ResourceLocation, IUnbakedModel> modelGetter,
                                                  Set<Pair<String, String>> missingTextureErrors) {
        List<RenderMaterial> list = new ArrayList<>();
        list.addAll(model.getMaterials(modelGetter,missingTextureErrors));

        return list;
    }
}
