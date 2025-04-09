package com.github.mahmudindev.mcmod.dimensionlink.world;

import com.google.gson.annotations.SerializedName;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class WorldData {
    private String overworld;
    @SerializedName("the_nether")
    private String theNether;
    @SerializedName("the_end")
    private String theEnd;
    @SerializedName("disable_end_respawn")
    private boolean disableEndRespawn;

    public String getOverworld() {
        return this.overworld;
    }

    public ResourceKey<Level> getOverworldKey() {
        String id = this.getOverworld();

        if (id == null || id.isEmpty()) {
            return null;
        }

        return ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(id));
    }

    public void setOverworld(String overworld) {
        this.overworld = overworld;
    }

    public String getTheNether() {
        return this.theNether;
    }

    public ResourceKey<Level> getTheNetherKey() {
        String id = this.getTheNether();

        if (id == null || id.isEmpty()) {
            return null;
        }

        return ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(id));
    }

    public void setTheNether(String theNether) {
        this.theNether = theNether;
    }

    public String getTheEnd() {
        return this.theEnd;
    }

    public ResourceKey<Level> getTheEndKey() {
        String id = this.getTheEnd();

        if (id == null || id.isEmpty()) {
            return null;
        }

        return ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(id));
    }

    public void setTheEnd(String theEnd) {
        this.theEnd = theEnd;
    }

    public boolean isDisableEndRespawn() {
        return this.disableEndRespawn;
    }

    public void setDisableEndRespawn(boolean disableEndRespawn) {
        this.disableEndRespawn = disableEndRespawn;
    }
}
