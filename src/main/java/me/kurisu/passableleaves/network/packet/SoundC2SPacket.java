package me.kurisu.passableleaves.network.packet;

import io.wispforest.owo.network.serialization.PacketBufSerializer;
import me.kurisu.passableleaves.enums.KeybindAction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public record SoundC2SPacket(BlockPos blockPos, SoundEvent sound, SoundCategory category,
                             float volume, float pitch)  {

    public static void encode(SoundC2SPacket packet, PacketByteBuf buffer) {
        buffer.writeBlockPos(packet.blockPos());
        PacketBufSerializer.get(SoundEvent.class).serializer().accept(buffer, packet.sound());
        PacketBufSerializer.get(SoundCategory.class).serializer().accept(buffer, packet.category());
        buffer.writeFloat(packet.volume());
        buffer.writeFloat(packet.pitch());
    }

    public static SoundC2SPacket decode(PacketByteBuf buffer) {
        BlockPos blockPos = buffer.readBlockPos();
        SoundEvent soundEvent = PacketBufSerializer.get(SoundEvent.class).deserializer().apply(buffer);
        SoundCategory category = PacketBufSerializer.get(SoundCategory.class).deserializer().apply(buffer);
        float volume = buffer.readFloat();
        float pitch = buffer.readFloat();

        return new SoundC2SPacket(blockPos, soundEvent, category, volume, pitch);
    }
}
