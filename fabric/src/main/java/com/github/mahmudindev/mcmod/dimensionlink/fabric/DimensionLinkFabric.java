package com.github.mahmudindev.mcmod.dimensionlink.fabric;

import com.github.mahmudindev.mcmod.dimensionlink.DimensionLink;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public final class DimensionLinkFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        DimensionLink.init();

        ResourceLoader
                .get(PackType.SERVER_DATA)
                .registerReloadListener(Identifier.fromNamespaceAndPath(
                        DimensionLink.MOD_ID,
                        "default"
                ), new ResourceManagerReloadListener() {
                    @Override
                    public void onResourceManagerReload(ResourceManager resourceManager) {
                        DimensionLink.onResourceManagerReload(resourceManager);
                    }
                });
    }
}
