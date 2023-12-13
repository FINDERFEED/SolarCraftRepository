package com.finderfeed.solarcraft.registries.attributes;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.RegistryObject;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;

public class AttributesRegistry {

    public static final DeferredRegister<Attribute> DEF_REG = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, SolarCraft.MOD_ID);

    public static final RegistryObject<Attribute> MAXIMUM_HEALTH_NO_LIMIT = DEF_REG.register("health_no_limit",
            ()->new RangedAttribute("attribute.name.generic.max_health_no_limit",1,1,Integer.MAX_VALUE).setSyncable(true));

    public static final RegistryObject<Attribute> MAGIC_RESISTANCE = DEF_REG.register("magic_resistance",
            ()->new RangedAttribute("attribute.name.generic.magic_resistance",1,1,100).setSyncable(true));
}
