package me.kurisu.passableleaves.access;

import net.minecraft.util.math.BlockPos;

public interface ProjectileEntityAccess {
    default boolean passableLeaves$canPassThroughLeaves(BlockPos blockPos) {
        return true;
    }
}
