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
        config.getWorlds().forEach(world -> addWorld(world, null));

        Gson parser = new Gson();
        manager.listResources(
                DimensionLink.MOD_ID,
                resourceLocation -> resourceLocation.getPath().endsWith(".json")
        ).forEach((resourceLocation, resource) -> {
            String resourcePath = resourceLocation.getPath().replaceFirst(
                    "^%s/".formatted(DimensionLink.MOD_ID),
                    ""
            );

            if (!resourcePath.startsWith("world/")) {
                return;
            }

            try {
                addWorld(parser.fromJson(
                        JsonParser.parseReader(resource.openAsReader()),
                        WorldData.class
                ), resourceLocation.getNamespace());
            } catch (IOException e) {
                DimensionLink.LOGGER.error("Failed to read datapack", e);
            }
        });
    }

    public static List<WorldData> getWorlds() {
        return List.copyOf(WORLDS);
    }

    private static WorldData getWorldPartial(ResourceKey<Level> key) {
        for (WorldData world : getWorlds()) {
            if (key == world.getOverworldKey()) {
                return world;
            }

            if (key == world.getTheNetherKey() || key == world.getTheEndKey()) {
                return world;
            }
        }

        return null;
    }

    public static WorldData getWorld(Level level) {
        ResourceKey<Level> dimension = level.dimension();

        WorldData world = getWorldPartial(dimension);
        if (world != null) {
            return world;
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
                world = new WorldData();
                world.setOverworld("%s:%s".formatted(resourceNamespace, aPath));
                world.setTheNether("%s:%s".formatted(resourceNamespace, bPath));
                world.setTheEnd("%s:%s".formatted(resourceNamespace, cPath));
                world.setDisableEndRespawn(true);
            }
        }

        MinecraftServer server = level.getServer();
        if (world != null && server != null) {
            if (server.getLevel(world.getOverworldKey()) != null) {
                return world;
            }
        }

        return null;
    }

    public static ResourceKey<Level> getWorldOverworld(
            Level level,
            ResourceKey<Level> fallback
    ) {
        WorldData world = getWorld(level);
        if (world != null) {
            return world.getOverworldKey();
        }

        return fallback;
    }

    public static ResourceKey<Level> getWorldTheNether(
            Level level,
            ResourceKey<Level> fallback
    ) {
        WorldData world = getWorld(level);
        if (world != null) {
            return world.getTheNetherKey();
        }

        return fallback;
    }

    public static ResourceKey<Level> getWorldTheEnd(
            Level level,
            ResourceKey<Level> fallback
    ) {
        WorldData world = getWorld(level);
        if (world != null) {
            return world.getTheEndKey();
        }

        return fallback;
    }

    public static boolean disableWorldEndRespawn(
            Level level,
            ResourceKey<Level> destination
    ) {
        WorldData world = getWorld(level);
        if (world != null) {
            if (destination != world.getOverworldKey()) {
                return false;
            }

            if (level.dimension() != world.getTheEndKey()) {
                return false;
            }

            return world.isDisableEndRespawn();
        }

        return false;
    }

    private static void addWorld(
            WorldData world,
            String defaultNamespace
    ) {
        if (defaultNamespace != null) {
            String overworld = world.getOverworld();
            if (overworld != null && !overworld.contains(":")) {
                world.setOverworld("%s:%s".formatted(defaultNamespace, overworld));
            }

            String theNether = world.getTheNether();
            if (theNether != null && !theNether.contains(":")) {
                world.setTheNether("%s:%s".formatted(defaultNamespace, theNether));
            }

            String theEnd = world.getTheEnd();
            if (theEnd != null && !theEnd.contains(":")) {
                world.setTheEnd("%s:%s".formatted(defaultNamespace, theEnd));
            }
        }

        ResourceKey<Level> key = world.getOverworldKey();
        if (key == null || getWorldPartial(key) != null) {
            return;
        }

        WORLDS.add(world);
    }
}
