package com.finderfeed.solarcraft.registries;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class Tags {




    public static void init(){}

    public static final TagKey<Block> CATALYST = BlockTags.create(new ResourceLocation(SolarCraft.MOD_ID,"catalyst"));
    public static final TagKey<Block> MAGISTONE = BlockTags.create(new ResourceLocation(SolarCraft.MOD_ID,"magistone"));

    public static final TagKey<Block> INSCRIPTION_STONE = BlockTags.create(new ResourceLocation(SolarCraft.MOD_ID,"inscription_stone"));


}
