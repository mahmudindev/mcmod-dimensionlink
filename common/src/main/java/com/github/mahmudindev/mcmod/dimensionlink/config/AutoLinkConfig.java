package com.github.mahmudindev.mcmod.dimensionlink.config;

import com.google.gson.annotations.SerializedName;

public class AutoLinkConfig {
    @SerializedName("exact_overworld_path")
    private String exactOverworldPath;
    @SerializedName("exact_the_nether_path")
    private String exactTheNetherPath;
    @SerializedName("exact_the_end_path")
    private String exactTheEndPath;

    public String getExactOverworldPath() {
        return this.exactOverworldPath;
    }

    public void setExactOverworldPath(String exactOverworldPath) {
        this.exactOverworldPath = exactOverworldPath;
    }

    public String getExactTheNetherPath() {
        return this.exactTheNetherPath;
    }

    public void setExactTheNetherPath(String exactTheNetherPath) {
        this.exactTheNetherPath = exactTheNetherPath;
    }

    public String getExactTheEndPath() {
        return this.exactTheEndPath;
    }

    public void setExactTheEndPath(String exactTheEndPath) {
        this.exactTheEndPath = exactTheEndPath;
    }
}
