package com.github.mahmudindev.mcmod.dimensionlink.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class DimensionLinkExpectPlatformImpl {
    public static Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
