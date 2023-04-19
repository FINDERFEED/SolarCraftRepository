package com.finderfeed.solarcraft.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Stack;

public class JsonBuilder {

    private JsonObject mainObject;
    private Stack<JsonObject> currentStack;

    private JsonBuilder(){
        mainObject = new JsonObject();
        currentStack = new Stack<>();
        currentStack.add(mainObject);
    }

    public static JsonBuilder begin(){
        return new JsonBuilder();
    }

    public JsonBuilder addArray(String id,JsonArray array){
        currentStack.peek().add(id,array);
        return this;
    }

    public JsonBuilder escape(){
        if (this.currentStack.size() > 1){
            currentStack.pop();
            return this;
        }else{
            throw new RuntimeException("Cannot escape from builder < 1 JsonObject current size!");
        }
    }

    public JsonBuilder addJsonObject(String id){
        JsonObject object = new JsonObject();
        this.currentStack.peek().add(id,object);
        this.currentStack.add(object);
        return this;
    }

    public JsonBuilder addInt(String id,int element){
        this.currentStack.peek().addProperty(id,element);
        return this;
    }

    public JsonBuilder addFloat(String id,float element){
        this.currentStack.peek().addProperty(id,element);
        return this;
    }

    public JsonBuilder addDouble(String id,double element){
        this.currentStack.peek().addProperty(id,element);
        return this;
    }

    public JsonBuilder addString(String id,String element){
        this.currentStack.peek().addProperty(id,element);
        return this;
    }

    public JsonObject end(){
        if (currentStack.size() != 1){
            throw new RuntimeException("Didn't escape from all objects!");
        }
        return mainObject;
    }

    public static class Array{

        private JsonArray array;

        public Array(){
            array = new JsonArray();
        }

        public Array addString(String string){
            array.add(string);
            return this;
        }

        public Array addNumber(Number number){
            array.add(number);
            return this;
        }

    }
}
