package me.kurisu.passableleaves.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowableFluid.class)
public class FlowableFluidMixin {

    @Inject(method = "receivesFlow", at = @At("HEAD"), cancellable = true)
    private void passableleaves_receivesFlow(Direction face, BlockView world, BlockPos pos, BlockState state, BlockPos fromPos, BlockState fromState, CallbackInfoReturnable<Boolean> cir) {
        if (state.isIn(BlockTags.LEAVES)) {
            cir.setReturnValue((false));
        }
    }
}
