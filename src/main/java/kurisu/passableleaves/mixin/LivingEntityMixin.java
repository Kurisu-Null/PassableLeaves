package kurisu.passableleaves.mixin;

import kurisu.passableleaves.PassableLeaves;
import kurisu.passableleaves.PassableLeavesConfig;
import kurisu.passableleaves.access.EntityAccess;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    private static final UUID LEAVES_SLOW_ID = UUID.fromString("b065c03d-0975-4f3d-a98e-b78bf6b81e0c");

    @Shadow
    @Nullable
    public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;addPowderSnowSlowIfNeeded()V"))
    public void passableleaves_tickMovement(CallbackInfo ci) {
        if (PassableLeavesConfig.isSlowEnabled()) {
            this.removeLeavesSlow();
            this.addLeavesSlowIfNeeded();
        }
    }

    public void removeLeavesSlow() {
        EntityAttributeInstance entityAttributeInstance =
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (entityAttributeInstance != null) {
            if (entityAttributeInstance.getModifier(LEAVES_SLOW_ID) != null) {
                entityAttributeInstance.removeModifier(LEAVES_SLOW_ID);
            }
        }
    }

    public void addLeavesSlowIfNeeded() {
        // don't affect creative mode and flying
        if (((LivingEntity) (Object) this) instanceof PlayerEntity) {
            if (PassableLeaves.isFlyingInCreative(((PlayerEntity) (Object) this))) {
                return;
            }
        }

        if (((EntityAccess) this).getIsInsideLeaves()) {

            EntityAttributeInstance entityAttributeInstance =
                    this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

            if (entityAttributeInstance == null) {
                return;
            }

            float slowEffect = PassableLeavesConfig.getSlowMultiplier();
            entityAttributeInstance.addTemporaryModifier(
                    new EntityAttributeModifier(LEAVES_SLOW_ID, "Leaves slow", slowEffect,
                            EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }
}
