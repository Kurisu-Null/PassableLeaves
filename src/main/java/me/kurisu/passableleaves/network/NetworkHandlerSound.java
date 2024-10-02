package me.kurisu.passableleaves.network;

import io.wispforest.owo.network.serialization.PacketBufSerializer;
import me.kurisu.passableleaves.PassableLeaves;
import me.kurisu.passableleaves.network.packet.SoundC2SPacket;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class NetworkHandlerSound {
    public static void registerServer() {

        // Write the ordinal of the SoundCategory enum
        PacketBufSerializer.register(SoundCategory.class, PacketByteBuf::writeEnumConstant, buf -> {
            // Read and return the SoundCategory enum constant
            return buf.readEnumConstant(SoundCategory.class);
        });

        PacketBufSerializer.register(SoundEvent.class, (buf, soundEvent) -> {
            // Write the identifier of the sound event
            buf.writeIdentifier(Registries.SOUND_EVENT.getId(soundEvent));
        }, buf -> {
            // Read the identifier and fetch the SoundEvent from the registry
            Identifier soundId = buf.readIdentifier();
            return Registries.SOUND_EVENT.get(soundId);
        });


        PassableLeaves.PASSABLE_LEAVES_CHANNEL.registerServerbound(SoundC2SPacket.class, (message, access) -> {
            access.player().getWorld().playSound(null, message.blockPos(), message.sound(), message.category(), message.volume(), message.pitch());

        });
    }
}
