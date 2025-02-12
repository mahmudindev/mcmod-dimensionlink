package com.github.mahmudindev.mcmod.dimensionlink;

import com.github.mahmudindev.mcmod.dimensionlink.config.Config;
import com.github.mahmudindev.mcmod.dimensionlink.world.WorldManager;
import com.mojang.logging.LogUtils;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import java.nio.file.Path;

public final class DimensionLink {
    public static final String MOD_ID = "dimensionlink";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static Path CONFIG_DIR = Path.of(".");

    public static void init() {
        Config.load();
    }

    public static void onResourceManagerReload(ResourceManager manager) {
        WorldManager.onResourceManagerReload(manager);
    }
}
