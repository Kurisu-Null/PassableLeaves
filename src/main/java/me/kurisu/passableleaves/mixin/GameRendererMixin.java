package me.kurisu.passableleaves.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import me.kurisu.passableleaves.access.GameRendererAccess;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin implements GameRendererAccess {

    @Shadow
    @Final
    MinecraftClient client;

    @Nullable
    @Unique
    public BlockPos bypassedLeave;

    @Override
    @Nullable
    public BlockPos passableLeaves$getBypassedLeave() {
        return this.bypassedLeave;
    }

    @ModifyExpressionValue(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;squaredDistanceTo(Lnet/minecraft/util/math/Vec3d;)D", opcode = Opcodes.INVOKEVIRTUAL, ordinal = 0))
    private double passableleaves$updateTargetedEntity$ignoreLeavesSafeEBefore(double original, @Local(ordinal = 1) double eBefore, @Share("eBeforeApplying") LocalDoubleRef sharedEOriginalRef, @Share("bypassedLeave") LocalRef<BlockPos> bypassedLeave) {
        HitResult hitResult = this.client.crosshairTarget;
        this.bypassedLeave = null;

        if (hitResult == null) {
            return original;
        }

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            World world = this.client.world;

            if (world == null || !world.isClient) {
                return original;
            }

            BlockState blockState = world.getBlockState(blockHitResult.getBlockPos());

            if (blockState.isIn(BlockTags.LEAVES)) {
                sharedEOriginalRef.set(original);
                bypassedLeave.set(blockHitResult.getBlockPos());
                return eBefore;
            }
        }

        return original;
    }

    @ModifyExpressionValue(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;squaredDistanceTo(Lnet/minecraft/util/math/Vec3d;)D", opcode = Opcodes.INVOKEVIRTUAL, ordinal = 1))
    private double passableleaves$updateTargetedEntity$checkIfEntityInRange(double originalG, @Local(ordinal = 1) LocalDoubleRef e, @Share("eBeforeApplying") LocalDoubleRef sharedEOriginalRef, @Share("bypassedLeave") LocalRef<BlockPos> bypassedLeave) {
        // Check if leaves where before entity
        if (bypassedLeave.get() != null && originalG >= sharedEOriginalRef.get()) {
            this.bypassedLeave = bypassedLeave.get();
        }

        return originalG;
    }


}
