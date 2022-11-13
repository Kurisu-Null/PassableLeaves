package kurisu.passableleaves.mixin;

import kurisu.passableleaves.PassableLeaves;
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
    private static float passableleaves_setDefaultPenaltyOfLeaves(float defaultPenalty) {
        if (PassableLeaves.getConfig().isPlayerOnlyAffected()) {
            return defaultPenalty;
        }
        return 2.0F;
    }
}
