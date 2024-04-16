package com.finderfeed.solarcraft.config.json_config.reflective;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.config.json_config.JsonConfig;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

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

    private HashMap<String,Object> defaultValues;

    public ReflectiveJsonConfig(String name) {
        super(name);
        defaultValues = new HashMap<>();
    }

    public void memorizeDefaultValues(){
        for (Field field : this.getClass().getFields()){
            if (field.getAnnotation(ConfigValue.class) != null){
                try {
                    this.defaultValues.put(field.getName(),field.get(this));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
            Class<?> fieldClass = field.getType();
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
            } else if (ReflectiveSerializable.class.isAssignableFrom(fieldClass)){
                Codec<?> codec = ((ReflectiveSerializable<?>)value).reflectiveCodec();
                JsonObject obj = object.getAsJsonObject(name);
                var res = codec.parse(JsonOps.INSTANCE,obj).resultOrPartial(str->{
                    SolarCraft.LOGGER.log(Level.ERROR,"Error while parsing field: " + name + ", in config: " + this.getName() + ", using default value.");
                    SolarCraft.LOGGER.log(Level.ERROR,str);
                });
                if (res.isPresent()){
                    field.set(this,res.get());
                }else{
                    field.set(this,this.defaultValues.get(name));
                }
            }else{
                throw new RuntimeException("Cannot deserialize field: " + name + ", in config " + this.getName());
            }
        }catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    private void addDataToJson(Field field,JsonObject object){
        try {
            Object value = field.get(this);
            String name = field.getName();
            Class<?> fieldClass = field.getType();
            if (value instanceof Number number) {
                object.addProperty(name, number);
            } else if (value instanceof String string) {
                object.addProperty(name, string);
            } else if (value instanceof Boolean b) {
                object.addProperty(name, b);
            } else if (value instanceof Character character) {
                object.addProperty(name, character);
            } else if (ReflectiveSerializable.class.isAssignableFrom(fieldClass)) {
                Object defaultVal = defaultValues.get(name);
                Codec<?> codec = ((ReflectiveSerializable<?>)value).reflectiveCodec();
                this.hackyCodecUse(defaultVal,(Codec<? super Object>) codec,object,name);
            }else{
                throw new RuntimeException("Cannot serialize field: " + name + ", in config " + this.getName());
            }
        }catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    private <T> void hackyCodecUse(T defaultVal,Codec<T> codec,JsonObject object,String name){
        var result = codec.encodeStart(JsonOps.INSTANCE,defaultVal).resultOrPartial(str->{
            SolarCraft.LOGGER.log(Level.ERROR,"Error while encoding field: " + name + ", in config: " + this.getName() + ", tell mod dev that he is dumb.");
            SolarCraft.LOGGER.log(Level.ERROR,str);
        });
        if (result.isPresent()){
            object.add(name,result.get());
        }else{
            throw new RuntimeException("Couldn't encode field for config. See log for details.");
        }
    }
}
