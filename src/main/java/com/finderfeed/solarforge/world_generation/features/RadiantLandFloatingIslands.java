package com.finderfeed.solarforge.world_generation.features;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.config.SolarcraftConfig;
import com.mojang.serialization.Codec;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class RadiantLandFloatingIslands extends Feature<NoneFeatureConfiguration> {

    private List<Block> AVAILABLE_TO_SPAWN;

    private static final ResourceLocation ISLAND1 = new ResourceLocation("solarforge:worldgen_features/floating_island_1");
    private static final ResourceLocation ISLAND2 = new ResourceLocation("solarforge:worldgen_features/floating_island_2");
    private static final ResourceLocation ISLAND3 = new ResourceLocation("solarforge:worldgen_features/floating_island_3");
    private static final ResourceLocation ISLAND4 = new ResourceLocation("solarforge:worldgen_features/floating_island_4");
    public RadiantLandFloatingIslands(Codec<NoneFeatureConfiguration> p_i231953_1_) {
        super(p_i231953_1_);
    }



    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel world = ctx.level();
        if (AVAILABLE_TO_SPAWN == null){
            initList(world);
        }
        BlockPos pos = ctx.origin();

        Random random = ctx.random();


        Rotation rot = Rotation.getRandom(random);
        StructureManager manager = world.getLevel().getStructureManager();
        StructureTemplate templ = manager.getOrCreate(List.of(ISLAND1,ISLAND2,ISLAND3,ISLAND4).get(random.nextInt(4)));
        StructurePlaceSettings set = new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.AIR).setRandom(random).setRotation(rot).setBoundingBox(BoundingBox.infinite());
        BlockPos blockpos1 = templ.getZeroPositionWithTransform(pos.offset(0,1,0), Mirror.NONE, rot);

        templ.placeInWorld(world, blockpos1, blockpos1, set, random, 4);
        templ.filterBlocks(blockpos1,set,Blocks.SEA_LANTERN).forEach((info)->{
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
                    }else{
                        SolarForge.LOGGER.log(Level.ERROR,"Couldn't parse block: " + string +" while initiating ores list");
                    }
                });
            }
        }
    }
}
