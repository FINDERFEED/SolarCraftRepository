package com.finderfeed.solarforge.registries.data_serializers;

import com.finderfeed.solarforge.SolarForge;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FDEntityDataSerializers {


    public static final DeferredRegister<EntityDataSerializer<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, SolarForge.MOD_ID);

    public static final RegistryObject<EntityDataSerializer<Vec3>> VEC3 = DEF_REG.register("vec3",()->{
        return new EntityDataSerializer<Vec3>() {
            @Override
            public void write(FriendlyByteBuf buf, Vec3 vec3) {
                buf.writeDouble(vec3.x);
                buf.writeDouble(vec3.y);
                buf.writeDouble(vec3.z);
            }

            @Override
            public Vec3 read(FriendlyByteBuf buf) {
                return new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble());
            }

            @Override
            public Vec3 copy(Vec3 vec) {
                return new Vec3(vec.x,vec.y,vec.z);
            }
        };
    });






}
