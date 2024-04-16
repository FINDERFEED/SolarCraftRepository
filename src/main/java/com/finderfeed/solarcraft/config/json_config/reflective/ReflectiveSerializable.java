package com.finderfeed.solarcraft.config.json_config.reflective;

import com.mojang.serialization.Codec;

public interface ReflectiveSerializable<T> {
    Codec<T> reflectiveCodec();

}
