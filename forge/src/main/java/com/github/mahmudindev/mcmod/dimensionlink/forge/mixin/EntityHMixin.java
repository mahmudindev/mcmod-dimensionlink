package com.github.mahmudindev.mcmod.dimensionlink.forge.mixin;

import com.github.mahmudindev.mcmod.dimensionlink.world.WorldManager;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.ITeleporter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Mixin(value = Entity.class, priority = 1250)
public abstract class EntityHMixin {
    @WrapOperation(
            method = "changeDimension(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraftforge/common/util/ITeleporter;)Lnet/minecraft/world/entity/Entity;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/common/util/ITeleporter;placeEntity(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerLevel;FLjava/util/function/Function;)Lnet/minecraft/world/entity/Entity;"
            ),
            remap = false
    )
    private Entity changeDimensionEndPlatform(
            ITeleporter instance,
            Entity entity,
            ServerLevel currentWorld,
            ServerLevel destWorld,
            float yaw,
            Function<Boolean, Entity> repositionEntity,
            Operation<Entity> original
    ) {
        Function<Boolean, Entity> modifiedRepositionEntity = (spawnPortal) -> {
            Entity entityX = repositionEntity.apply(false);

            if (spawnPortal && destWorld.dimension() == WorldManager.getWorldTheEnd(
                    currentWorld,
                    Level.END
            )) {
                ServerLevel.makeObsidianPlatform(destWorld);
            }

            return entityX;
        };

        return original.call(
                instance,
                entity,
                currentWorld,
                destWorld,
                yaw,
                modifiedRepositionEntity
        );
    }
}
