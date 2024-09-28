package me.kurisu.passableleaves.mixin;

import me.kurisu.passableleaves.PassableLeaves;
import me.kurisu.passableleaves.access.ProjectileEntityAccess;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ProjectileEntity.class)
public class ProjectileEntityMixin implements ProjectileEntityAccess {
    @Unique
    @Nullable
    private BlockPos lastLeaveWentThrough;

    @Unique
    @Nullable
    private BlockPos stuckOnLeave;

    @Shadow
    @Nullable
    private Entity owner;

    public boolean passableLeaves$canPassThroughLeaves(BlockPos blockPos) {
        if (((Entity) (Object) this).getBlockPos().equals(this.stuckOnLeave)) {
            return false;
        }

        if (blockPos.equals(this.lastLeaveWentThrough)) {
            return true;
        }

        Entity entity = ((Entity) (Object) this);
        int multiplier = (int) entity.getVelocity().length();

        World world = entity.getWorld();

        if (!world.isClient) {
            if (PassableLeaves.CONFIG.soundEnabled()) {
                BlockSoundGroup soundGroup = BlockSoundGroup.AZALEA_LEAVES;
                world.playSound(null, blockPos, soundGroup.getBreakSound(), SoundCategory.BLOCKS, soundGroup.getVolume() * multiplier * 0.6F,
                        soundGroup.getPitch());
            }

            // spawn fancy particle
            if (PassableLeaves.CONFIG.particlesEnabled()) {
                int particleCount = multiplier * 2;
                BlockState blockState = world.getBlockState(blockPos);
                ((ServerWorld) world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState),
                        entity.getX(), entity.getY(), entity.getZ(), particleCount,
                        0.0, 0.0, 0.0, 0.15000000596046448);
            }
        }

        if (this.owner != null && this.owner.passableLeaves$isInLeave(blockPos)) {
            this.lastLeaveWentThrough = blockPos;
            return true;
        }

        if (Math.random() > PassableLeaves.CONFIG.projectileHitLeavesChance()) {
            this.lastLeaveWentThrough = blockPos;
            return true;
        }

        this.stuckOnLeave = blockPos;
        return false;
    }

}
