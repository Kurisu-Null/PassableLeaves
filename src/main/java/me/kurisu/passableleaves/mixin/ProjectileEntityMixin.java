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
    private BlockPos lastLeavesWentThrough;

    @Shadow
    private @Nullable Entity owner;

    public boolean passableLeaves$canPassThroughLeaves(BlockPos blockPos) {
        if (blockPos.equals(this.lastLeavesWentThrough)) {
            return true;
        }

        Entity entity = ((Entity) (Object) this);
        int multiplier = (int) entity.getVelocity().length();

        if (PassableLeaves.CONFIG.soundEnabled()) {
            BlockSoundGroup soundGroup = BlockSoundGroup.AZALEA_LEAVES;
            entity.playSound(soundGroup.getBreakSound(), soundGroup.getVolume() * multiplier * 0.6F,
                    soundGroup.getPitch());
        }

        World world = entity.getWorld();

        // spawn fancy particle
        if (!world.isClient && PassableLeaves.CONFIG.particlesEnabled()) {
            int particleCount = multiplier * 2;
            BlockState blockState = world.getBlockState(blockPos);
            ((ServerWorld) world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState),
                    entity.getX(), entity.getY(), entity.getZ(), particleCount,
                    0.0, 0.0, 0.0, 0.15000000596046448);
        }

        if (this.owner != null && this.owner.passableLeaves$isInLeave(blockPos)) {
            this.lastLeavesWentThrough = blockPos;
            return true;
        }

        if(Math.random() > PassableLeaves.CONFIG.projectileHitLeavesChance()){
            this.lastLeavesWentThrough = blockPos;
            return true;
        }

        return false;
    }

}
