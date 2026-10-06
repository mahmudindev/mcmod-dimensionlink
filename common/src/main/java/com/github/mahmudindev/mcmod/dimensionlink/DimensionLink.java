package com.github.mahmudindev.mcmod.dimensionlink;

import com.github.mahmudindev.mcmod.dimensionlink.config.Config;
import com.github.mahmudindev.mcmod.dimensionlink.world.WorldManager;
import com.github.mahmudindev.mcmod.orenoevents.event.events.ServerEvents;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public final class DimensionLink {
    public static final String MOD_ID = "dimensionlink";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        Config.load();

        ServerEvents.RESOURCE_MANAGER_RELOAD.register(resourceManager -> {
            WorldManager.onServerResourceManagerReload(resourceManager);
        });
    }
}
