package com.finderfeed.solarforge.content.world_generation.dimension_related.radiant_land;

import com.finderfeed.solarforge.config.SolarcraftConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CrystallizedOreVeinFeature extends Feature<NoneFeatureConfiguration> {
    private List<Block> AVAILABLE_TO_SPAWN;
    private static final ResourceLocation VEIN = new ResourceLocation("solarforge:worldgen_features/crystallized_ore_vein");
    public CrystallizedOreVeinFeature(Codec<NoneFeatureConfiguration> p_65786_) {
        super(p_65786_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel world = ctx.level();
        if (AVAILABLE_TO_SPAWN == null){
            initList(world);
        }
        BlockPos pos = ctx.origin().below(13);

        RandomSource random = ctx.random();


        Rotation rot = Rotation.getRandom(random);
        StructureTemplateManager manager = world.getLevel().getStructureManager();
        StructureTemplate templ = manager.getOrCreate(VEIN);
        StructurePlaceSettings set = new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.AIR).setRandom(random).setRotation(rot).setBoundingBox(BoundingBox.infinite());
        BlockPos blockpos1 = templ.getZeroPositionWithTransform(pos.offset(0,1,0), Mirror.NONE, rot);

        templ.placeInWorld(world, blockpos1, blockpos1, set, random, 4);
        templ.filterBlocks(blockpos1,set, Blocks.SEA_LANTERN).forEach((info)->{
            setBlock(world,info.pos,AVAILABLE_TO_SPAWN.get(world.getRandom().nextInt(AVAILABLE_TO_SPAWN.size())).defaultBlockState());
        });

        return true;
    }

    private void initList(WorldGenLevel level){
        if (AVAILABLE_TO_SPAWN == null){
            AVAILABLE_TO_SPAWN = new ArrayList<>();
            List<String> ids = SolarcraftConfig.ISLAND_ORES.get();
            Optional<? extends Registry<Block>> a  = level.registryAccess().registry(Registry.BLOCK_REGISTRY);
            if (a.isPresent()){
                Registry<Block> reg = a.get();
                ids.forEach((string)->{
                    Block block = reg.get(new ResourceLocation(string));
                    if (block != null){
                        AVAILABLE_TO_SPAWN.add(block);
                    }
                });
            }
        }
    }
}
