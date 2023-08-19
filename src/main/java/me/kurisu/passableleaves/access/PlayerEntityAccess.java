package me.kurisu.passableleaves.access;

import me.kurisu.passableleaves.enums.KeybindAction;

public interface PlayerEntityAccess {
    default void addKeybindAction(KeybindAction keybindAction) {

    }

    default boolean hasKeybindAction(KeybindAction keybindAction) {
        return false;
    }

    default void removeKeybindActions(KeybindAction keybindAction) {

    }
}
