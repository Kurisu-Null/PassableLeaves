package me.kurisu.passableleaves.mixin;

import me.kurisu.passableleaves.PassableLeaves;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LandPathNodeMaker.class)
public class LandPathNodeMakerMixin {

    @Inject(method = "adjustNodeType", at = @At("HEAD"), cancellable = true)
    private void passableLeaves_adjustNodeType_changeLeavesToOpen(BlockView world, BlockPos pos, PathNodeType type, CallbackInfoReturnable<PathNodeType> cir) {
        if (type == PathNodeType.LEAVES && !PassableLeaves.CONFIG.playerOnlyAffected()) {
            cir.setReturnValue(PathNodeType.LEAVES);
        }
    }
}
