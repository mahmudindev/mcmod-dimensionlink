package com.github.mahmudindev.mcmod.dimensionlink;

import com.github.mahmudindev.mcmod.dimensionlink.config.Config;
import com.github.mahmudindev.mcmod.dimensionlink.platform.Services;
import com.github.mahmudindev.mcmod.dimensionlink.platform.services.IPlatformHelper;
import com.github.mahmudindev.mcmod.dimensionlink.world.WorldManager;
import com.mojang.logging.LogUtils;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

public final class DimensionLink {
    public static final String MOD_ID = "dimensionlink";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final IPlatformHelper PLATFORM = Services.PLATFORM;

    public static void init() {
        Config.load();
    }

    public static void onResourceManagerReload(ResourceManager resourceManager) {
        WorldManager.onResourceManagerReload(resourceManager);
    }
}
