package com.github.mahmudindev.mcmod.dimensionlink.fabric.mixin;

import com.github.mahmudindev.mcmod.dimensionlink.world.WorldManager;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Shadow public abstract ServerLevel serverLevel();

    @ModifyExpressionValue(
            method = "changeDimension",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;END:Lnet/minecraft/resources/ResourceKey;",
                    ordinal = 1
            )
    )
    private ResourceKey<Level> changeDimensionEndPlatform(
            ResourceKey<Level> original
    ) {
        return WorldManager.getWorldTheEnd(this.serverLevel(), original);
    }

    @ModifyExpressionValue(
            method = "changeDimension",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;END:Lnet/minecraft/resources/ResourceKey;",
                    ordinal = 0
            )
    )
    private ResourceKey<Level> changeDimensionEndRespawn0(
            ResourceKey<Level> original,
            ServerLevel serverLevel
    ) {
        if (WorldManager.disableWorldEndRespawn(
                this.serverLevel(),
                serverLevel.dimension()
        )) {
            return null;
        }
        return WorldManager.getWorldTheEnd(serverLevel, original);
    }

    @ModifyExpressionValue(
            method = "changeDimension",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;OVERWORLD:Lnet/minecraft/resources/ResourceKey;",
                    ordinal = 0
            )
    )
    private ResourceKey<Level> changeDimensionEndRespawn1(
            ResourceKey<Level> original
    ) {
        return WorldManager.getWorldOverworld(this.serverLevel(), original);
    }
}
