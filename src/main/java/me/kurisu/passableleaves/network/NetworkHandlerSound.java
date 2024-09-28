package me.kurisu.passableleaves.network;

import me.kurisu.passableleaves.PassableLeaves;
import me.kurisu.passableleaves.network.packet.SoundC2SPacket;

public class NetworkHandlerSound {
    public static void registerServer() {
        PassableLeaves.PASSABLE_LEAVES_CHANNEL.registerServerbound(SoundC2SPacket.class, (message, access) -> {
            access.player().getWorld().playSound(null, message.blockPos(), message.sound(), message.category(), message.volume(), message.pitch());
        });
    }
}
