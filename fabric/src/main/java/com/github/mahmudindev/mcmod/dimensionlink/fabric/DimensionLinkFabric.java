package com.github.mahmudindev.mcmod.dimensionlink.fabric;

import com.github.mahmudindev.mcmod.dimensionlink.DimensionLink;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

public final class DimensionLinkFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        DimensionLink.init();

        ResourceManagerHelper
                .get(PackType.SERVER_DATA)
                .registerReloadListener(new SimpleSynchronousResourceReloadListener() {
                    @Override
                    public ResourceLocation getFabricId() {
                        return ResourceLocation.fromNamespaceAndPath(
                                DimensionLink.MOD_ID,
                                "default"
                        );
                    }

                    @Override
                    public void onResourceManagerReload(ResourceManager resourceManager) {
                        DimensionLink.onResourceManagerReload(resourceManager);
                    }
                });
    }
}
