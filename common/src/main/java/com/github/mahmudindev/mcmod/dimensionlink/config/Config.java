package com.github.mahmudindev.mcmod.dimensionlink.config;

import com.github.mahmudindev.mcmod.dimensionlink.DimensionLink;
import com.github.mahmudindev.mcmod.dimensionlink.DimensionLinkExpectPlatform;
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
    private static final Path CONFIG_DIR = DimensionLinkExpectPlatform.getConfigDir();
    private static Config CONFIG = new Config();

    @SerializedName("auto_link")
    private final AutoLinkConfig autoLink = new AutoLinkConfig();
    private final List<WorldData> worlds = new LinkedList<>();

    private void defaults() {
        this.autoLink.setExactOverworldPath("overworld");
        this.autoLink.setExactTheNetherPath("the_nether");
        this.autoLink.setExactTheEndPath("the_end");

        WorldData world = new WorldData();
        world.setOverworld(DimensionLink.MOD_ID + ":overworld");
        world.setTheNether(DimensionLink.MOD_ID + ":the_nether");
        world.setTheEnd(DimensionLink.MOD_ID + ":the_end");
        world.setDisableEndRespawn(true);
        this.worlds.add(world);
    }

    public AutoLinkConfig getAutoLink() {
        return this.autoLink;
    }

    public List<WorldData> getWorlds() {
        return List.copyOf(this.worlds);
    }

    public static void load() {
        Gson parser = new GsonBuilder().setPrettyPrinting().create();

        File configFile = CONFIG_DIR.resolve(DimensionLink.MOD_ID + ".json").toFile();
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
