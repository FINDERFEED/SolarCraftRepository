package com.finderfeed.solarcraft.local_library.entities.bossbar.client;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ActiveBossBar {

    private UUID uuid;
    private float progress = 0f;
    private String customBarId;
    private Component name;
    private int entityId;

    public ActiveBossBar(UUID uuid,Component name,float progress,String customBarId,int entityId){
        this.uuid = uuid;
        this.name = name;
        this.progress = progress;
        this.customBarId = customBarId;
        this.entityId = entityId;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getCustomBarId() {
        return customBarId;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public Component getName() {
        return name;
    }

    public int getEntityId() {
        return entityId;
    }

    @Nullable
    public Entity getEntity(Level world){
        return world.getEntity(entityId);
    }
}
