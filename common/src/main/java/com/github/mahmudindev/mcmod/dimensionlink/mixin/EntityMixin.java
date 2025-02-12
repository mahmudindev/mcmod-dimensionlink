package com.github.mahmudindev.mcmod.dimensionlink.mixin;

import com.github.mahmudindev.mcmod.dimensionlink.world.WorldManager;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract Level level();

    @WrapOperation(
            method = "handleNetherPortal",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/MinecraftServer;getLevel(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/server/level/ServerLevel;"
            )
    )
    private ServerLevel handleNetherPortalModifyKey(
            MinecraftServer instance,
            ResourceKey<Level> resourceKey,
            Operation<ServerLevel> original
    ) {
        return original.call(
                instance,
                WorldManager.getWorldLinkNether(this.level(), resourceKey)
        );
    }

    @ModifyExpressionValue(
            method = "findDimensionEntryPoint",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;END:Lnet/minecraft/resources/ResourceKey;",
                    ordinal = 0
            )
    )
    private ResourceKey<Level> findDimensionEntryPointEndKey0(
            ResourceKey<Level> original,
            ServerLevel serverLevel
    ) {
        return WorldManager.getWorldTheEnd(serverLevel, original);
    }

    @ModifyExpressionValue(
            method = "findDimensionEntryPoint",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;OVERWORLD:Lnet/minecraft/resources/ResourceKey;"
            )
    )
    private ResourceKey<Level> findDimensionEntryPointOverworldKey(
            ResourceKey<Level> original
    ) {
        return WorldManager.getWorldOverworld(this.level(), original);
    }

    @ModifyExpressionValue(
            method = "findDimensionEntryPoint",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;END:Lnet/minecraft/resources/ResourceKey;",
                    ordinal = 1
            )
    )
    private ResourceKey<Level> findDimensionEntryPointEndKey1(
            ResourceKey<Level> original
    ) {
        return WorldManager.getWorldTheEnd(this.level(), original);
    }

    @ModifyExpressionValue(
            method = "findDimensionEntryPoint",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;NETHER:Lnet/minecraft/resources/ResourceKey;",
                    ordinal = 0
            )
    )
    private ResourceKey<Level> findDimensionEntryPointNetherKey0(
            ResourceKey<Level> original
    ) {
        return WorldManager.getWorldTheNether(this.level(), original);
    }

    @ModifyExpressionValue(
            method = "findDimensionEntryPoint",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;NETHER:Lnet/minecraft/resources/ResourceKey;",
                    ordinal = 1
            )
    )
    private ResourceKey<Level> findDimensionEntryPointNetherKey1(
            ResourceKey<Level> original,
            ServerLevel serverLevel
    ) {
        return WorldManager.getWorldTheNether(serverLevel, original);
    }

    @WrapOperation(
            method = "findDimensionEntryPoint",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;getHeightmapPos(Lnet/minecraft/world/level/levelgen/Heightmap$Types;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;"
            )
    )
    private BlockPos findDimensionEntryPointRespawn(
            ServerLevel instance,
            Heightmap.Types types,
            BlockPos blockPos,
            Operation<BlockPos> original
    ) {
        if (WorldManager.disableWorldEndRespawn(
                this.level(),
                instance.dimension()
        )) {
            return blockPos;
        }

        return original.call(instance, types, blockPos);
    }
}
