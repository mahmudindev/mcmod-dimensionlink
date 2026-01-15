package com.github.mahmudindev.mcmod.dimensionlink.neoforge;

import com.github.mahmudindev.mcmod.dimensionlink.DimensionLink;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;

@Mod(DimensionLink.MOD_ID)
public final class DimensionLinkNeoForge {
    public DimensionLinkNeoForge() {
        // Run our common setup.
        DimensionLink.init();

        NeoForge.EVENT_BUS.addListener((AddServerReloadListenersEvent event) -> {
            event.addListener(
                    Identifier.fromNamespaceAndPath(
                            DimensionLink.MOD_ID,
                            "default"
                    ),
                    new ResourceManagerReloadListener() {
                        @Override
                        public void onResourceManagerReload(ResourceManager resourceManager) {
                            DimensionLink.onResourceManagerReload(resourceManager);
                        }
                    }
            );
        });
    }
}
