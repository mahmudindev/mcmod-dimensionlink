package com.github.mahmudindev.mcmod.dimensionlink.forge;

import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.common.Mod;

import com.github.mahmudindev.mcmod.dimensionlink.DimensionLink;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(DimensionLink.MOD_ID)
public final class DimensionLinkForge {
    public DimensionLinkForge() {
        DimensionLink.CONFIG_DIR = FMLPaths.CONFIGDIR.get();

        // Run our common setup.
        DimensionLink.init();

        MinecraftForge.EVENT_BUS.addListener((AddReloadListenerEvent event) -> {
            event.addListener(this.onResourceManagerReload());
        });
    }

    private ResourceManagerReloadListener onResourceManagerReload() {
        return DimensionLink::onResourceManagerReload;
    }
}
