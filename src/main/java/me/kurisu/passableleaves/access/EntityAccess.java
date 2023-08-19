package me.kurisu.passableleaves.access;

public interface EntityAccess {
    default boolean getIsInsideLeaves() {
        return false;
    }
}
