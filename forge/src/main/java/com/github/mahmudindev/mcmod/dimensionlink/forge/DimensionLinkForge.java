package com.github.mahmudindev.mcmod.dimensionlink.forge;

import com.github.mahmudindev.mcmod.dimensionlink.DimensionLink;
import net.minecraftforge.fml.common.Mod;

@Mod(DimensionLink.MOD_ID)
public final class DimensionLinkForge {
    public DimensionLinkForge() {
        // Run our common setup.
        DimensionLink.init();
    }
}
