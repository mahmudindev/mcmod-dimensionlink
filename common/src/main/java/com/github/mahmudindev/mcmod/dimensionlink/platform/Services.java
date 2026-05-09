package com.github.mahmudindev.mcmod.dimensionlink.platform;

import com.github.mahmudindev.mcmod.dimensionlink.DimensionLink;
import com.github.mahmudindev.mcmod.dimensionlink.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(
                clazz,
                Services.class.getClassLoader()
        ).findFirst().orElseThrow(() -> {
            return new NullPointerException("Failed to load service for " + clazz.getName());
        });
        DimensionLink.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
