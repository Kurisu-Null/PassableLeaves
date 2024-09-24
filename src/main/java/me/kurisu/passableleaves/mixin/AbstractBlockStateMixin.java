package me.kurisu.passableleaves.mixin;

import me.kurisu.passableleaves.PassableLeaves;
import me.kurisu.passableleaves.access.ProjectileEntityAccess;
import me.kurisu.passableleaves.enchantment.PassableLeavesEnchantments;
import me.kurisu.passableleaves.enums.KeybindAction;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Shadow
    public abstract boolean isIn(TagKey<Block> tag);

    @Inject(method = "getOutlineShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    private void passableleaves_getOutlineShape(BlockView world, BlockPos pos, CallbackInfoReturnable<VoxelShape> cir) {
        if (this.isIn(BlockTags.LEAVES)) {
            this.passableleaves_getOutlineShape(world, pos, ShapeContext.absent(), cir);
        }
    }


    @Inject(method = "getOutlineShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    private void passableleaves_getOutlineShape(BlockView blockView, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (!this.isIn(BlockTags.LEAVES)) {
            return;
        }

        if (!(context instanceof EntityShapeContext)) {
            return;
        }

        Entity entity = ((EntityShapeContext) context).getEntity();

        if (entity == null) {
            return;
        }

        BlockPos lastLeaveWentThroughPos = entity.passableLeaves$getLastLeaveWentThrough();

        if (pos.equals(lastLeaveWentThroughPos)) {
            cir.setReturnValue(VoxelShapes.empty());
            return;
        }

        if (entity instanceof ProjectileEntity) {
            if (((ProjectileEntityAccess) (Object) entity).passableLeaves$canPassThroughLeaves(pos)) {
                entity.passableLeaves$setLastLeaveWentThrough(pos);
                cir.setReturnValue(VoxelShapes.empty());
                return;
            }
        }

        if (!PassableLeaves.CONFIG.canHitThroughLeaves()) {
            return;
        }

        if (!(entity instanceof LivingEntity)) {
            return;
        }

        ItemStack itemStack = ((LivingEntity) entity).getEquippedStack(EquipmentSlot.MAINHAND);

        if (entity.isPlayer()) {
            if (itemStack.getItem().canMine(blockView.getBlockState(pos), ((World) blockView), pos, ((PlayerEntity) entity))) {
                return;
            }
        }

        if (PassableLeaves.CONFIG.playerOnly()) {
            return;
        }

        cir.setReturnValue(VoxelShapes.empty());
    }


    @Inject(method = "getRaycastShape", at = @At("HEAD"), cancellable = true)
    private void passableleaves_getRaycastShape(BlockView world, BlockPos pos, CallbackInfoReturnable<VoxelShape> cir) {
        if (this.isIn(BlockTags.LEAVES)) {
            cir.setReturnValue(VoxelShapes.empty());
        }
    }

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    private void passableleaves_getCollisionShape(BlockView world, BlockPos pos, CallbackInfoReturnable<VoxelShape> cir) {
        if (this.isIn(BlockTags.LEAVES)) {
            cir.setReturnValue(VoxelShapes.empty());
        }
    }

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;", at = @At("HEAD"), cancellable = true)
    private void passableleaves_getCollisionShape(BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (this.isIn(BlockTags.LEAVES)) {
            VoxelShape voxelShape = this.getVoxelShape(world, pos, context);
            if (voxelShape == null) {
                return;
            }

            cir.setReturnValue(voxelShape);
        }
    }

    @Inject(method = "canPathfindThrough", at = @At("HEAD"), cancellable = true)
    private void passableleaves_canPathfindThrough(BlockView world, BlockPos pos, NavigationType type, CallbackInfoReturnable<Boolean> cir) {
        if (this.isIn(BlockTags.LEAVES)) {
            cir.setReturnValue(!PassableLeaves.CONFIG.playerOnly());
        }
    }

    @Unique
    @Nullable
    private VoxelShape getVoxelShape(BlockView world, BlockPos pos, ShapeContext context) {
        if (!this.isIn(BlockTags.LEAVES)) {
            return null;
        }

        if (!(context instanceof EntityShapeContext)) {
            return null;
        }

        Entity entity = ((EntityShapeContext) context).getEntity();

        if (entity == null) {
            return null;
        }

        BlockPos lastLeaveWentThroughPos = entity.passableLeaves$getLastLeaveWentThrough();

        if (pos.equals(lastLeaveWentThroughPos)) {
            return VoxelShapes.empty();
        }

        entity.passableLeaves$setLastLeaveWentThrough(null);

        if (entity instanceof ProjectileEntity) {
            if (((ProjectileEntityAccess) (Object) entity).passableLeaves$canPassThroughLeaves(pos)) {
                entity.passableLeaves$setLastLeaveWentThrough(pos);
                return VoxelShapes.empty();
            }
        }

        if (!(entity instanceof LivingEntity)) {
            return null;
        }

        if (!entity.isPlayer() && PassableLeaves.CONFIG.playerOnly()) {
            return null;
        }

        if (entity instanceof PlayerEntity) {
            // don't affect creative mode and flying
            if (PassableLeaves.isFlyingInCreative(((PlayerEntity) (Object) entity))) {
                entity.passableLeaves$setLastLeaveWentThrough(pos);
                return VoxelShapes.empty();
            }

            boolean pressingKey = ((PlayerEntity) (Object) entity).hasKeybindAction(KeybindAction.FALL_THROUGH_LEAVES);

            if (PassableLeaves.CONFIG.fallOnKeyPress() && pressingKey) {
                entity.passableLeaves$setLastLeaveWentThrough(pos);
                return VoxelShapes.empty();
            }
        }

        BlockPos entityPos = entity.getBlockPos();
        // check if leaf is below player
        if (pos.getY() >= entityPos.getY()) {
            entity.passableLeaves$setLastLeaveWentThrough(pos);
            return VoxelShapes.empty();
        }

        // don't apply when the player is falling from to high
        if (entity.fallDistance > entity.getSafeFallDistance() || !PassableLeaves.CONFIG.fallingEnabled()) {
            entity.passableLeaves$setLastLeaveWentThrough(pos);
            return VoxelShapes.empty();
        }

        if (PassableLeaves.CONFIG.enchantmentEnabled()) {
            int enchantmentLevel = EnchantmentHelper.getEquipmentLevel(PassableLeavesEnchantments.LEAF_WALKER, (LivingEntity) entity);
            if (enchantmentLevel > 0) {
                return null;
            }
        }

        if (PassableLeaves.CONFIG.leafWalking()) {
            if (!PassableLeaves.CONFIG.leafSprinting() && entity.isSprinting()) {
                entity.passableLeaves$setLastLeaveWentThrough(pos);
                return VoxelShapes.empty();
            }
        }

        return null;
    }
}
