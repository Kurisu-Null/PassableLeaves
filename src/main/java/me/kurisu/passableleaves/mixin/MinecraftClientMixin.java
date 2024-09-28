package me.kurisu.passableleaves.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.wispforest.owo.particles.ClientParticles;
import me.kurisu.passableleaves.PassableLeaves;
import me.kurisu.passableleaves.access.AbstractBlockStateAccess;
import me.kurisu.passableleaves.access.GameRendererAccess;
import me.kurisu.passableleaves.network.packet.HitLeaveC2SPacket;
import me.kurisu.passableleaves.network.packet.SoundC2SPacket;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow
    @Final
    public GameRenderer gameRenderer;

    @Shadow
    @Nullable
    public abstract net.minecraft.entity.@Nullable Entity getCameraEntity();

    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Shadow
    @Nullable
    public ClientWorld world;

    @ModifyExpressionValue(method = "doAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/hit/HitResult;getType()Lnet/minecraft/util/hit/HitResult$Type;"))
    private HitResult.Type passableleaves$doAttack$checkIfHitLeaves(HitResult.Type original) {
        BlockPos bypassedLeave = ((GameRendererAccess) this.gameRenderer).passableLeaves$getBypassedLeave();

        if (bypassedLeave == null) {
            return original;
        }

        if (Math.random() <= PassableLeaves.CONFIG.attackHitLeavesChange()) {
            if (world == null) {
                return HitResult.Type.MISS;
            }

            if (PassableLeaves.CONFIG.fallWhenHittingLeaves()) {
                PassableLeaves.PASSABLE_LEAVES_CHANNEL.clientHandle().send(new HitLeaveC2SPacket(bypassedLeave));
                ((AbstractBlockStateAccess) world.getBlockState(bypassedLeave)).passableleaves$playerHitLeaves();
            }

            // Should not happend
            if (!world.isClient) {
                return HitResult.Type.MISS;
            }

            float randominess = (float) Math.random();

            if (PassableLeaves.CONFIG.soundEnabled()) {
                BlockSoundGroup soundGroup = BlockSoundGroup.AZALEA_LEAVES;
                PassableLeaves.PASSABLE_LEAVES_CHANNEL.clientHandle().send(new SoundC2SPacket(bypassedLeave, soundGroup.getBreakSound(), SoundCategory.BLOCKS, Math.max(randominess, 0.6f), soundGroup.getPitch() * Math.max(randominess, 0.6f)));
            }

            // spawn fancy particle
            if (PassableLeaves.CONFIG.particlesEnabled()) {
                BlockState blockState = world.getBlockState(bypassedLeave);

                if (this.getCameraEntity() != null) {
                    ClientParticles.setVelocity(this.getCameraEntity().getRotationVector().multiply(-2f));
                }

                // TODO: Position of the particles can be better
                ClientParticles.setParticleCount((int) (randominess * 8));
                ClientParticles.spawnCubeOutline(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), world, bypassedLeave.toCenterPos(), 0f, 0.2f);
            }

            return HitResult.Type.MISS;
        }
        return original;
    }

}
