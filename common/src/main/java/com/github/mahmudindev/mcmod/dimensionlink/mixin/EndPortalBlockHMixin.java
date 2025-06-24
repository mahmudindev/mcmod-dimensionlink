package com.github.mahmudindev.mcmod.dimensionlink.mixin;

import com.github.mahmudindev.mcmod.dimensionlink.world.WorldManager;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EndPortalBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;

@Mixin(value = EndPortalBlock.class, priority = 1250)
public abstract class EndPortalBlockHMixin {
    @ModifyExpressionValue(
            method = "getPortalDestination",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;END:Lnet/minecraft/resources/ResourceKey;"
            )
    )
    private ResourceKey<Level> getPortalDestinationEndKey(
            ResourceKey<Level> original,
            ServerLevel serverLevel
    ) {
        return WorldManager.getWorldTheEnd(serverLevel, original);
    }

    @ModifyExpressionValue(
            method = "getPortalDestination",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/Level;OVERWORLD:Lnet/minecraft/resources/ResourceKey;"
            )
    )
    private ResourceKey<Level> getPortalDestinationOverworldKey(
            ResourceKey<Level> original,
            ServerLevel serverLevel
    ) {
        return WorldManager.getWorldOverworld(serverLevel, original);
    }

    @WrapOperation(
            method = "getPortalDestination",
            constant = @Constant(classValue = ServerPlayer.class, ordinal = 1)
    )
    public boolean getPortalDestinationRespawn(
            Object obj,
            Operation<Boolean> original,
            ServerLevel serverLevelA,
            @Local(ordinal = 1) ServerLevel serverLevelB
    ) {
        if (WorldManager.disableWorldEndRespawn(serverLevelA, serverLevelB.dimension())) {
            return false;
        }

        return original.call(obj);
    }
}
