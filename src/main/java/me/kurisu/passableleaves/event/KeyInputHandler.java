package me.kurisu.passableleaves.event;

import me.kurisu.passableleaves.PassableLeaves;
import me.kurisu.passableleaves.access.PlayerEntityAccess;
import me.kurisu.passableleaves.enums.KeybindAction;
import me.kurisu.passableleaves.network.packet.KeybindingC2SPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeyInputHandler {
    public static final String KEY_CATEGORY = "category.passableleaves.name";
    public static final String KEY_KEY_THROUGH_LEAVES = "key.passableleaves.fall_through_leaves";

    private static KeyBinding fallThroughLeavesKey;

    private static boolean fallThroughLeavesKeyUp = true;

    private static void registerKeyInputs() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (fallThroughLeavesKey.isPressed() && fallThroughLeavesKeyUp) {
                PassableLeaves.PASSABLE_LEAVES_CHANNEL.clientHandle().send(new KeybindingC2SPacket(KeybindAction.FALL_THROUGH_LEAVES, true));
                ((PlayerEntityAccess) client.player).addKeybindAction(KeybindAction.FALL_THROUGH_LEAVES);
                fallThroughLeavesKeyUp = false;
            }

            if (!fallThroughLeavesKey.isPressed() && !fallThroughLeavesKeyUp) {
                PassableLeaves.PASSABLE_LEAVES_CHANNEL.clientHandle().send(new KeybindingC2SPacket(KeybindAction.FALL_THROUGH_LEAVES, false));
                ((PlayerEntityAccess) client.player).removeKeybindActions(KeybindAction.FALL_THROUGH_LEAVES);
                fallThroughLeavesKeyUp = true;
            }
        });
    }

    public static void registerClient() {
        fallThroughLeavesKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_KEY_THROUGH_LEAVES,
                InputUtil.Type.KEYSYM,
                InputUtil.UNKNOWN_KEY.getCode(),
                KEY_CATEGORY
        ));

        registerKeyInputs();
    }
}
