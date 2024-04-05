package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager;

import net.minecraft.world.entity.Entity;

public interface AnimationManager {

    void tickAnimations();

    void setAnimation(String tickerName, AnimationTicker animation);

    void stopAnimation(String tickerName);

    AnimationTicker getTicker(String name);

    default ClientAnimationManager getAsClientManager(){
        return (ClientAnimationManager) this;
    }

    default ServerAnimationManager getAsServerManager(){
        return (ServerAnimationManager) this;
    }

    static AnimationManager createEntityAnimationManager(Entity entity,boolean isClientSide){
        if (isClientSide){
            return new ClientAnimationManager();
        }else{
            return new EntityServerAnimationManager(entity);
        }
    }

}
