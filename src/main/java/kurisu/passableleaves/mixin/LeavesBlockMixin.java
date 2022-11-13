package kurisu.passableleaves.mixin;

import kurisu.passableleaves.PassableLeaves;
import kurisu.passableleaves.PassableLeavesConfig;
import kurisu.passableleaves.enchantment.PassableLeavesEnchantments;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 0)
    private static AbstractBlock.Settings passableleaves_ModifySettings(AbstractBlock.Settings settings) {
        return settings.noCollision();
    }

    public VoxelShape getCollisionShape(BlockState blockState, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext) {
            Entity entity = ((EntityShapeContext) context).getEntity();

            if (entity instanceof LivingEntity) {
                PassableLeavesConfig config = PassableLeaves.getConfig();
                if (!entity.isPlayer() && config.isPlayerOnlyAffected()) {
                    return VoxelShapes.fullCube();
                }

                // don't affect creative mode and flying
                if (entity instanceof PlayerEntity) {
                    if (PassableLeaves.isFlyingInCreative(((PlayerEntity) (Object) entity))) {
                        System.out.println(PathNodeType.LEAVES.getDefaultPenalty());
                        return VoxelShapes.empty();
                    }
                }

                if (context.isDescending()) {
                    return VoxelShapes.empty();
                }

                if (!config.isEnchantmentEnabled()) {
                    return VoxelShapes.empty();
                }

                int enchantmentLevel = EnchantmentHelper.getEquipmentLevel(PassableLeavesEnchantments.LEAF_WALKER, (LivingEntity) entity);
                if (enchantmentLevel == 0) {
                    return VoxelShapes.empty();
                }

                BlockPos entityPos = entity.getBlockPos();
                // check if player is already in leaves and on ground
                if (pos.getY() <= entityPos.getY() && entityPos.getX() == pos.up().getX() && entityPos.getZ() == pos.up().getZ()) {

                    // don't apply when the player is falling from to high
                    if (entity.fallDistance < entity.getSafeFallDistance() || !config.isFallingEnabled()) {
                        return VoxelShapes.fullCube();
                    }
                }
            }
        }
        return VoxelShapes.empty();
    }

    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 0.2f;
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return !PassableLeaves.getConfig().isPlayerOnlyAffected();
    }
}