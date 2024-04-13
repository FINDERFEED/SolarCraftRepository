package com.finderfeed.solarcraft.config.json_config.reflective;

import com.finderfeed.solarcraft.config.json_config.JsonConfig;
import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This config requires fields within it to be
 * 1) Annotated with @ConfigValue
 * 2) Non-static, non-final and public
 * 3) If a value of a field is user-defined class it should have two STATIC methods:
 * *class* fromJson(JsonObject) that parses a json object and returns a value of said class.
 * void toJson(*class where static method is in*, JsonObject) that should jsonify the object into given JsonObject.
 * 4) Fields should be assigned a value from the start. It will be used as default value. Null is not permitted. Values of fields will be reassigned when
 * config load from disk or when config is updated by packet.
 */
public abstract class ReflectiveJsonConfig extends JsonConfig {

    public ReflectiveJsonConfig(String name) {
        super(name);
    }


    @Override
    public boolean parseJson(JsonObject object) {
        Class<? extends ReflectiveJsonConfig> c = this.getClass();
        Field[] fields = c.getFields();
        boolean changesMade = false;
        for (Field field : fields){
            if (field.getAnnotation(ConfigValue.class) != null){
                if (this.parseDataFromJson(field,object)){
                    changesMade = true;
                }
            }
        }
        return changesMade;
    }

    private boolean parseDataFromJson(Field field, JsonObject object){
        String name = field.getName();
        if (object.has(name)){
            this.readFromJson(field,object);
            return false;
        }else {
            this.addDataToJson(field,object);
            return true;
        }
    }

    private void readFromJson(Field field,JsonObject object){
        try {
            Object value = field.get(this);
            String name = field.getName();
            if (value instanceof Integer val) {
                field.set(this,object.get(name).getAsInt());
            }else if (value instanceof Float fl){
                field.set(this,object.get(name).getAsFloat());
            }else if (value instanceof Double d){
                field.set(this,object.get(name).getAsDouble());
            } else if (value instanceof String string) {
                field.set(this,object.get(name).getAsString());
            } else if (value instanceof Boolean b) {
                field.set(this,object.get(name).getAsBoolean());
            } else if (value instanceof Character character) {
                field.set(this,(char)object.get(name).getAsByte());
            } else {
                Class<?> fieldClass = field.getType();
                Method method = fieldClass.getMethod("fromJson", JsonObject.class);
                Object res = method.invoke(null,object.get(name).getAsJsonObject());
                field.set(this,res);
            }
        }catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e){
            throw new RuntimeException(e);
        }
    }

    private void addDataToJson(Field field,JsonObject object){
        try {
            Object value = field.get(this);
            String name = field.getName();
            if (value instanceof Number number) {
                object.addProperty(name, number);
            } else if (value instanceof String string) {
                object.addProperty(name, string);
            } else if (value instanceof Boolean b) {
                object.addProperty(name, b);
            } else if (value instanceof Character character) {
                object.addProperty(name, character);
            } else {
                Class<?> fieldClass = field.getType();
                Method method = fieldClass.getMethod("toJson",fieldClass, JsonObject.class);
                JsonObject jsonified = new JsonObject();
                method.invoke(null,value,jsonified);
                object.add(name,jsonified);
            }
        }catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e){
            throw new RuntimeException(e);
        }
    }
}
