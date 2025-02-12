package com.github.mahmudindev.mcmod.dimensionlink.mixin;

import com.github.mahmudindev.mcmod.dimensionlink.world.WorldManager;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {
    @ModifyExpressionValue(
            method = "inPortalDimension",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;OVERWORLD:Lnet/minecraft/resources/ResourceKey;"
            )
    )
    private static ResourceKey<Level> inPortalDimensionOverworldKey(
            ResourceKey<Level> original,
            Level level
    ) {
        if (WorldManager.getWorldOverworld(level, original) != null) {
            return level.dimension();
        }

        return original;
    }

    @ModifyExpressionValue(
            method = "inPortalDimension",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;NETHER:Lnet/minecraft/resources/ResourceKey;"
            )
    )
    private static ResourceKey<Level> inPortalDimensionNetherKey(
            ResourceKey<Level> original,
            Level level
    ) {
        if (WorldManager.getWorldTheNether(level, original) != null) {
            return level.dimension();
        }

        return original;
    }
}
