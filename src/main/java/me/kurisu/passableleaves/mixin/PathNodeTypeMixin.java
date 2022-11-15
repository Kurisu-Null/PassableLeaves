package me.kurisu.passableleaves.mixin;

import me.kurisu.passableleaves.PassableLeavesConfig;
import net.minecraft.entity.ai.pathing.PathNodeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(PathNodeType.class)
public abstract class PathNodeTypeMixin {

    @ModifyConstant(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=LEAVES")),
            constant = @Constant(floatValue = -1.0F))
    private static float passableLeaves_setDefaultPenaltyOfLeaves(float defaultPenalty) {
        if (PassableLeavesConfig.playerOnlyAffected) {
            return defaultPenalty;
        }
        return 1.0F;
    }
}
