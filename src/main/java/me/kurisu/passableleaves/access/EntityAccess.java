package me.kurisu.passableleaves.access;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface EntityAccess {
    default boolean passableLeaves$getIsInsideLeaves() {
        return false;
    }

    @Nullable
    default BlockPos passableLeaves$getLeavePositionEntityIsInside() {
        return null;
    }

    default boolean passableLeaves$isInLeave(BlockPos blockPos) {
        return false;
    }

}
