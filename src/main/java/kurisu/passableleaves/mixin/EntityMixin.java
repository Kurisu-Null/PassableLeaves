package kurisu.passableleaves.mixin;

import kurisu.passableleaves.PassableLeaves;
import kurisu.passableleaves.PassableLeavesConfig;
import kurisu.passableleaves.access.EntityAccess;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityAccess {
    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow
    public World world;

    @Shadow
    public abstract Box getBoundingBox();

    private boolean isInsideLeaves;

    private PassableLeavesConfig config = PassableLeaves.getConfig();

    public boolean getIsInsideLeaves() {
        return this.isInsideLeaves;
    }

    @Inject(method = "baseTick", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;inPowderSnow:Z"))
    private void passableleaves_baseTick(CallbackInfo ci) {

        BlockPos leafBlockPos = this.getLeafPositionEntityIsInside();
        this.isInsideLeaves = leafBlockPos != null ? true : false;

        if (this.isInsideLeaves) {
            this.handleInsideLeaves(leafBlockPos);
        }
    }

    @Inject(method = "playStepSound", at = @At("TAIL"))
    private void passableleaves_playLeafStepSound(CallbackInfo ci) {
        if (this.isInsideLeaves && config.isSoundEnabled()) {
            BlockSoundGroup soundGroup = BlockSoundGroup.AZALEA_LEAVES;
            this.playSound(soundGroup.getBreakSound(), soundGroup.getVolume() * 0.6F,
                    soundGroup.getPitch());
        }
    }

    private BlockPos getLeafPositionEntityIsInside() {
        Box contractedBoundingBox = this.getBoundingBox().contract(0.1F);
        return BlockPos.stream(contractedBoundingBox).filter((pos) -> {
            BlockState blockState = this.world.getBlockState(pos);
            return blockState.isIn(BlockTags.LEAVES);
        }).findFirst().orElse(null);
    }

    private void handleInsideLeaves(BlockPos blockPos) {
        // don't affect creative mode and flying
        if (((Entity) (Object) this) instanceof PlayerEntity) {
            if (PassableLeaves.isFlyingInCreative(((PlayerEntity) (Object) this))) {
                return;
            }
        }

        if (config.isFallingEnabled()) {
            BlockState leafBlockState = this.world.getBlockState(blockPos);
            this.fallingOnLeaves(leafBlockState, this.world, ((Entity) (Object) this));
        }
    }

    private void fallingOnLeaves(BlockState blockState, World world, Entity entity) {
        if (!entity.isOnGround()) {
            if (entity.fallDistance > entity.getSafeFallDistance()) {
                if (config.isSoundEnabled()) {
                    BlockSoundGroup soundGroup = BlockSoundGroup.AZALEA_LEAVES;
                    entity.playSound(soundGroup.getBreakSound(),
                            Math.min(soundGroup.getVolume() + 0.5F, soundGroup.getVolume() + entity.fallDistance - entity.getSafeFallDistance()),
                            soundGroup.getPitch() * 0.7F);
                }


                Vec3d slowDownedVelocity = entity.getVelocity().multiply(config.getFallingSpeedReductionMultiplicator());
                entity.setVelocity(slowDownedVelocity);

                // spawn fancy particle
                if (!world.isClient && config.isParticlesEnabled()) {
                    int particleCount = (int) MathHelper.ceil(entity.fallDistance - entity.getSafeFallDistance()) * 100;


                    ((ServerWorld) world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState),
                            entity.getX(), entity.getY(), entity.getZ(), particleCount,
                            0.0, 0.0, 0.0, 0.15000000596046448);
                }

                entity.fallDistance = entity.fallDistance * config.getFallingDistanceReductionMultiplicator();
                entity.handleFallDamage(entity.fallDistance, 1.0F, DamageSource.FALL);
            }
        }
    }
}
