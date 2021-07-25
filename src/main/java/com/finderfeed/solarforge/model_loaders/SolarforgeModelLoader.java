package com.finderfeed.solarforge.model_loaders;

import com.finderfeed.solarforge.baked_models.ProgressionOreModel;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;

public class SolarforgeModelLoader implements IModelLoader<ProgressionOreGeometry> {

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {

    }

    @Override
    public ProgressionOreGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
            BlockModel model = deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents,"orig_model"), BlockModel.class);
        return new ProgressionOreGeometry(model);
    }
}

class ProgressionOreGeometry implements IModelGeometry<ProgressionOreGeometry>{

    public BlockModel model;

    public ProgressionOreGeometry(BlockModel model){
        this.model = model;
    }

    @Override
    public BakedModel bake(IModelConfiguration owner,
                            ModelBakery bakery,
                            Function<Material, TextureAtlasSprite> spriteGetter,
                            ModelState modelTransform,
                            ItemOverrides overrides,
                            ResourceLocation modelLocation) {


        BakedModel model = this.model.bake(bakery,this.model,spriteGetter,modelTransform,modelLocation,false);
        return new ProgressionOreModel(model);
    }

    @Override
    public Collection<Material> getTextures(IModelConfiguration owner,
                                                  Function<ResourceLocation, UnbakedModel> modelGetter,
                                                  Set<Pair<String, String>> missingTextureErrors) {
        List<Material> list = new ArrayList<>();
        list.addAll(model.getMaterials(modelGetter,missingTextureErrors));

        return list;
    }
}
