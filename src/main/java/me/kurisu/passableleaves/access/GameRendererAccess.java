package me.kurisu.passableleaves.access;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface GameRendererAccess {
    @Nullable
    default BlockPos passableLeaves$getBypassedLeave() {
        return null;
    }
}
