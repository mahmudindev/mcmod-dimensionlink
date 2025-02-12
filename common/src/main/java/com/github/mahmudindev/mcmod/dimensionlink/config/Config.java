package com.github.mahmudindev.mcmod.dimensionlink.config;

import com.github.mahmudindev.mcmod.dimensionlink.DimensionLink;
import com.github.mahmudindev.mcmod.dimensionlink.world.WorldData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class Config {
    private static Config CONFIG = new Config();

    @SerializedName("auto_link")
    private final AutoLinkConfig autoLink = new AutoLinkConfig();
    private final List<WorldData> worlds = new LinkedList<>();

    private void defaults() {
        this.autoLink.setExactOverworldPath("overworld");
        this.autoLink.setExactTheNetherPath("the_nether");
        this.autoLink.setExactTheEndPath("the_end");

        WorldData worldData = new WorldData();
        worldData.setOverworld(DimensionLink.MOD_ID + ":overworld");
        worldData.setTheNether(DimensionLink.MOD_ID + ":the_nether");
        worldData.setTheEnd(DimensionLink.MOD_ID + ":the_end");
        worldData.setDisableEndRespawn(true);
        this.worlds.add(worldData);
    }

    public AutoLinkConfig getAutoLink() {
        return this.autoLink;
    }

    public List<WorldData> getWorlds() {
        return List.copyOf(this.worlds);
    }

    private static Path getPath() {
        return DimensionLink.CONFIG_DIR.resolve(DimensionLink.MOD_ID + ".json");
    }

    private static File getFile() {
        return getPath().toFile();
    }

    public static void load() {
        Gson parser = new GsonBuilder().setPrettyPrinting().create();

        File configFile = getFile();
        if (!configFile.exists()) {
            CONFIG.defaults();

            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write(parser.toJson(CONFIG));
            } catch (IOException e) {
                DimensionLink.LOGGER.error("Failed to write config", e);
            }
        } else {
            try (FileReader reader = new FileReader(configFile)) {
                CONFIG = parser.fromJson(reader, Config.class);
            } catch (IOException e) {
                DimensionLink.LOGGER.error("Failed to read config", e);
            }
        }
    }

    public static Config getConfig() {
        return CONFIG;
    }
}
