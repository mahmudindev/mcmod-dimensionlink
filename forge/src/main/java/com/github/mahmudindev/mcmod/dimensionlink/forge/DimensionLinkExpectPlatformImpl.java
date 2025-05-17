package com.github.mahmudindev.mcmod.dimensionlink.forge;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class DimensionLinkExpectPlatformImpl {
    public static Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}
