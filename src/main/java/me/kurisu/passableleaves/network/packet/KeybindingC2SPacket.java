package me.kurisu.passableleaves.network.packet;

import me.kurisu.passableleaves.enums.KeybindAction;

public record KeybindingC2SPacket(KeybindAction keybindAction, boolean pressed) {
}
