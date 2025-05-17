package com.github.mahmudindev.mcmod.dimensionlink;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

public class DimensionLinkExpectPlatform {
    @ExpectPlatform
    public static Path getConfigDir() {
        return Path.of(".");
    }
}
