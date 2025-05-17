package com.github.mahmudindev.mcmod.dimensionlink.forge;

import com.github.mahmudindev.mcmod.dimensionlink.DimensionLink;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(DimensionLink.MOD_ID)
public final class DimensionLinkForge {
    public DimensionLinkForge() {
        // Run our common setup.
        DimensionLink.init();

        MinecraftForge.EVENT_BUS.addListener((AddReloadListenerEvent event) -> {
            event.addListener(new ResourceManagerReloadListener() {
                @Override
                public void onResourceManagerReload(ResourceManager resourceManager) {
                    DimensionLink.onResourceManagerReload(resourceManager);
                }
            });
        });
    }
}
