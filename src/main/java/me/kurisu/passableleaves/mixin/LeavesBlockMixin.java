package me.kurisu.passableleaves.mixin;

import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 0)
    private static AbstractBlock.Settings passableleaves_ModifySettings(AbstractBlock.Settings settings) {
        return settings.noCollision();
    }

}