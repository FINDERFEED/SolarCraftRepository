package com.finderfeed.solarcraft.client.model_loaders;

import com.finderfeed.solarcraft.client.baked_models.ProgressionOreModel;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;

public class SolarCraftModelLoader implements IGeometryLoader<ProgressionOreGeometry> {



//    @Override
//    public ProgressionOreGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
//            BlockModel model = deserializationContext.deserialize(GsonHelper.getAsJsonObject(modelContents,"orig_model"), BlockModel.class);
//        return new ProgressionOreGeometry(model);
//    }

    @Override
    public ProgressionOreGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        BlockModel model = deserializationContext.deserialize(GsonHelper.getAsJsonObject(jsonObject,"orig_model"), BlockModel.class);
        return new ProgressionOreGeometry(model);
    }
}

class ProgressionOreGeometry implements IUnbakedGeometry<ProgressionOreGeometry> {

    public BlockModel model;

    public ProgressionOreGeometry(BlockModel model){
        this.model = model;
    }

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
        BakedModel model = this.model.bake(baker,this.model,spriteGetter,modelState,modelLocation,false);
        return new ProgressionOreModel(model);
    }

}
