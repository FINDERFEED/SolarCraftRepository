package com.finderfeed.solarforge.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class Tags {




    public static void init(){}

    public static final Tag.Named<Block> CATALYST = BlockTags.createOptional(new ResourceLocation("solarforge","catalyst"));


}
