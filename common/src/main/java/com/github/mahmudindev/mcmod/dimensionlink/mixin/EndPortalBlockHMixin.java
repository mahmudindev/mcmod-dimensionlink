package com.github.mahmudindev.mcmod.dimensionlink.mixin;

import com.github.mahmudindev.mcmod.dimensionlink.world.WorldManager;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EndPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EndPortalBlock.class, priority = 1250)
public abstract class EndPortalBlockHMixin {
    @ModifyExpressionValue(
            method = "entityInside",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;END:Lnet/minecraft/resources/ResourceKey;"
            )
    )
    private ResourceKey<Level> entityInsideEndKey(
            ResourceKey<Level> original,
            BlockState blockState,
            Level level
    ) {
        return WorldManager.getWorldTheEnd(level, original);
    }

    @ModifyExpressionValue(
            method = "entityInside",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;OVERWORLD:Lnet/minecraft/resources/ResourceKey;"
            )
    )
    private ResourceKey<Level> entityInsideOverworldKey(
            ResourceKey<Level> original,
            BlockState blockState,
            Level level
    ) {
        return WorldManager.getWorldOverworld(level, original);
    }
}
