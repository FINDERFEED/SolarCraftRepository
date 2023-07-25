package com.finderfeed.solarcraft.local_library.other.delayed_getter;

public class DelayedObjectGetter<T>{

    protected T value;

    public DelayedObjectGetter(T value){
        this.value = value;
    }

    public DelayedObjectGetter(){
        this.value = null;
    }

    public T getValue() {
        return value;
    }
}
