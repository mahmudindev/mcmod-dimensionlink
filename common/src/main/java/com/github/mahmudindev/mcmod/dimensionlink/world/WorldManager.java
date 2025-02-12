package com.github.mahmudindev.mcmod.dimensionlink.world;

import com.github.mahmudindev.mcmod.dimensionlink.DimensionLink;
import com.github.mahmudindev.mcmod.dimensionlink.config.AutoLinkConfig;
import com.github.mahmudindev.mcmod.dimensionlink.config.Config;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.Level;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WorldManager {
    private static final List<WorldData> WORLDS = new LinkedList<>();

    public static void onResourceManagerReload(ResourceManager manager) {
        WORLDS.clear();

        Config config = Config.getConfig();
        config.getWorlds().forEach(worldData -> addWorld(worldData, null));

        Gson parser = new Gson();
        manager.listResources(
                DimensionLink.MOD_ID,
                resourceLocation -> resourceLocation.getPath().endsWith(".json")
        ).forEach((resourceLocation, resource) -> {
            String resourcePath = resourceLocation.getPath().replaceFirst(
                    "^%s/".formatted(DimensionLink.MOD_ID),
                    ""
            );

            try {
                if (resourcePath.startsWith("world/")) {
                    addWorld(parser.fromJson(
                            JsonParser.parseReader(resource.openAsReader()),
                            WorldData.class
                    ), resourceLocation.getNamespace());
                }
            } catch (IOException e) {
                DimensionLink.LOGGER.error("Failed to read datapack", e);
            }
        });
    }

    public static List<WorldData> getWorlds() {
        return List.copyOf(WORLDS);
    }

    private static WorldData getWorldPartial(ResourceKey<Level> key) {
        for (WorldData worldData : getWorlds()) {
            if (key == worldData.getOverworldKey()) {
                return worldData;
            }

            if (key == worldData.getTheNetherKey() || key == worldData.getTheEndKey()) {
                return worldData;
            }
        }

        return null;
    }

    public static WorldData getWorld(Level level) {
        ResourceKey<Level> dimension = level.dimension();

        WorldData worldData = getWorldPartial(dimension);
        if (worldData != null) {
            return worldData;
        }

        Config config = Config.getConfig();
        AutoLinkConfig autoLink = config.getAutoLink();

        ResourceLocation resourceLocation = dimension.location();
        String resourceNamespace = resourceLocation.getNamespace();
        String resourcePath = resourceLocation.getPath();

        if (!resourceNamespace.equals(ResourceLocation.DEFAULT_NAMESPACE)) {
            String aPath = autoLink.getExactOverworldPath();
            String bPath = autoLink.getExactTheNetherPath();
            String cPath = autoLink.getExactTheEndPath();

            if (Arrays.asList(aPath, bPath, cPath).contains(resourcePath)) {
                worldData = new WorldData();
                worldData.setOverworld("%s:%s".formatted(resourceNamespace, aPath));
                worldData.setTheNether("%s:%s".formatted(resourceNamespace, bPath));
                worldData.setTheEnd("%s:%s".formatted(resourceNamespace, cPath));
                worldData.setDisableEndRespawn(true);
            }
        }

        MinecraftServer minecraftServer = level.getServer();
        if (worldData != null && minecraftServer != null) {
            if (minecraftServer.getLevel(worldData.getOverworldKey()) != null) {
                return worldData;
            }
        }

        return null;
    }

    public static ResourceKey<Level> getWorldOverworld(
            Level level,
            ResourceKey<Level> fallback
    ) {
        WorldData worldData = getWorld(level);
        if (worldData != null) {
            return worldData.getOverworldKey();
        }

        return fallback;
    }

    public static ResourceKey<Level> getWorldTheNether(
            Level level,
            ResourceKey<Level> fallback
    ) {
        WorldData worldData = getWorld(level);
        if (worldData != null) {
            return worldData.getTheNetherKey();
        }

        return fallback;
    }

    public static ResourceKey<Level> getWorldTheEnd(
            Level level,
            ResourceKey<Level> fallback
    ) {
        WorldData worldData = getWorld(level);
        if (worldData != null) {
            return worldData.getTheEndKey();
        }

        return fallback;
    }

    public static ResourceKey<Level> getWorldLinkNether(
            Level level,
            ResourceKey<Level> fallback
    ) {
        ResourceKey<Level> dimension = level.dimension();

        WorldData worldData = getWorld(level);
        if (worldData != null) {
            if (dimension == worldData.getOverworldKey()) {
                return worldData.getTheNetherKey();
            }

            if (dimension == worldData.getTheNetherKey()) {
                return worldData.getOverworldKey();
            }
        }

        return fallback;
    }

    public static ResourceKey<Level> getWorldLinkEnd(
            Level level,
            ResourceKey<Level> fallback
    ) {
        ResourceKey<Level> dimension = level.dimension();

        WorldData worldData = getWorld(level);
        if (worldData != null) {
            if (dimension == worldData.getOverworldKey()) {
                return worldData.getTheEndKey();
            }

            if (dimension == worldData.getTheEndKey()) {
                return worldData.getOverworldKey();
            }
        }

        return fallback;
    }

    public static boolean disableWorldEndRespawn(
            Level level,
            ResourceKey<Level> destination
    ) {
        WorldData worldData = getWorld(level);
        if (worldData != null) {
            if (destination != worldData.getOverworldKey()) {
                return false;
            }

            if (level.dimension() != worldData.getTheEndKey()) {
                return false;
            }

            return worldData.isDisableEndRespawn();
        }

        return false;
    }

    private static void addWorld(
            WorldData worldData,
            String defaultNamespace
    ) {
        if (defaultNamespace != null) {
            String overworld = worldData.getOverworld();
            if (overworld != null && !overworld.contains(":")) {
                worldData.setOverworld("%s:%s".formatted(defaultNamespace, overworld));
            }

            String theNether = worldData.getTheNether();
            if (theNether != null && !theNether.contains(":")) {
                worldData.setTheNether("%s:%s".formatted(defaultNamespace, theNether));
            }

            String theEnd = worldData.getTheEnd();
            if (theEnd != null && !theEnd.contains(":")) {
                worldData.setTheEnd("%s:%s".formatted(defaultNamespace, theEnd));
            }
        }

        ResourceKey<Level> key = worldData.getOverworldKey();
        if (key == null || getWorldPartial(key) != null) {
            return;
        }

        WORLDS.add(worldData);
    }
}
