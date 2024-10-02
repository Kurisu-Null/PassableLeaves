package me.kurisu.passableleaves.network;

import me.kurisu.passableleaves.PassableLeaves;
import me.kurisu.passableleaves.access.PlayerEntityAccess;
import me.kurisu.passableleaves.enums.KeybindAction;
import me.kurisu.passableleaves.network.packet.KeybindingC2SPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

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
