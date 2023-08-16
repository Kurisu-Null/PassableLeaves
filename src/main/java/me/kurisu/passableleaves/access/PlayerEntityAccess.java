package me.kurisu.passableleaves.access;

import me.kurisu.passableleaves.enums.KeybindAction;

public interface PlayerEntityAccess {
    void addKeybindAction(KeybindAction keybindAction);
    boolean hasKeybindAction(KeybindAction keybindAction);
    void removeKeybindActions(KeybindAction keybindAction);
}
