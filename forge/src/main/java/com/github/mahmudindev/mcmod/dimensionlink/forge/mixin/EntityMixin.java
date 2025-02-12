package com.github.mahmudindev.mcmod.dimensionlink.forge.mixin;

import com.github.mahmudindev.mcmod.dimensionlink.world.WorldManager;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract Level level();

    @ModifyExpressionValue(
            method = "lambda$changeDimension$16",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;END:Lnet/minecraft/resources/ResourceKey;"
            ),
            remap = false
    )
    private ResourceKey<Level> changeDimensionModifyEndPlatform(
            ResourceKey<Level> original
    ) {
        return WorldManager.getWorldTheEnd(this.level(), original);
    }
}
