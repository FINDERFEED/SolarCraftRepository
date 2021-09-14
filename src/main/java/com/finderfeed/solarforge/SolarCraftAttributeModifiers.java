package com.finderfeed.solarforge;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class SolarCraftAttributeModifiers {

    public static final UUID REACH_2_MODIFIER_UUID = UUID.fromString("082d2edf-8569-4cf1-a98a-968240513854");
    public static final AttributeModifier REACH_2_MODIFIER = new AttributeModifier(REACH_2_MODIFIER_UUID,"reach_2_mod",2, AttributeModifier.Operation.ADDITION);



}
