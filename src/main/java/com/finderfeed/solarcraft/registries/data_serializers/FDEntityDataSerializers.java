package com.finderfeed.solarcraft.registries.data_serializers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.helpers.FriendlyByteBufHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class FDEntityDataSerializers {


    public static final DeferredRegister<EntityDataSerializer<?>> DEF_REG = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, SolarCraft.MOD_ID);

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

    public static final RegistryObject<EntityDataSerializer<List<Vec3>>> POSITION_LIST = DEF_REG.register("vec3list",()->{
        return new EntityDataSerializer<List<Vec3>>() {
            @Override
            public void write(FriendlyByteBuf buf, List<Vec3> vec3list) {
                buf.writeInt(vec3list.size());
                for (Vec3 v : vec3list){
                    FriendlyByteBufHelper.writeVec3(buf,v);
                }
            }

            @Override
            public List<Vec3> read(FriendlyByteBuf buf) {
                List<Vec3> l = new ArrayList<>();
                int size = buf.readInt();
                for (int i = 0; i < size; i++){
                    l.add(FriendlyByteBufHelper.readVec3(buf));
                }
                return l;
            }

            @Override
            public List<Vec3> copy(List<Vec3> list) {
                return new ArrayList<>(list);
            }


        };
    });






}
