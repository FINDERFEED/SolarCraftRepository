package com.finderfeed.solarcraft.local_library.helpers;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.block.state.properties.Property;


import java.util.Optional;

public class FDBlockStateParser {



    public static BlockState parseString(String blockState){
        try {
            int propertiesIndex = blockState.indexOf("[");
            if (propertiesIndex != -1) {
                BlockState block = BuiltInRegistries.BLOCK.get(new ResourceLocation(blockState.substring(0, propertiesIndex))).defaultBlockState();
                return assignPropertiesNode(block,blockState);
            } else {
                return BuiltInRegistries.BLOCK.get(new ResourceLocation(blockState)).defaultBlockState();
            }
        }catch (Exception e){
            throw new RuntimeException("Error reading: " + blockState,e);
        }
    }


    private static BlockState assignPropertiesNode(BlockState state,String blockState){
        var definition = state.getBlock().getStateDefinition();
        String[] properties = blockState.substring(
                blockState.indexOf("[") + 1,
                blockState.indexOf("]")
        ).split(",");
        for (String propertyValue : properties){
            String[] pv = propertyValue.split("=");
            String sproperty = pv[0];
            String svalue = pv[1];
            Property<?> property = definition.getProperty(sproperty);
            state = assignProperty(property,svalue,state);
        }
        return state;
    }

    private static <T extends Comparable<T>> BlockState assignProperty(Property<T> property,String value,BlockState state){
        Optional<T> optional = property.getValue(value);
        if (!optional.isEmpty()){
            return state.setValue(property,optional.get());
        }else{
           throw new RuntimeException("Error reading property: " + property + " with value: " + value);
        }
    }

}
