package me.kurisu.passableleaves.event;

import me.kurisu.passableleaves.PassableLeaves;
import me.kurisu.passableleaves.access.AbstractBlockStateAccess;
import me.kurisu.passableleaves.network.packet.HitLeaveC2SPacket;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;

public class HitLeavesHandler {
    public static void registerServer() {
        PassableLeaves.PASSABLE_LEAVES_CHANNEL.registerServerbound(HitLeaveC2SPacket.class, (message, access) -> {
            BlockState blockState = access.player().getWorld().getBlockState(message.blockPos());

            if (blockState == null) {
                return;
            }

            if (blockState.isIn(BlockTags.LEAVES)) {
                ((AbstractBlockStateAccess) blockState).passableleaves$playerHitLeaves();
            }
        });
    }
}
