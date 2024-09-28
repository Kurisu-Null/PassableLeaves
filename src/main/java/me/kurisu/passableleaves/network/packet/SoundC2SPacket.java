package me.kurisu.passableleaves.network.packet;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

public record SoundC2SPacket(BlockPos blockPos, SoundEvent sound, SoundCategory category,
                             float volume, float pitch) {
}