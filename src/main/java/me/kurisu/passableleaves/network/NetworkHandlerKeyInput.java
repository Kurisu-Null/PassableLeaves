package me.kurisu.passableleaves.network;

import me.kurisu.passableleaves.PassableLeaves;
import me.kurisu.passableleaves.access.PlayerEntityAccess;
import me.kurisu.passableleaves.network.packet.KeybindingC2SPacket;

public class NetworkHandlerKeyInput {
    public static void registerServer() {
        PassableLeaves.PASSABLE_LEAVES_CHANNEL.registerServerbound(KeybindingC2SPacket.class, (message, access) -> {
            if (message.pressed()) {
                ((PlayerEntityAccess) access.player()).addKeybindAction(message.keybindAction());
            } else {
                ((PlayerEntityAccess) access.player()).removeKeybindActions(message.keybindAction());
            }
        });
    }
}
