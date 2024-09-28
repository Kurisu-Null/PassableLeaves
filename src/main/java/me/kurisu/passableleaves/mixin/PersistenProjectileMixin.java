package me.kurisu.passableleaves.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.kurisu.passableleaves.access.AbstractBlockStateAccess;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistenProjectileMixin {

    @Shadow
    private @Nullable BlockState inBlockState;

    @Shadow
    protected abstract boolean shouldFall();

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/shape/VoxelShape;"))
    private VoxelShape passableleaves$tick$preventStickingInLeaves(VoxelShape originalVoxelShape, @Local BlockState blockState) {
        if (blockState.isIn(BlockTags.LEAVES)) {
            return VoxelShapes.empty();
        }

        return originalVoxelShape;
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;inBlockState:Lnet/minecraft/block/BlockState;", opcode = Opcodes.GETFIELD))
    private BlockState passableleaves$tick$leavesHit(BlockState original) {
        if (this.inBlockState == null) {
            return original;
        }

        if (this.inBlockState.isIn(BlockTags.LEAVES) && ((AbstractBlockStateAccess) this.inBlockState).$passableleaves$getPlayerHitLeaves()
        ) {
            return null;
        }

        return original;
    }

    @ModifyExpressionValue(method = "shouldFall", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isSpaceEmpty(Lnet/minecraft/util/math/Box;)Z"))
    private boolean passableleaves$shouldFall$leavesHit(boolean original) {
        if (this.inBlockState == null) {
            return original;
        }

        if (this.inBlockState.isIn(BlockTags.LEAVES) && ((AbstractBlockStateAccess) this.inBlockState).$passableleaves$getPlayerHitLeaves()
        ) {
            return true;
        }

        return original;
    }
}
