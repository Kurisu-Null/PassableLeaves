package me.kurisu.passableleaves.network;

import io.wispforest.owo.serialization.Endec;
import io.wispforest.owo.serialization.endec.BuiltInEndecs;
import io.wispforest.owo.serialization.endec.ReflectiveEndecBuilder;
import io.wispforest.owo.serialization.endec.StructEndecBuilder;
import me.kurisu.passableleaves.network.packet.SoundC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class NetworkEndecPassableLeaves {
    public static final Endec<SoundEvent> SOUND_EVENT_ENDEC = BuiltInEndecs.IDENTIFIER.xmap(
            Registries.SOUND_EVENT::get,   // From Identifier to SoundEvent
            Registries.SOUND_EVENT::getId  // From SoundEvent to Identifier
    );

    public static final Endec<SoundC2SPacket> SOUND_C2S_ENDEC = StructEndecBuilder.of(
            BuiltInEndecs.BLOCK_POS.fieldOf("blockPos", SoundC2SPacket::blockPos),
            NetworkEndecPassableLeaves.SOUND_EVENT_ENDEC.fieldOf("sound", SoundC2SPacket::sound),
            Endec.forEnum(SoundCategory.class).fieldOf("category", SoundC2SPacket::category),
            Endec.FLOAT.fieldOf("volume", SoundC2SPacket::volume),
            Endec.FLOAT.fieldOf("pitch", SoundC2SPacket::pitch),
            SoundC2SPacket::new // Constructor for SoundC2SPacket
    );

    public static void registerEndecs(){
        ReflectiveEndecBuilder.register(SOUND_EVENT_ENDEC, SoundEvent.class);
        ReflectiveEndecBuilder.register(SOUND_C2S_ENDEC, SoundC2SPacket.class);
    }
}
