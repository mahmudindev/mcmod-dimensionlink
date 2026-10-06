package com.github.mahmudindev.mcmod.dimensionlink.neoforge;

import com.github.mahmudindev.mcmod.dimensionlink.DimensionLink;
import net.neoforged.fml.common.Mod;

@Mod(DimensionLink.MOD_ID)
public final class DimensionLinkNeoForge {
    public DimensionLinkNeoForge() {
        // Run our common setup.
        DimensionLink.init();
    }
}
