package com.finderfeed.solarcraft.config.json_config.reflective;

import com.google.gson.JsonObject;

public interface ReflectiveSerializable<T> {

    T fromJson(JsonObject object);
    void toJson(T value,JsonObject object);

}
