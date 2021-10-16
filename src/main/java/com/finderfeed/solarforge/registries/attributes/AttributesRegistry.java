package com.finderfeed.solarforge.registries.attributes;

import com.finderfeed.solarforge.SolarForge;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AttributesRegistry {

    public static final DeferredRegister<Attribute> DEF_REG = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, SolarForge.MOD_ID);

    public static final RegistryObject<Attribute> MAXIMUM_HEALTH_NO_LIMIT = DEF_REG.register("health_no_limit",
            ()->new RangedAttribute("attribute.name.generic.max_health_no_limit",1,1,Integer.MAX_VALUE).setSyncable(true));

}
