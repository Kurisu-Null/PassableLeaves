package me.kurisu.passableleaves.access;


public interface AbstractBlockStateAccess {
    default void passableleaves$playerHitLeaves() {
    }

    default boolean $passableleaves$getPlayerHitLeaves() {
        return false;
    }
}
